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

/**
 * Represents a sort of a memory.An interface which may be implemented as CPU Memory, PPU Memory, Mapper Memory and
 * stuff.
 *
 * @see RandomAccessMemory
 * @see CompleteMemory
 */
public interface Memory {

    /**
     * Reads the value at the address specified in the parameter and returns the value at that address
     *
     * @param address
     *         The address
     *
     * @return The value at that address
     */
    int read(int address);

    /**
     * Writes the value at the address specified in the parameter.
     *
     * @param address
     *         The address to write the value to
     * @param value
     *         The value to write
     */
    void write(int address, int value);

    /**
     * Returns the size of the memory
     *
     * @return the size of the memory
     */
    int size();
}
