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

package com.waoss.enesys;

import com.waoss.enesys.cpu.CentralProcessor;

public class Enesys {

    static Console console;
    static CentralProcessor centralProcessor;

    public static void main(String... args) {
        console = new Console();
        centralProcessor = new CentralProcessor(console);
        centralProcessor.lda((byte) 10);
        centralProcessor.sta(0x0200);
        assert centralProcessor.getConsole() == console;
    }
}
