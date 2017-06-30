package com.waoss.enesys.cpu.registers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ProcessorStatus extends UnsignedByteRegister {

    BooleanProperty carryFlagEnabledProperty = new SimpleBooleanProperty(false);

    {
        this.carryFlagEnabledProperty.setValue(((this.getValue().byteValue() & (1 << 7)) > 0));
    }

    public ProcessorStatus() {
        super((byte) 0b00110000);
    }

    public boolean isCarryFlagEnabled() {
        return carryFlagEnabledProperty.get();
    }

    public void setCarryFlagEnabled(boolean carryFlagEnabled) {
        this.carryFlagEnabledProperty.setValue(carryFlagEnabled);
        if (carryFlagEnabled) {
            enableBit((byte) 7);
        } else {
            clearBit((byte) 7);
        }
    }

    public BooleanProperty carryFlagEnabledProperty() {
        return carryFlagEnabledProperty;
    }

    private void enableBit(byte index) {
        this.setValue((byte) (this.getValue().byteValue() | (1 << index)));
    }

    private void clearBit(byte index) {
        this.setValue(this.getValue().byteValue() & ~(1 << index));
    }
}
