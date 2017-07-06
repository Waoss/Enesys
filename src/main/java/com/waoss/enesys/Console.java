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

package com.waoss.enesys;

import com.waoss.enesys.cpu.registers.*;
import com.waoss.enesys.mem.CompleteMemory;

public class Console {

    private CompleteMemory completeMemory = new CompleteMemory();
    private ProcessorStatus processorStatus = new ProcessorStatus();
    private AccumalativeRegister ARegister = new AccumalativeRegister();
    private XRegister XRegister = new XRegister();
    private YRegister YRegister = new YRegister();
    private StackPointer stackPointer = new StackPointer();
    private ProgramCounter programCounter = new ProgramCounter((short) 0x0600);

    public ProcessorStatus getProcessorStatus() {
        return processorStatus;
    }

    public void setProcessorStatus(ProcessorStatus processorStatus) {
        this.processorStatus = processorStatus;
    }

    public AccumalativeRegister getARegister() {
        return ARegister;
    }

    public void setARegister(AccumalativeRegister ARegister) {
        this.ARegister = ARegister;
    }

    public com.waoss.enesys.cpu.registers.XRegister getXRegister() {
        return XRegister;
    }

    public void setXRegister(com.waoss.enesys.cpu.registers.XRegister XRegister) {
        this.XRegister = XRegister;
    }

    public com.waoss.enesys.cpu.registers.YRegister getYRegister() {
        return YRegister;
    }

    public void setYRegister(com.waoss.enesys.cpu.registers.YRegister YRegister) {
        this.YRegister = YRegister;
    }

    public StackPointer getStackPointer() {
        return stackPointer;
    }

    public void setStackPointer(StackPointer stackPointer) {
        this.stackPointer = stackPointer;
    }

    public ProgramCounter getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(ProgramCounter programCounter) {
        this.programCounter = programCounter;
    }

    public CompleteMemory getCompleteMemory() {
        return completeMemory;
    }

    public void setCompleteMemory(CompleteMemory completeMemory) {
        this.completeMemory = completeMemory;
    }

    public void foo() {
        return;
    }
}
