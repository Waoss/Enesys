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

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class CentralProcessingThread extends Thread {

    private final AtomicReference<CentralProcessor> centralProcessor;
    private final AtomicReference<Console> console;

    public CentralProcessingThread(CentralProcessor centralProcessor, Console console) {
        super("CentralProcessingThread");
        this.centralProcessor = new AtomicReference<>(centralProcessor);
        this.console = new AtomicReference<>(console);
    }

    @Override
    public void run() {
        short i = console.get().getProgramCounter().getValue();
        while (true) {
            int opCode = console.get().getCompleteMemory().read(i);
            int size = Instructions.getInstructionSize(opCode) - 1;
            final Number[] arguments = new Number[size];
            for (short j = 1; j <= size; j++) {
                arguments[j - 1] = console.get().getCompleteMemory().read((short) (i + j));
            }
            final Instruction result = new Instruction(opCode, InstructionConstants.addressings[opCode]);
            result.argumentsProperty().set(arguments);
            centralProcessor.get().process(result);
            i += size;
            console.get().getProgramCounter().setValue(i);
        }
    }
}
