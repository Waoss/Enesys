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
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

public class CentralProcessorTest {

    @NotNull Console targetConsole = new Console();
    @NotNull CentralProcessor target = new CentralProcessor(targetConsole);
    @Test
    public void ldx() throws Exception {
        targetConsole.getCompleteMemory().write((short) 0x0600, 0xa2);
        targetConsole.getCompleteMemory().write((short) 0x0601, 0x44);
        target.start();
        Thread.sleep(3000);
        target.interruptThread();
    }

    @Test
    public void bcc() throws Exception {
        targetConsole.getProcessorStatus().setCarryFlagEnabled(false);
        targetConsole.getCompleteMemory().write((short) 0x0600, 0x90);
        targetConsole.getCompleteMemory().write((short) 0x0601, 0x0600);
        target.start();
        Thread.sleep(3000);
        target.interruptThread();
    }
}