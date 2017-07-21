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

import java.util.concurrent.atomic.AtomicReference;

/**
 * An implemented Register
 * All Registers extend this class
 *
 * @param <T> The type of the register
 */
public class RegisterImpl<T extends Number> extends Register<T> {

    /**
     * The default value of the register
     */
    protected final AtomicReference<SimpleObjectProperty<T>> defaultValue = new AtomicReference<>(new SimpleObjectProperty<>(this, "defaultValue"));

    /**
     * The property that stores the value
     * Reasons for using properties :
     * <ul>
     * <li>They can have {@link javafx.beans.value.ChangeListener}</li>
     * <li>They can be passed on and the values and can be changed, without passing the whole bean</li>
     * </ul>
     */
    protected final AtomicReference<SimpleObjectProperty<T>> valueProperty = new AtomicReference<>();


    /**
     * Creates a new <b>implemented</b> register
     *
     * @param defaultValue The default value of the register
     */
    protected RegisterImpl(T defaultValue) {
        this.defaultValue.get().set(defaultValue);
        valueProperty.set(new SimpleObjectProperty<>(defaultValue));
    }


    /**
     * Returns the property that contains the value
     *
     * @return the property that contains the value
     */
    public final ObjectProperty<T> valueProperty() {
        return valueProperty.get();
    }


    /**
     * Returns the value of the Register
     *
     * @return the value of the Register
     */
    @Override
    public T getValue() {
        return valueProperty.get().get();
    }


    /**
     * Sets the value of the register
     *
     * @param value the value to be set
     */
    @Override
    public void setValue(T value) {
        valueProperty.get().set(value);
    }
}
