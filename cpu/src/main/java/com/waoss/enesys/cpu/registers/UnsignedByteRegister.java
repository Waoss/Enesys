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
    public Number getValue() {
        return valueProperty.get();
    }

    @Override
    public void setValue(Number value) {
        Registers.requireUnsignedByte(value);
        valueProperty.set((Byte) value);
    }
}
