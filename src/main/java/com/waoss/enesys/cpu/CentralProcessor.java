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
@Incomplete
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

    /********************************************************* Getters 

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

    // Instructions implementation 

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
     * Set the interrupt disable flag disallowing interrupts
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean sei(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.SEI);
        getProcessorStatus().setInterruptFlagEnabled(true);
        return false;
    }

    /**
     * Arithmetic shift left
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to the PC
     *
     * @throws ProcessingException
     *         If some shit happens
     */
    public boolean asl(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.ASL);
        final AccumalativeRegister accumalativeRegister = getARegister();
        accumalativeRegister.setValue(accumalativeRegister.getValue() << instruction.getArguments()[0]);
        checkZeroAndNegative(accumalativeRegister.getValue());
        return false;
    }

    /**
     * Clears the carry flag
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean clc(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.CLC);
        setFlag("carryFlagEnabled", false);
        return false;
    }

    /**
     * Clear interrupt disable allowing interrupts
     *
     * @param instruction
     *         The instruction
     *
     * @return false;no change to CP
     *
     * @throws ProcessingException
     *         if any shit happens
     */
    public boolean cli(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.CLI);
        setFlag("interruptFlagEnabled", false);
        return false;
    }

    /**
     * Clear overflow flag
     *
     * @param instruction
     *         The instruction
     *
     * @return false;no change top PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean clv(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.CLV);
        setFlag("overflowFlagEnabled", false);
        return false;
    }

    /**
     * Logical shift right
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean lsr(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.LSR);
        final AccumalativeRegister accumalativeRegister = getARegister();
        accumalativeRegister.setValue(accumalativeRegister.getValue() >> instruction.getArguments()[0]);
        return false;
    }

    /**
     * Logical bitwise OR
     *
     * @param instruction
     *         The instruction
     *
     * @return false;no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean ora(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.ORA);
        final AccumalativeRegister accumalativeRegister = getARegister();
        accumalativeRegister.setValue(accumalativeRegister.getValue() | instruction.getArguments()[0]);
        return false;
    }

    /**
     * Transfer A to X
     *
     * @param instruction
     *         The instruction
     *
     * @return false;no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean tax(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.TAX);
        transferRegister(getARegister(), getXRegister());
        return false;
    }

    /**
     * Transfer A to Y
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean tay(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.TAY);
        transferRegister(getARegister(), getYRegister());
        return false;
    }

    /**
     * Transfer Stack Pointer to X
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean tsx(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.TSX);
        transferRegister(getStackPointer(), getXRegister());
        return false;
    }

    /**
     * Transfer X to A
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean txa(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.TXA);
        transferRegister(getXRegister(), getARegister());
        return false;
    }

    /**
     * Transfer X to Stack Pointer
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean txs(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.TXS);
        transferRegister(getXRegister(), getStackPointer());
        return false;
    }

    /**
     * Transfer X to Stack Pointer
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     */
    public boolean tya(@NotNull Instruction instruction) throws ProcessingException {
        checkInstructionName(instruction, InstructionName.TYA);
        transferRegister(getYRegister(), getARegister());
        return false;
    }

    /**
     * Compares some value with the A register.
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     * @see #cpx(Instruction)
     * @see #cpy(Instruction)
     */
    public boolean cmp(@NotNull Instruction instruction) throws ProcessingException {
        final Integer toCompare = instruction.getArguments()[0];
        final Integer aRegisterValue = getARegister().getValue();
        compareRegisters(aRegisterValue, toCompare);
        return false;
    }

    /**
     * Compares some value with the X register.
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     * @see #cmp(Instruction)
     * @see #cpy(Instruction)
     */
    public boolean cpx(@NotNull Instruction instruction) throws ProcessingException {
        final Integer toCompare = instruction.getArguments()[0];
        final Integer xRegisterValue = getXRegister().getValue();
        compareRegisters(xRegisterValue, toCompare);
        return false;
    }

    /**
     * Compares some value with the Y register.
     *
     * @param instruction
     *         The instruction
     *
     * @return false; no change to PC
     *
     * @throws ProcessingException
     *         if some shit happens
     * @see #cmp(Instruction)
     * @see #cpx(Instruction)
     */
    public boolean cpy(@NotNull Instruction instruction) throws ProcessingException {
        final Integer toCompare = instruction.getArguments()[0];
        final Integer yRegisterValue = getXRegister().getValue();
        compareRegisters(yRegisterValue, toCompare);
        return false;
    }

    // End of Instructions implementation

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

    /**
     * Randomly hashes this object.
     *
     * @return the randomly hashed stuff.
     */
    @Override
    public int hashCode() {
        int result = console != null ? console.hashCode() : 0;
        result = 1341 * result + (thread != null ? thread.hashCode() : 0);
        result = 452 * result + (runningInMainThread != null ? runningInMainThread.hashCode() : 0);
        result ^= 14;
        return result;
    }

    /**
     * Returns true if the object given in the parameters is equal to this.<p>The fields taking into consideration are
     * the console, the CentralProcessingThread, and the
     * runningInMainThread property</p>
     *
     * @param o
     *         The object
     *
     * @return true if all the properties mentioned above are true.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CentralProcessor that = (CentralProcessor) o;

        if (console != null ? ! console.equals(that.console) : that.console != null) {
            return false;
        }
        if (thread != null ? ! thread.equals(that.thread) : that.thread != null) {
            return false;
        }
        return runningInMainThread != null ? runningInMainThread.equals(
                that.runningInMainThread) : that.runningInMainThread == null;
    }

    // Internal API 

    private void transferRegister(Register register1, Register register2) {
        register1.setValue(register2.getValue());
    }

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

    private void setFlag(String flagName, boolean newValue) {
        final ProcessorStatus currentProcessorStatus = getProcessorStatus();
        flagName += "Property";
        try {
            final Method method = ProcessorStatus.class.getMethod(flagName);
            final BooleanProperty flagProperty = (BooleanProperty) method.invoke(currentProcessorStatus);
            flagProperty.setValue(newValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void compareRegisters(Integer integer1, Integer integer2) {
        checkZeroAndNegative(integer1 - integer2);
        if (integer1 > integer2) {
            getProcessorStatus().setCarryFlagEnabled(true);
        }
    }

}
