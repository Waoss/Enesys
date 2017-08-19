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

import com.waoss.enesys.cpu.CentralProcessor;
import com.waoss.enesys.mem.Addressing;
import javafx.beans.property.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents an instruction of the NES processor.<br>
 * <p>An instruction object contains all the data : the name of the instruction,the opcode,the addressing mode,the
 * arguments required for the instruction (a jump instruction takes in the new value of the program counter along)</p>
 * For example,
 * {@code
 * Instruction instruction = new Instruction(InstructionName.ADC, Addressing.ABSOLUTE);
 * instruction.getOpCode(); // works fine
 * instruction.setArguments(5); // the arguments
 * }
 * <p>Addressing is also handled because every time there is a change in the arguments;their is an update according to
 * the addressing mode</p>
 */
public final class Instruction {

    /**
     * This property holds the instruction name
     */
    private final AtomicReference<SimpleObjectProperty<InstructionName>> instructionName = new AtomicReference<>(
            new SimpleObjectProperty<>(this, "instructionName"));
    /**
     * This property holds the opcode
     */
    private final AtomicReference<SimpleObjectProperty<Integer>> opCode = new AtomicReference<>(
            new SimpleObjectProperty<>(this, "opCode"));
    /**
     * This property holds the addressing mode
     */
    private final AtomicReference<SimpleObjectProperty<Addressing>> addressing = new AtomicReference<>(new SimpleObjectProperty<>(this, "addressing"));
    /**
     * This property stores the arguments<br>
     * For example, a branching instruction would have one argument: where to go if condition is true
     */
    private final AtomicReference<SimpleObjectProperty<Integer[]>> arguments = new AtomicReference<>(
            new SimpleObjectProperty<>(this, "arguments"));

    private final AtomicReference<IntegerProperty> size = new AtomicReference<>(
            new SimpleIntegerProperty(this, "size", 0));

    private final AtomicReference<SimpleObjectProperty<CentralProcessor>> centralProcessor = new AtomicReference<>(
            new SimpleObjectProperty<>(this, "centralProcessor"));

    {
        /*
        This ensures that if arguments change the size also changes because size is actually the length of the arguments
        and the addressing mode is recognised an the arguments
        are parsed accordingly.
        */
        arguments.get().addListener((observable, oldValue, newValue) -> size.get().set(newValue.length));
    }

    /**
     * Creates a new Instruction when given the name and addressing
     *
     * @param instructionName
     *         The name
     * @param addressing
     *         The Addressing mode
     */
    public Instruction(InstructionName instructionName, Addressing addressing) {
        this.instructionName.get().set(instructionName);
        this.addressing.get().set(addressing);
    }

    /**
     * Creates a new Instruction given the opcode and addressing
     *
     * @param opcode
     *         The opcode
     * @param addressing
     *         The addressing mode
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

    public final ObjectProperty<InstructionName> instructionNameProperty() {
        return instructionName.get();
    }

    public Integer getOpCode() {
        return opCode.get().get();
    }

    public void setOpCode(Integer opCode) {
        this.opCode.get().set(opCode);
    }

    public final ObjectProperty<Integer> opCodeProperty() {
        return opCode.get();
    }

    public Addressing getAddressing() {
        return addressing.get().get();
    }

    public void setAddressing(Addressing addressing) {
        this.addressing.get().set(addressing);
    }

    public final ObjectProperty<Addressing> addressingProperty() {
        return addressing.get();
    }

    public Integer[] getArguments() {
        return arguments.get().get();
    }

    public void setArguments(Integer... arguments) {
        this.arguments.get().set(arguments);
    }

    public ObjectProperty<Integer[]> argumentsProperty() {
        return arguments.get();
    }

    public int getSize() {
        return size.get().get();
    }

    public void setSize(int size) {
        this.size.get().set(size);
    }

    public final IntegerProperty sizeProperty() {
        return size.get();
    }

    public CentralProcessor getCentralProcessor() {
        return centralProcessor.get().get();
    }

    public void setCentralProcessor(final CentralProcessor centralProcessor) {
        this.centralProcessor.get().set(centralProcessor);
    }

    public final ObjectProperty<CentralProcessor> centralProcessorProperty() {
        return centralProcessor.get();
    }

    /**
     * Parses itself according to the arguments and addressing
     */
    public void parseArgumentsAccordingToAddressing() {
        final Addressing addressing = getAddressing();
        final Integer[] givenArguments = argumentsProperty().get();
        /*
        If there are no arguments it is safe to assume that the instruction does not require any results from us.
        This type of addressing is handled but not inferring nullity is shitty af.Wish I was using Kotlin
        */
        final Integer[] resultArguments = givenArguments != null ? Arrays.copyOf(givenArguments, givenArguments.length) : null;
        final CentralProcessor centralProcessor = getCentralProcessor();
        switch (addressing) {
            case ABSOLUTE:
                if (resultArguments != null) {
                    resultArguments[0] = centralProcessor.getCompleteMemory().read(givenArguments[0]);
                }
                break;
            case ABSOLUTE_X:
                if (resultArguments != null) {
                    resultArguments[0] = givenArguments[0] + centralProcessor.getXRegister().getValue();
                }
                break;
            case ABSOLUTE_Y:
                if (resultArguments != null) {
                    resultArguments[0] = givenArguments[0] + centralProcessor.getYRegister().getValue();
                }
                break;
            case ACCUMULATOR:
                break;
            case IMMEDIATE:
                if (resultArguments != null) {
                    resultArguments[0] = givenArguments[0];
                }
                break;
            case IMPLIED:
                break;
            case INDEXED_INDIRECT:
                Integer postXAddress = null;
                if (givenArguments != null) {
                    postXAddress = givenArguments[0] + centralProcessor.getXRegister().getValue();
                }
                Integer mostSignificantByte = centralProcessor.getCompleteMemory().read(postXAddress + 1);
                Integer leastSignificantByte = centralProcessor.getCompleteMemory().read(postXAddress);
                Integer finalAddress = (mostSignificantByte * 0x0100) + leastSignificantByte;
                if (resultArguments != null) {
                    resultArguments[0] = centralProcessor.getCompleteMemory().read(finalAddress);
                }
                break;
            case INDIRECT:
                mostSignificantByte = givenArguments != null ? givenArguments[1] : null;
                leastSignificantByte = givenArguments != null ? givenArguments[0] : null;
                finalAddress = (mostSignificantByte * 0x0100) + leastSignificantByte;
                if (resultArguments != null) {
                    resultArguments[0] = centralProcessor.getCompleteMemory().read(finalAddress);
                }
                break;
            case INDIRECT_INDEXED:
                Integer addressToLookup = givenArguments != null ? givenArguments[0] : null;
                mostSignificantByte = centralProcessor.getCompleteMemory().read(addressToLookup + 1);
                leastSignificantByte = centralProcessor.getCompleteMemory().read(addressToLookup);
                finalAddress = (mostSignificantByte * 0x0100) + leastSignificantByte;
                finalAddress += centralProcessor.getYRegister().getValue();
                if (resultArguments != null) {
                    resultArguments[0] = centralProcessor.getCompleteMemory().read(finalAddress);
                }
                break;
            case RELATIVE:
                Byte rawAddress = givenArguments != null ? givenArguments[0].byteValue() : 0;
                if (resultArguments != null) {
                    resultArguments[0] = rawAddress.intValue();
                }
                break;
            case ZERO_PAGE:
                addressToLookup = givenArguments != null ? givenArguments[0] : null;
                if (resultArguments != null) {
                    resultArguments[0] = centralProcessor.getCompleteMemory().read(addressToLookup);
                }
                break;
            case ZERO_PAGE_X:
                Integer zeroPageAddressToLookup = givenArguments != null ? givenArguments[0] : 0;
                if (resultArguments != null) {
                    resultArguments[0] = centralProcessor.getCompleteMemory().read(zeroPageAddressToLookup);
                }
                if (resultArguments != null) {
                    resultArguments[0] += centralProcessor.getXRegister().getValue();
                }
                break;
            case ZERO_PAGE_Y:
                zeroPageAddressToLookup = givenArguments != null ? givenArguments[0] : 0;
                if (resultArguments != null) {
                    resultArguments[0] = centralProcessor.getCompleteMemory().read(zeroPageAddressToLookup);
                }
                if (resultArguments != null) {
                    resultArguments[0] += centralProcessor.getYRegister().getValue();
                }
                break;
        }
        setArguments(resultArguments);
    }
}
