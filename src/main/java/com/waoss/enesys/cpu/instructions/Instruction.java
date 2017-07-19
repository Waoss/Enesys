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
import javafx.beans.property.SimpleObjectProperty;

public final class Instruction {

    private SimpleObjectProperty<InstructionName> instructionName = new SimpleObjectProperty<>(this, "instructionName");
    private SimpleObjectProperty<Byte> opCode = new SimpleObjectProperty<>(this, "opCode");
    private SimpleObjectProperty<Addresing> addresing = new SimpleObjectProperty<>(this, "addresing");

    public Instruction(InstructionName instructionName, Addresing addresing) {
        this.instructionName.set(instructionName);
        this.addresing.set(addresing);
    }

    public Instruction(Byte opcode, Addresing addresing) {
        this.opCode.set(opcode);
        this.addresing.set(addresing);
    }

    public InstructionName getInstructionName() {
        return instructionName.get();
    }

    public void setInstructionName(InstructionName instructionName) {
        this.instructionName.set(instructionName);
    }

    public SimpleObjectProperty<InstructionName> instructionNameProperty() {
        return instructionName;
    }

    public Byte getOpCode() {
        return opCode.get();
    }

    public void setOpCode(Byte opCode) {
        this.opCode.set(opCode);
    }

    public SimpleObjectProperty<Byte> opCodeProperty() {
        return opCode;
    }

    public Addresing getAddresing() {
        return addresing.get();
    }

    public void setAddresing(Addresing addresing) {
        this.addresing.set(addresing);
    }

    public SimpleObjectProperty<Addresing> addresingProperty() {
        return addresing;
    }
}
