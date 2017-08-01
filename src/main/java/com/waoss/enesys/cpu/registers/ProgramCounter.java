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

package com.waoss.enesys.cpu.registers;

/**
 * <p>The program counter is a register that stored the location of the current instruction.The PC of the 6502 was 2
 * bytes wide.The PC was not accessible through any instruction but could only be changed using a jump/jsr instruction
 * or a
 * branching.</p>
 */
public class ProgramCounter extends IntRegister {

    /**
     * <p>Creates a new program counter with the default value.<br>Usually,the PC is set by the OS.But the 6502 usually
     * did not run an OS.This means that the binaries were loaded by hardware and the PC was set accordingly.Enesys
     * loads binaries to 0x0600 and then binaries are free to change the program counter using not only the
     * jmp/jsr/branching but also using the 0xfffe,the reset vector(I guess that's what it's called).</p>
     *
     * @param defaultValue
     *         The default value
     */
    public ProgramCounter(int defaultValue) {
        super(defaultValue);
    }
}
