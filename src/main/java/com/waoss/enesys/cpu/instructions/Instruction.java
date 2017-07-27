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

import com.waoss.enesys.mem.Addressing;
import javafx.beans.property.SimpleObjectProperty;

import java.util.concurrent.atomic.AtomicReference;

public final class Instruction {

    /**
     * This property holds the instruction name
     */
    private final AtomicReference<SimpleObjectProperty<InstructionName>> instructionName = new AtomicReference<>(new SimpleObjectProperty<>(this, "instructionName"));
    /**
     * This property holds the opcode
     */
    private final AtomicReference<SimpleObjectProperty<Integer>> opCode = new AtomicReference<>(new SimpleObjectProperty<>(this, "opCode"));
    /**
     * This property holds the addressing mode
     */
    private final AtomicReference<SimpleObjectProperty<Addressing>> addressing = new AtomicReference<>(new SimpleObjectProperty<>(this, "addressing"));
    /**
     * This property stores the arguments<br>
     * For example, a branching instruction would have one argument: where to go if condition is true
     */
    private final AtomicReference<SimpleObjectProperty<Integer[]>> arguments = new AtomicReference<>(new SimpleObjectProperty<>(this, "arguments"));

    /**
     * Creates a new Instruction when given the name and addressing
     *
     * @param instructionName The name
     * @param addressing      The Addressing mode
     */
    public Instruction(InstructionName instructionName, Addressing addressing) {
        this.instructionName.get().set(instructionName);
        this.addressing.get().set(addressing);
    }

    /**
     * Creates a new Instruction given the opcode and addressing
     *
     * @param opcode     The opcode
     * @param addressing The addressing mode
     */
    public Instruction(Integer opcode, Addressing addressing) {
        this.opCode.get().set(opcode);
        this.addressing.get().set(addressing);
        this.instructionName.get().set(InstructionName.getByOpCode(opcode));
    }

    public InstructionName getInstructionName() {
        return instructionName.get().get();
    }

    public void setInstructionName(InstructionName instructionName) {
        this.instructionName.get().set(instructionName);
    }

    public final SimpleObjectProperty<InstructionName> instructionNameProperty() {
        return instructionName.get();
    }

    public Integer getOpCode() {
        return opCode.get().get();
    }

    public void setOpCode(Integer opCode) {
        this.opCode.get().set(opCode);
    }

    public final SimpleObjectProperty<Integer> opCodeProperty() {
        return opCode.get();
    }

    public Addressing getAddressing() {
        return addressing.get().get();
    }

    public void setAddressing(Addressing addressing) {
        this.addressing.get().set(addressing);
    }

    public final SimpleObjectProperty<Addressing> addressingProperty() {
        return addressing.get();
    }

    public Object[] getArguments() {
        return arguments.get().get();
    }

    public void setArguments(Integer... arguments) {
        this.arguments.get().set(arguments);
    }

    public SimpleObjectProperty<Integer[]> argumentsProperty() {
        return arguments.get();
    }
}
