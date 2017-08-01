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
import java.util.concurrent.atomic.AtomicReference;

public final class RandomAccessMemory implements Memory {

    private final AtomicReference<IntBuffer> buffer = new AtomicReference<>(IntBuffer.allocate(2048));

    public RandomAccessMemory() {
        super();
    }

    @Override
    public int read(int address) {
        return buffer.get().get(address);
    }

    @Override
    public void write(int address, int value) {
        buffer.set(buffer.get().put(address, value));
    }

    @Override
    public int size() {
        return buffer.get().capacity();
    }

}
