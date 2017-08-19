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

package com.waoss.enesys.standalone6502;

import com.waoss.enesys.Console;
import org.apache.commons.lang3.ArrayUtils;

public class Emulator {

    private final Console console = new Console();
    private int[] binaries;

    {
        console.getCentralProcessor().addInstructionExecutionHandler((centralProcessor, executed) -> {
            System.out.println("Instruction executed was " + executed);
        });
    }

    public int[] getBinaries() {
        return binaries;
    }

    public void setBinaries(final int[] binaries) {
        this.binaries = binaries;
    }

    public void executeBinaries() {
        console.loadAndExecuteBinaries(ArrayUtils.remove(binaries, binaries.length - 1), 0x0600);
    }
}
