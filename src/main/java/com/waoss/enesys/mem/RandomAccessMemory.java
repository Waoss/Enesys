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

package com.waoss.enesys.mem;

import java.nio.IntBuffer;

public final class RandomAccessMemory extends Memory {

    private IntBuffer buffer = IntBuffer.allocate(2048);

    public RandomAccessMemory() {
        super();
    }

    @Override
    public int read(short address) {
        return buffer.get(address);
    }

    @Override
    public void write(short address, int value) {
        buffer = buffer.put(address, value);
    }
}
