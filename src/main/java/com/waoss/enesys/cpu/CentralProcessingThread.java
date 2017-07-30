/*
 * Enesys : An NES Emulator
 * Copyright (C) 2017  Rahul Chhabra and Waoss
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.waoss.enesys.cpu;

import com.waoss.enesys.Console;
import com.waoss.enesys.cpu.instructions.Instruction;
import com.waoss.enesys.cpu.instructions.InstructionConstants;
import com.waoss.enesys.cpu.instructions.Instructions;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>A thread in which the central processing of the CPU occurs.
 * The CPU uses the {@link CentralProcessor#start()} method to start processing.
 * Processing starts a loop from the program counter and the thread reads every byte and finds the
 * corresponding instruction and then uses the {@link CentralProcessor#process(Instruction)} function to process the instruction</p>
 *
 * @see CentralProcessor
 * @see CentralProcessor#process(Instruction)
 * @see CentralProcessor#start()
 * @see Console
 */
public class CentralProcessingThread extends Thread {

    /**
     * An atomic reference to the central processor which contains this thread
     */
    @NotNull
    private final AtomicReference<CentralProcessor> centralProcessor;

    /**
     * An atomic reference to the {@link Console} this is a part of.
     */
    @NotNull
    private final AtomicReference<Console> console;

    /**
     * Creates a new CentralProcessingThread for the processor specified in the constructor
     *
     * @param centralProcessor The processor
     */
    public CentralProcessingThread(CentralProcessor centralProcessor) {
        super("CentralProcessingThread");
        this.centralProcessor = new AtomicReference<>(centralProcessor);
        this.console = new AtomicReference<>(this.centralProcessor.get().getConsole());
    }

    /**
     * Starts execution
     */
    @Override
    public void run() {
        int i = console.get().getProgramCounter().getValue();
        while (true) {
            int opCode = console.get().getCompleteMemory().read(i);
            int size = Instructions.getInstructionSize(opCode);
            @NotNull final Integer[] arguments = new Integer[size];
            for (short j = 1; j <= size; j++) {
                arguments[j - 1] = console.get().getCompleteMemory().read(i + j);
            }
            @NotNull final Instruction result = new Instruction(opCode, InstructionConstants.addressings[opCode]);
            result.argumentsProperty().set(arguments);
            centralProcessor.get().process(result);
            //TODO:Make program counter sober
        }
    }

    /**
     * Returns the CentralProcessor that this thread is processing for
     *
     * @return the CentralProcessor that this thread is processing for
     */
    public CentralProcessor getCentralProcessor() {
        return centralProcessor.get();
    }
}
