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

package com.waoss.enesys.cpu.registers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

abstract class UnsignedByteRegister extends Register<Byte> {

    protected byte defaultValue;
    protected SimpleObjectProperty<Byte> valueProperty;

    protected UnsignedByteRegister(byte defaultValue) {
        this.defaultValue = defaultValue;
        valueProperty = new SimpleObjectProperty<>(defaultValue);
    }

    public ObjectProperty<Byte> valueProperty() {
        return valueProperty;
    }

    @Override
    public Byte getValue() {
        return valueProperty.get();
    }

    @Override
    public void setValue(Byte value) {
        valueProperty.set(value);
    }
}
