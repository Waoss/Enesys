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
import com.waoss.enesys.cpu.instructions.InstructionName;
import com.waoss.enesys.cpu.registers.*;
import com.waoss.enesys.mem.CompleteMemory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>An instance of this class represents the CPU of the NES
 * The CPU of the NES had only 6 registers.All of them,except the {@link ProgramCounter} were 8 bits wide.
 * </p>
 */
public final class CentralProcessor implements Cloneable {

    /**
     * The console "this" is a part of.
     */
    private final AtomicReference<Console> console = new AtomicReference<Console>();
    private final AtomicReference<CentralProcessingThread> thread = new AtomicReference<CentralProcessingThread>();

    /**
     * Creates a new Processor for the console.
     *
     * @param console The console
     */
    public CentralProcessor(Console console) {
        this.console.set(console);
        this.thread.set(new CentralProcessingThread(this, console));
    }

    /**
     * Creates a clone by copying all the properties
     *
     * @param centralProcessor the processor to clone
     */
    public CentralProcessor(CentralProcessor centralProcessor) {
        this(centralProcessor.console.get());
    }

    /**
     * Returns a clone of this processor
     *
     * @return A clone
     * @throws CloneNotSupportedException Never
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CentralProcessor(this);
    }

    /**
     * Returns the {@link Console} the processor is part of
     *
     * @return the {@link Console} the processor is part of
     */
    public Console getConsole() {
        return console.get();
    }

    public CompleteMemory getCompleteMemory() {
        return console.get().getCompleteMemory();
    }

    /**
     * Returns the {@link ProcessorStatus}
     *
     * @return the {@link ProcessorStatus}
     */
    public ProcessorStatus getProcessorStatus() {
        return console.get().getProcessorStatus();
    }

    /**
     * Returns the {@link AccumalativeRegister}
     *
     * @return the {@link AccumalativeRegister}
     */
    public AccumalativeRegister getARegister() {
        return console.get().getARegister();
    }

    /**
     * Returns the {@link XRegister}
     *
     * @return the {@link XRegister}
     */
    public XRegister getXRegister() {
        return console.get().getXRegister();
    }

    /**
     * Returns the {@link YRegister}
     *
     * @return the {@link YRegister}
     */
    public YRegister getYRegister() {
        return console.get().getYRegister();
    }

    /**
     * Returns the {@link StackPointer}
     *
     * @return the {@link StackPointer}
     */
    public StackPointer getStackPointer() {
        return console.get().getStackPointer();
    }

    /**
     * Returns the {@link ProgramCounter}
     *
     * @return the {@link ProgramCounter}
     */
    public ProgramCounter getProgramCounter() {
        return console.get().getProgramCounter();
    }

    private void checkZeroAndNegative(int value) {
        if (value == 0) {
            getProcessorStatus().setZeroFlagEnabled(true);
        } else if (value < 0) {
            getProcessorStatus().setNegativeFlagEnabled(true);
        }
    }

    /**
     * Loads a value into the A register.
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#LDA}
     *
     * @param instruction The instruction
     */
    public void lda(Instruction instruction) throws IOException {
        loadRegister(getARegister(), instruction, InstructionName.LDA);
    }

    /**
     * <p>Stores the value of the A register into the index specified
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#STA}</p>
     *
     * @param instruction The instruction
     */
    public void sta(Instruction instruction) throws IOException {
        storeRegister(getARegister(), instruction, InstructionName.STA);
    }

    /**
     * <p>Stores the value of the X register into the index specified
     * Implementation of {@link InstructionName#LDX}</p>
     *
     * @param instruction The instruction
     * @throws IOException If the instruction is LDX
     */
    public void ldx(Instruction instruction) throws IOException {
        loadRegister(getXRegister(), instruction, InstructionName.LDX);
    }

    public void stx(Instruction instruction) throws IOException {
        storeRegister(getXRegister(), instruction, InstructionName.STX);
    }

    public void inx() {
        getXRegister().setValue((getXRegister().getValue() + 1));
    }

    /**
     * Processes an instruction
     * Invokes the required method for the instruction implementation
     *
     * @param instruction The instruction
     */
    public void process(Instruction instruction) {
        String name = instruction.getInstructionName().toString().toLowerCase();
        Method method;
        try {
            method = getClass().getMethod(name, Instruction.class);
            method.invoke(this, instruction);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts executing from the program counter in another thread
     */
    public void start() {
        thread.get().start();
    }

    /**
     * Starts executing from the program counter in the same thread
     */
    public void run() {
        thread.get().run();
    }

    public void interruptThread() {
        thread.get().interrupt();
    }

    public void brk(Instruction instruction) {
        interruptThread();
    }

    public void nop(Instruction instruction) {
        return;
    }

    /**
     * Logical AND
     * Stores into the accumalator the "AND" of the value and the previous value
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#AND}
     *
     * @param instruction The instruction
     */
    public void and(Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.AND);
    }

    private void checkInstructionName(Instruction instruction, InstructionName name) throws IOException {
        if (instruction.instructionNameProperty().get() != name) {
            throw new IOException("Wrong Instruction");
        }
    }

    private void loadRegister(Register register, Instruction instruction, InstructionName name) throws IOException {
        checkInstructionName(instruction, name);
        Registers.loadRegister(register, instruction.argumentsProperty().get()[0]);
        checkZeroAndNegative((Integer) register.getValue());
    }

    private void storeRegister(Register register, Instruction instruction, InstructionName name) throws IOException {
        checkInstructionName(instruction, name);
        Registers.storeRegister(register, console.get(), (Integer) instruction.argumentsProperty().get()[0]);
        checkZeroAndNegative((Byte) register.getValue());
    }

}
