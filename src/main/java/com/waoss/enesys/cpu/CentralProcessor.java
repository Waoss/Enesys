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

import com.google.gson.GsonBuilder;
import com.waoss.enesys.Console;
import com.waoss.enesys.cpu.registers.*;
import com.waoss.enesys.mem.CompleteMemory;

/**
 * An instance of this class represents the CPU of the NES<br>
 */
public final class CentralProcessor implements Cloneable {

    private CompleteMemory completeMemory;
    private ProcessorStatus processorStatus;
    private AccumalativeRegister ARegister;
    private XRegister XRegister;
    private YRegister YRegister;
    private StackPointer stackPointer;
    private ProgramCounter programCounter;
    private Console console;

    /**
     * Creates a new Processor for the console.
     *
     * @param console The console
     */
    public CentralProcessor(Console console) {
        this.console = console;
        this.completeMemory = console.getCompleteMemory();
        this.ARegister = console.getARegister();
        this.XRegister = console.getXRegister();
        this.YRegister = console.getYRegister();
        this.processorStatus = console.getProcessorStatus();
        this.programCounter = console.getProgramCounter();
        this.stackPointer = console.getStackPointer();
    }

    /**
     * Creates a clone by copying all the properties
     * @param centralProcessor the processor to clone
     */
    public CentralProcessor(CentralProcessor centralProcessor) {
        this(centralProcessor.console);
    }

    /**
     * Returns a clone of this processor
     * @return A clone
     * @throws CloneNotSupportedException Never
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CentralProcessor(this);
    }

    /**
     * Returns the console the processor is part of
     * @return the console the processor is part of
     */
    public Console getConsole() {
        return console;
    }

    public CompleteMemory getCompleteMemory() {
        return completeMemory;
    }

    public ProcessorStatus getProcessorStatus() {
        return processorStatus;
    }

    public AccumalativeRegister getARegister() {
        return ARegister;
    }

    public XRegister getXRegister() {
        return XRegister;
    }

    public YRegister getYRegister() {
        return YRegister;
    }

    public StackPointer getStackPointer() {
        return stackPointer;
    }

    public ProgramCounter getProgramCounter() {
        return programCounter;
    }

    public void lda(byte value) {
        Registers.loadRegister(this.ARegister, value);
    }

    public void sta(int index) {
        Registers.storeRegister(this.ARegister, console, index);
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(this);
    }
}
