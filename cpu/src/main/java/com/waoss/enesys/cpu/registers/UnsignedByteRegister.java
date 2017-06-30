package com.waoss.enesys.cpu.registers;

import javafx.beans.property.SimpleObjectProperty;

abstract class UnsignedByteRegister extends Register<Byte> {

    byte defaultValue;
    SimpleObjectProperty<Byte> valueProperty;

    protected UnsignedByteRegister(byte defaultValue) {
        this.defaultValue = defaultValue;
        valueProperty = new SimpleObjectProperty<>(defaultValue);
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
