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
import com.waoss.enesys.ProcessingException;
import com.waoss.enesys.annotations.Incomplete;
import com.waoss.enesys.cpu.instructions.Instruction;
import com.waoss.enesys.cpu.instructions.InstructionName;
import com.waoss.enesys.cpu.registers.*;
import com.waoss.enesys.mem.CompleteMemory;
import javafx.beans.property.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>An instance of this class represents the CPU of the NES
 * The CPU of the NES had only 6 registers.All of them,except the {@link ProgramCounter} were 8 bits wide.
 * Each and every instruction of the NES is implemented as a method that takes in an {@link Instruction}.The
 * instruction
 * object contains
 * all the required data to execute the instruction.Every method also returns a boolean which is true, if and only if
 * any change to the program
 * counter was done.The {@link #process(Instruction)} method takes in an instruction object and finds the method to
 * invoke using reflection.
 * All shit(processing) happens in another thread.Which is stored in an atomic reference for thread safety.It is
 * <strong>strongly recommended</strong> that the {@link #run()}
 * method not be used unless for debugging, for it runs stuff in the thread that calls it and the loop is infinite
 * unless it be stopped by the {@link #interruptThread()} method.
 * </p>
 *
 * @see #process(Instruction)
 */
public final class CentralProcessor implements Cloneable {

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

    /**
     * Returns the thread in which work is happening
     *
     * @return the thread in which work is happening
     */
    public CentralProcessingThread getThread() {
        return thread.get();
    }

    /**
     * Loads a value into the A register.
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#LDA}
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     */
    public boolean lda(@NotNull Instruction instruction) throws ProcessingException {
        return loadRegister(getARegister(), instruction, InstructionName.LDA);
    }

    /**
     * <p>Stores the value of the A register into the index specified
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#STA}</p>
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     */
    public boolean sta(@NotNull Instruction instruction) throws ProcessingException {
        return storeRegister(getARegister(), instruction, InstructionName.STA);
    }

    /**
     * <p>Stores the value of the X register into the index specified
     * Implementation of {@link InstructionName#LDX}</p>
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     *
     * @throws ProcessingException
     *         If the instruction is not valid
     */
    public boolean ldx(@NotNull Instruction instruction) throws ProcessingException {
        return loadRegister(getXRegister(), instruction, InstructionName.LDX);
    }

    /**
     * <p>Stores the value stored in the X register into the address specified</p>
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     *
     * @throws ProcessingException
     *         If the instruction is not valid
     */
    public boolean stx(@NotNull Instruction instruction) throws ProcessingException {
        return storeRegister(getXRegister(), instruction, InstructionName.STX);
    }

    /**
     * <p>Increments the value of the X register by 1</p>
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     */
    public boolean inx(Instruction instruction) {
        getXRegister().setValue((getXRegister().getValue() + 1));
        return false;
    }

    /**
     * <p>Stores the value of the Y register into the index specified
     * Implementation of {@link InstructionName#LDY}</p>
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     *
     * @throws ProcessingException
     *         If the instruction is not valid
     */
    public boolean ldy(@NotNull Instruction instruction) throws ProcessingException {
        loadRegister(getYRegister(), instruction, InstructionName.LDY);
        return false;
    }

    /**
     * <p>Stores the value stored in the Y register into the address specified</p>
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     *
     * @throws ProcessingException
     *         If the instruction is not valid
     */
    public boolean sty(@NotNull Instruction instruction) throws ProcessingException {
        return storeRegister(getYRegister(), instruction, InstructionName.STY);
    }

    /**
     * <p>Increments the value of the Y register by 1</p>
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
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
     *
     * @return false; because this instruction does not change the Program counter
     */
    public boolean brk(Instruction instruction) {
        interruptThread();
        return false;
    }

    /**
     * No operation.Does nothing.
     *
     * @param instruction
     *         The instruction.
     *
     * @return false; no operation = no change in PC
     */
    public boolean nop(@Nullable Instruction instruction) {
        return false;
    }

    /**
     * Logical AND
     * Stores into the accumalator the "AND" of the value and the previous value
     * Implementation of {@link com.waoss.enesys.cpu.instructions.InstructionName#AND}
     *
     * @param instruction
     *         The instruction
     *
     * @return false; because this instruction does not change the Program counter
     */
    public boolean and(@NotNull Instruction instruction) throws ProcessingException {
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
     *
     * @return false; because this instruction does not change the Program counter
     */
    public boolean adc(@NotNull Instruction instruction) throws ProcessingException {
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
     * @return True if branching happened
     *
     * @throws ProcessingException
     *         If some shit happens
     */
    public boolean bcc(@NotNull Instruction instruction) throws ProcessingException {
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
     * @return True if branching happened
     *
     * @throws ProcessingException
     *         If some shit happens
     */
    public boolean bcs(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.BCS);
        return branchOnFlag("carryFlagEnabled", instruction, true);
    }

    /**
     * Branch on overflow clear
     *
     * @param instruction
     *         The instruction
     *
     * @return True if branching happened.
     *
     * @throws ProcessingException
     *         If some shit happens
     */
    public boolean bvc(@NotNull Instruction instruction) throws ProcessingException {
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
     * @throws ProcessingException
     *         If some shit happens
     */
    public boolean bvs(@NotNull Instruction instruction) throws ProcessingException {
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
     * @throws ProcessingException
     *         If some shit happens
     */
    public boolean sec(@NotNull Instruction instruction) throws ProcessingException {
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
     * @throws ProcessingException
     *         If some shit happens
     */
    public boolean sed(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.SED);
        getProcessorStatus().setDecimalFlagEnabled(true);
        return false;
    }

    /**
     * <p>Processes an instruction.
     * Invokes the required method for the instruction implementation.The method to invoke is found dynamically using
     * reflection.
     * All instruction implementation methods have the same name as the instruction in lower case.The instruction name
     * from the instruction
     * object given as a parameter is converted to lowercase using {@link String#toLowerCase()}.Then a method of that
     * name is invoked.The method is still incomplete as there is no complete handling of the updation of the program
     * counter.</p>
     *
     * @param instruction
     *         The instruction
     */
    @Incomplete
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

    /********************************************************* Internal API *********************************************************/

    private void checkZeroAndNegative(int value) {
        if (value == 0) {
            getProcessorStatus().setZeroFlagEnabled(true);
        } else if (value < 0) {
            getProcessorStatus().setNegativeFlagEnabled(true);
        }
    }

    private void checkInstructionName(@NotNull Instruction instruction, InstructionName name) throws
            ProcessingException {
        if (instruction.instructionNameProperty().get() != name) {
            throw new ProcessingException("Wrong Instruction");
        }
    }

    private boolean loadRegister(@NotNull Register register, @NotNull Instruction instruction, InstructionName name) throws
            ProcessingException {
        checkInstructionName(instruction, name);
        Registers.loadRegister(register, instruction.argumentsProperty().get()[0]);
        checkZeroAndNegative((Integer) register.getValue());
        return false;
    }

    private boolean storeRegister(@NotNull Register register, @NotNull Instruction instruction, InstructionName name) throws
            ProcessingException {
        checkInstructionName(instruction, name);
        Registers.storeRegister(register, console.get(), instruction.argumentsProperty().get()[0]);
        checkZeroAndNegative((Byte) register.getValue());
        return false;
    }

    private boolean branchOnFlag(@NotNull String flagName, @NotNull Instruction instruction, boolean bool) throws
            ProcessingException {
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
