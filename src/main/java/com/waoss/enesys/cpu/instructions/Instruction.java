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

package com.waoss.enesys.cpu.instructions;

import com.waoss.enesys.mem.Addresing;

public final class Instruction {

    private InstructionName instructionName;
    private Byte opCode;
    private Addresing addresing;

    public Instruction(InstructionName instructionName, Addresing addresing) {
        this.instructionName = instructionName;
        this.addresing = addresing;
    }

    public Instruction(Byte opcode, Addresing addresing) {
        this.opCode = opcode;
        this.addresing = addresing;
    }

    public InstructionName getInstructionName() {
        return instructionName;
    }

    public byte getOpCode() {
        return opCode;
    }

    public Addresing getAddresing() {
        return addresing;
    }
}
