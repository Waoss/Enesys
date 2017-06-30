package com.waoss.enesys.cpu.registers;

import javafx.beans.property.SimpleObjectProperty;

class UnsignedByteRegister extends Register<Byte> {

    SimpleObjectProperty<Byte> valueProperty = new SimpleObjectProperty<>((byte) 0);

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
