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

public class InstructionConstants {

    /**
     * <p>Stores the names of the instructions such that the value at [opcode] is the name of the instruction.
     * For example,
     * {@code
     * assert "ADC".equals(instructions[0x69]);
     * }
     * Will <i>never</i> throw AssertionError</p>
     */
    public static final String[] instructions =
            {
                    "BRK", "ORA", "KIL", "SLO", "NOP", "ORA", "ASL", "SLO",
                    "PHP", "ORA", "ASL", "ANC", "NOP", "ORA", "ASL", "SLO",
                    "BPL", "ORA", "KIL", "SLO", "NOP", "ORA", "ASL", "SLO",
                    "CLC", "ORA", "NOP", "SLO", "NOP", "ORA", "ASL", "SLO",
                    "JSR", "AND", "KIL", "RLA", "BIT", "AND", "ROL", "RLA",
                    "PLP", "AND", "ROL", "ANC", "BIT", "AND", "ROL", "RLA",
                    "BMI", "AND", "KIL", "RLA", "NOP", "AND", "ROL", "RLA",
                    "SEC", "AND", "NOP", "RLA", "NOP", "AND", "ROL", "RLA",
                    "RTI", "EOR", "KIL", "SRE", "NOP", "EOR", "LSR", "SRE",
                    "PHA", "EOR", "LSR", "ALR", "JMP", "EOR", "LSR", "SRE",
                    "BVC", "EOR", "KIL", "SRE", "NOP", "EOR", "LSR", "SRE",
                    "CLI", "EOR", "NOP", "SRE", "NOP", "EOR", "LSR", "SRE",
                    "RTS", "ADC", "KIL", "RRA", "NOP", "ADC", "ROR", "RRA",
                    "PLA", "ADC", "ROR", "ARR", "JMP", "ADC", "ROR", "RRA",
                    "BVS", "ADC", "KIL", "RRA", "NOP", "ADC", "ROR", "RRA",
                    "SEI", "ADC", "NOP", "RRA", "NOP", "ADC", "ROR", "RRA",
                    "NOP", "STA", "NOP", "SAX", "STY", "STA", "STX", "SAX",
                    "DEY", "NOP", "TXA", "XAA", "STY", "STA", "STX", "SAX",
                    "BCC", "STA", "KIL", "AHX", "STY", "STA", "STX", "SAX",
                    "TYA", "STA", "TXS", "TAS", "SHY", "STA", "SHX", "AHX",
                    "LDY", "LDA", "LDX", "LAX", "LDY", "LDA", "LDX", "LAX",
                    "TAY", "LDA", "TAX", "LAX", "LDY", "LDA", "LDX", "LAX",
                    "BCS", "LDA", "KIL", "LAX", "LDY", "LDA", "LDX", "LAX",
                    "CLV", "LDA", "TSX", "LAS", "LDY", "LDA", "LDX", "LAX",
                    "CPY", "CMP", "NOP", "DCP", "CPY", "CMP", "DEC", "DCP",
                    "INY", "CMP", "DEX", "AXS", "CPY", "CMP", "DEC", "DCP",
                    "BNE", "CMP", "KIL", "DCP", "NOP", "CMP", "DEC", "DCP",
                    "CLD", "CMP", "NOP", "DCP", "NOP", "CMP", "DEC", "DCP",
                    "CPX", "SBC", "NOP", "ISC", "CPX", "SBC", "INC", "ISC",
                    "INX", "SBC", "NOP", "SBC", "CPX", "SBC", "INC", "ISC",
                    "BEQ", "SBC", "KIL", "ISC", "NOP", "SBC", "INC", "ISC",
                    "SED", "SBC", "NOP", "ISC", "NOP", "SBC", "INC", "ISC",
            };
    /**
     * The addressings stored in such a manner that the opcode is the index.
     */
    public static final Addressing[] addressings =
            {
                    Addressing.IMPLIED, Addressing.INDEXED_INDIRECT, Addressing.IMPLIED, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ACCUMULATOR, Addressing.IMMEDIATE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X,
                    Addressing.ABSOLUTE, Addressing.INDEXED_INDIRECT, Addressing.IMPLIED, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ACCUMULATOR, Addressing.IMMEDIATE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X,
                    Addressing.IMPLIED, Addressing.INDEXED_INDIRECT, Addressing.IMPLIED, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ACCUMULATOR, Addressing.IMMEDIATE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X,
                    Addressing.IMPLIED, Addressing.INDEXED_INDIRECT, Addressing.IMPLIED, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ACCUMULATOR, Addressing.IMMEDIATE, Addressing.INDIRECT, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X,
                    Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_Y, Addressing.ZERO_PAGE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_Y,
                    Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_Y, Addressing.ZERO_PAGE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_Y,
                    Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X,
                    Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.IMMEDIATE, Addressing.INDEXED_INDIRECT, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.ZERO_PAGE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.IMPLIED, Addressing.IMMEDIATE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE, Addressing.ABSOLUTE,
                    Addressing.RELATIVE, Addressing.INDIRECT_INDEXED, Addressing.IMPLIED, Addressing.INDIRECT_INDEXED, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.ZERO_PAGE_X, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.IMPLIED, Addressing.ABSOLUTE_Y, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X, Addressing.ABSOLUTE_X,
            };
}
