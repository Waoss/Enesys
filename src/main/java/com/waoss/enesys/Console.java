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

import java.util.concurrent.atomic.AtomicReference;

public class Console {

    private final AtomicReference<CompleteMemory> completeMemory = new AtomicReference<>(new CompleteMemory());
    private final AtomicReference<ProcessorStatus> processorStatus = new AtomicReference<>(new ProcessorStatus());
    private final AtomicReference<AccumalativeRegister> ARegister = new AtomicReference<>(new AccumalativeRegister());
    private final AtomicReference<XRegister> XRegister = new AtomicReference<>(new XRegister());
    private final AtomicReference<YRegister> YRegister = new AtomicReference<>(new YRegister());
    private final AtomicReference<StackPointer> stackPointer = new AtomicReference<>(new StackPointer());
    private final AtomicReference<ProgramCounter> programCounter = new AtomicReference<>(new ProgramCounter((short) 0x0600));

    public ProcessorStatus getProcessorStatus() {
        return processorStatus.get();
    }

    public void setProcessorStatus(ProcessorStatus processorStatus) {
        this.processorStatus.set(processorStatus);
    }

    public AccumalativeRegister getARegister() {
        return ARegister.get();
    }

    public void setARegister(AccumalativeRegister ARegister) {
        this.ARegister.set(ARegister);
    }

    public com.waoss.enesys.cpu.registers.XRegister getXRegister() {
        return XRegister.get();
    }

    public void setXRegister(com.waoss.enesys.cpu.registers.XRegister XRegister) {
        this.XRegister.set(XRegister);
    }

    public com.waoss.enesys.cpu.registers.YRegister getYRegister() {
        return YRegister.get();
    }

    public void setYRegister(com.waoss.enesys.cpu.registers.YRegister YRegister) {
        this.YRegister.set(YRegister);
    }

    public StackPointer getStackPointer() {
        return stackPointer.get();
    }

    public void setStackPointer(StackPointer stackPointer) {
        this.stackPointer.set(stackPointer);
    }

    public ProgramCounter getProgramCounter() {
        return programCounter.get();
    }

    public void setProgramCounter(ProgramCounter programCounter) {
        this.programCounter.set(programCounter);
    }

    public CompleteMemory getCompleteMemory() {
        return completeMemory.get();
    }

    public void setCompleteMemory(CompleteMemory completeMemory) {
        this.completeMemory.set(completeMemory);
    }

}
