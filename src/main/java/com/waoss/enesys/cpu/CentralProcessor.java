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

import com.waoss.enesys.*;
import com.waoss.enesys.cpu.instructions.*;
import com.waoss.enesys.cpu.registers.*;
import com.waoss.enesys.mem.Addressing;
import com.waoss.enesys.mem.CompleteMemory;
import javafx.beans.property.BooleanProperty;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>An instance of this class represents the CPU of the NES
 * The CPU of the NES had only 6 registers.All of them,except the {@link ProgramCounter} were 8 bits wide.
 * </p>
 */
public final class CentralProcessor implements Cloneable, Processor {

    /**
     * The console "this" is a part of.
     */
    private final transient AtomicReference<Console> console = new AtomicReference<>();

    /**
     * The {@link CentralProcessingThread} in which shit happens
     */
    private final AtomicReference<CentralProcessingThread> thread = new AtomicReference<>();

    /**
     * This atomic boolean which tells us whether the processing is happening in the main thread.
     */
    private final AtomicBoolean runningInMainThread = new AtomicBoolean(false);

    /**
     * Creates a new Processor for the console.
     *
     * @param console
     *         The console
     */
    public CentralProcessor(Console console) {
        this.console.set(console);
        this.thread.set(new CentralProcessingThread(this));
    }

    /**
     * Creates a clone by copying all the properties
     *
     * @param centralProcessor
     *         the processor to clone
     */
    public CentralProcessor(@NotNull CentralProcessor centralProcessor) {
        this(centralProcessor.console.get());
    }

    /**
     * Returns a clone of this processor
     *
     * @return A clone
     *
     * @throws CloneNotSupportedException
     *         Never
     */
    @NotNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CentralProcessor(this);
    }

    /**
     * Returns true if the processing is happening in the main thread
     *
     * @return true if the processing is happening in the main thread
     */
    public boolean isRunningInMainThread() {
        return runningInMainThread.get();
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

    public CentralProcessingThread getThread() {
        return thread.get();
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
     * @param instruction
     *         The instruction
     */
    public boolean lda(@NotNull Instruction instruction) throws IOException {
        return loadRegister(getARegister(), instruction, InstructionName.LDA);
    }

    /**
     * <p>Stores the value of the A register into the index specified
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#STA}</p>
     *
     * @param instruction
     *         The instruction
     */
    public boolean sta(@NotNull Instruction instruction) throws IOException {
        return storeRegister(getARegister(), instruction, InstructionName.STA);
    }

    /**
     * <p>Stores the value of the X register into the index specified
     * Implementation of {@link InstructionName#LDX}</p>
     *
     * @param instruction
     *         The instruction
     *
     * @throws IOException
     *         If the instruction is not valid
     */
    public boolean ldx(@NotNull Instruction instruction) throws IOException {
        return loadRegister(getXRegister(), instruction, InstructionName.LDX);
    }

    /**
     * <p>Stores the value stored in the X register into the address specified</p>
     *
     * @param instruction
     *         The instruction
     *
     * @throws IOException
     *         If the instruction is not valid
     */
    public boolean stx(@NotNull Instruction instruction) throws IOException {
        return storeRegister(getXRegister(), instruction, InstructionName.STX);
    }

    /**
     * <p>Increments the value of the X register by 1</p>
     *
     * @param instruction
     *         The instruction
     */
    public boolean inx(Instruction instruction) {
        getYRegister().setValue((getYRegister().getValue() + 1));
        return false;
    }

    /**
     * <p>Stores the value of the Y register into the index specified
     * Implementation of {@link InstructionName#LDY}</p>
     *
     * @param instruction
     *         The instruction
     *
     * @throws IOException
     *         If the instruction is not valid
     */
    public boolean ldy(@NotNull Instruction instruction) throws IOException {
        loadRegister(getYRegister(), instruction, InstructionName.LDY);
        return false;
    }

    /**
     * <p>Stores the value stored in the Y register into the address specified</p>
     *
     * @param instruction
     *         The instruction
     *
     * @throws IOException
     *         If the instruction is not valid
     */
    public boolean sty(@NotNull Instruction instruction) throws IOException {
        return storeRegister(getYRegister(), instruction, InstructionName.STY);
    }

    /**
     * <p>Increments the value of the Y register by 1</p>
     *
     * @param instruction
     *         The instruction
     */
    public boolean iny(Instruction instruction) {
        getYRegister().setValue(getYRegister().getValue() + 1);
        return false;
    }

    /**
     * Breaks execution.An interrupt.Interrupts the running thread.
     *
     * @param instruction
     *         The instruction
     */
    public boolean brk(Instruction instruction) {
        interruptThread();
        return false;
    }

    public boolean nop(Instruction instruction) {
        return false;
    }

    /**
     * Logical AND
     * Stores into the accumalator the "AND" of the value and the previous value
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#AND}
     *
     * @param instruction
     *         The instruction
     */
    public boolean and(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.AND);
        getARegister().setValue(getARegister().getValue() & instruction.argumentsProperty().get()[0]);
        checkZeroAndNegative(getARegister().getValue());
        return false;
    }

    /**
     * Add with carry
     * Implementation of {@link InstructionName#ADC}
     *
     * @param instruction
     *         The instruction
     */
    public boolean adc(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.ADC);
        getARegister().setValue(
                getARegister().getValue() + instruction.argumentsProperty().get()[0] + (getProcessorStatus().isCarryFlagEnabled() ? 1 : 0));
        checkZeroAndNegative(getARegister().getValue());
        return false;
    }

    /**
     * Branch on carry clear
     * Implementation of {@link InstructionName#BCC}
     *
     * @param instruction
     *         The instruction
     *
     * @throws IOException
     *         If some IO shit happens
     */
    public boolean bcc(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.BCC);
        return branchOnFlag("carryFlagEnabled", instruction, false);
    }

    /**
     * Branch on carry set
     * Implementation of {@link InstructionName#BCS}
     *
     * @param instruction
     *         The instruction
     *
     * @throws IOException
     *         If some IO shit happens
     */
    public boolean bcs(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.BCS);
        return branchOnFlag("carryFlagEnabled", instruction, true);
    }

    /**
     * Branch on overflow clear
     *
     * @param instruction
     *         The instruction
     *
     * @throws IOException
     *         If some IO shit happens
     */
    public boolean bvc(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.BVC);
        return branchOnFlag("overflowFlagEnabled", instruction, false);
    }

    /**
     * Branch on overflow set
     *
     * @param instruction
     *         The instruction
     *
     * @return True if branching happened,false otherwise.
     *
     * @throws IOException
     *         If some IO shit happens
     */
    public boolean bvs(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.BVS);
        return branchOnFlag("overflowFlagEnabled", instruction, true);
    }

    /**
     * Set carry flag
     *
     * @param instruction
     *         The instruction
     *
     * @return False:for no manupilation to the Program counter was doen
     *
     * @throws IOException
     *         If some IO shit happens
     */
    public boolean sec(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.SEC);
        getProcessorStatus().setCarryFlagEnabled(true);
        return false;
    }

    /**
     * Set decimal flag
     *
     * @param instruction
     *         The instruction
     *
     * @return False : No change of Program counter
     *
     * @throws IOException
     *         If some IO shit happens
     */
    public boolean sed(@NotNull Instruction instruction) throws IOException {
        checkInstructionName(instruction, InstructionName.SED);
        getProcessorStatus().setDecimalFlagEnabled(true);
        return false;
    }

    /**
     * Processes an instruction
     * Invokes the required method for the instruction implementation
     *
     * @param instruction
     *         The instruction
     */
    public void process(@NotNull Instruction instruction) {
        @NotNull String name = instruction.getInstructionName().toString().toLowerCase();
        Method method;
        try {
            method = getClass().getMethod(name, Instruction.class);
            method.invoke(this, instruction);
            final ProgramCounter programCounter = getProgramCounter();
            programCounter.setValue(programCounter.getValue() + instruction.argumentsProperty().get().length);
        } catch (@NotNull NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Implementation of {@link Processor#process(Object...)}.
     * Assumes the first argument to be the opcode and a second optional argument to be the Addressing mode as defined
     * in
     * {@link Addressing}.If no addressing mode is given,it is found using the opcode<br>
     * For example, {@code
     * cpu.process(0xe8);
     * } would execute {@link #inx(Instruction)} because of the opcode.</p>
     *
     * @param args
     *         The arguments, at least one is expected(the opcode),the addressing too may be optionally given
     *
     * @throws ProcessingException
     *         If the opcode is invalid or the arguments are invalid.
     * @throws NullPointerException
     *         If {@code args} is null
     */
    @Override
    public void process(Object... args) throws ProcessingException {
        Objects.requireNonNull(args);
        Integer opCode = 0;
        Addressing addressing = null;
        if (args[0] instanceof Number) {
            if (args[0] instanceof Integer || args[1] instanceof Byte || args[0] instanceof Short) {
                opCode = (Integer) args[0];
            } else {
                throw new ProcessingException("Invalid argument " + args[0]);
            }
        }
        if (args[1] instanceof Integer) {
            addressing = Addressing.values()[(Integer) args[1]];
        } else if (args[1] == null) {
            addressing = InstructionConstants.addressings[opCode];
        }
        process(new Instruction(opCode, addressing));
    }

    /**
     * Starts executing from the program counter in another thread
     */
    public void start() {
        runningInMainThread.set(false);
        thread.get().start();
    }

    /**
     * Starts executing from the program counter in the same thread.<br>
     * This is deprecated API, use {@link CentralProcessor#start()} instead
     */
    @Deprecated
    public void run() {
        runningInMainThread.set(true);
        thread.get().run();
    }

    /**
     * <p>Interrupts the execution thread.
     * If the execution was taking place in the main thread, it assumes that the caller is of the main thread
     * , so it interrupts the current thread.</p>
     */
    public void interruptThread() {
        thread.get().setRunning(false);
    }


    private void checkInstructionName(@NotNull Instruction instruction, InstructionName name) throws IOException {
        if (instruction.instructionNameProperty().get() != name) {
            throw new IOException("Wrong Instruction");
        }
    }

    private boolean loadRegister(@NotNull Register register, @NotNull Instruction instruction, InstructionName name) throws IOException {
        checkInstructionName(instruction, name);
        Registers.loadRegister(register, instruction.argumentsProperty().get()[0]);
        checkZeroAndNegative((Integer) register.getValue());
        return false;
    }

    private boolean storeRegister(@NotNull Register register, @NotNull Instruction instruction, InstructionName name) throws IOException {
        checkInstructionName(instruction, name);
        Registers.storeRegister(register, console.get(), instruction.argumentsProperty().get()[0]);
        checkZeroAndNegative((Byte) register.getValue());
        return false;
    }

    private boolean branchOnFlag(@NotNull String flagName, @NotNull Instruction instruction, boolean bool) throws IOException {
        final ProcessorStatus currentProcessorStatus = getProcessorStatus();
        flagName += "Property";
        try {
            final Method method = ProcessorStatus.class.getMethod(flagName);
            final BooleanProperty flagProperty = (BooleanProperty) method.invoke(currentProcessorStatus);
            final ProgramCounter programCounter = getProgramCounter();
            if ((bool && flagProperty.get()) || (! bool && ! flagProperty.get())) {
                return updateProgramCounter(programCounter,
                        (programCounter.getValue() + instruction.argumentsProperty().get()[0]));

            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateProgramCounter(@NotNull final ProgramCounter programCounter, Integer newValue) {
        programCounter.setValue(newValue);
        return true;
    }

}
