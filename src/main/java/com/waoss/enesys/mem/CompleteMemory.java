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

import javafx.beans.property.SimpleObjectProperty;

public class CompleteMemory extends Memory {

    SimpleObjectProperty<RandomAccessMemory> randomAccessMemory = new SimpleObjectProperty<>(new RandomAccessMemory());

    public RandomAccessMemory getRandomAccessMemory() {
        return randomAccessMemory.get();
    }

    public void setRandomAccessMemory(RandomAccessMemory randomAccessMemory) {
        this.randomAccessMemory.set(randomAccessMemory);
    }

    public SimpleObjectProperty<RandomAccessMemory> randomAccessMemoryProperty() {
        return randomAccessMemory;
    }

    /**
     * Returns the value at the given address
     *
     * @param address The address
     * @return The value
     */
    @Override
    public int read(short address) {
        //guy wants RAM
        if (address < 2048) {
            return randomAccessMemory.get().read(address);
        }
        return 0;
    }

    /**
     * Writes the value to the given address
     *
     * @param address The address
     * @param value   The value
     */
    @Override
    public void write(short address, int value) {
        if (address < 2048) {
            randomAccessMemory.get().write(address, value);
        }
    }
}
