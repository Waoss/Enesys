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

public enum InstructionName {
    /**
     * Add with carry
     */
    ADC,
    /**
     * Logical And
     */
    AND,
    ASL,
    BCC,
    BCS,
    BEQ,
    BIT,
    BMI,
    BNE,
    BPL,
    BRK,
    BVC,
    BVS,
    CLC,
    CLD,
    CLI,
    CLV,
    CMP,
    CPX,
    CPY,
    DEC,
    DEX,
    DEY,
    EOR,
    INC,
    INX,
    INY,
    JMP,
    JSR,
    LDA,
    LDX,
    LDY,
    LSR,
    NOP,
    ORA,
    PHA,
    PHP,
    PLA,
    PLP,
    ROL,
    ROR,
    RTI,
    RTS,
    SBC,
    SEC,
    SED,
    SEI,
    STA,
    STX,
    STY,
    TAX,
    TAY,
    TSX,
    TXA,
    TXS,
    TYA;

    /**
     * Returns the corresponding {@link InstructionName} according to the opCode.
     *
     * @param opCode
     * @return
     */
    public static InstructionName getByOpCode(Byte opCode) {
        //TODO
        return null;
    }

    /**
     * Returns the opcode of this instruction name based on the addressing
     *
     * @param addressing The Addressing
     * @return The OpCode
     */
    public byte getOpCode(Addressing addressing) {
        //TODO
        switch (addressing) {
            case ABSOLUTE:
                switch (this) {
                    case ADC:
                        return 0x69;
                    case AND:
                        return 0x29;
                    case ASL:
                        return 0x0a;
                    case BIT:
                        return 0x2c;
                    case BRK:
                        return 0;
                    case CMP:
                        return (byte) 0xcd;
                    case CPX:
                        return (byte) 0xec;
                    case CPY:
                        return (byte) 0xcc;
                    case DEC:
                        return (byte) 0xce;
                    case DEX:
                        break;
                    case DEY:
                        break;
                    case EOR:
                        break;
                    case INC:
                        break;
                    case INX:
                        break;
                    case INY:
                        break;
                    case JMP:
                        break;
                    case JSR:
                        break;
                    case LDA:
                        break;
                    case LDX:
                        break;
                    case LDY:
                        break;
                    case LSR:
                        break;
                    case NOP:
                        break;
                    case ORA:
                        break;
                    case PHA:
                        break;
                    case PHP:
                        break;
                    case PLA:
                        break;
                    case PLP:
                        break;
                    case ROL:
                        break;
                    case ROR:
                        break;
                    case RTI:
                        break;
                    case RTS:
                        break;
                    case SBC:
                        break;
                    case SEC:
                        break;
                    case SED:
                        break;
                    case SEI:
                        break;
                    case STA:
                        break;
                    case STX:
                        break;
                    case STY:
                        break;
                    case TAX:
                        break;
                    case TAY:
                        break;
                    case TSX:
                        break;
                    case TXA:
                        break;
                    case TXS:
                        break;
                    case TYA:
                        break;
                }
        }
        return 0;
    }
}
