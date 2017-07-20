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

import com.waoss.enesys.Console;
import com.waoss.enesys.cpu.instructions.Instruction;
import com.waoss.enesys.cpu.instructions.InstructionName;
import com.waoss.enesys.mem.Addressing;
import org.junit.Test;

public class CentralProcessorTest {

    Console targetConsole = new Console();
    CentralProcessor target = new CentralProcessor(targetConsole);

    @Test
    public void sta() throws Exception {
        Instruction instruction = new Instruction(InstructionName.STA, Addressing.ABSOLUTE);
        instruction.setArguments(0x0200);
        target.lda((byte) 42);
        target.sta(instruction);
        assert target.getARegister().getValue() == targetConsole.getCompleteMemory().read((short) 0x0200);
    }
}