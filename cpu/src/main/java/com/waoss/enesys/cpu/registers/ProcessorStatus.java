package com.waoss.enesys.cpu.registers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public final class ProcessorStatus extends UnsignedByteRegister {

    private final BooleanProperty carryFlagEnabledProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty zeroFlagEnabledProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty interruptFlagEnabledProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty decimalFlagEnabledProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty breakFlagEnabledProperty = new SimpleBooleanProperty(false);
    //     private access means no one can use this guy.
//     It should be unsued
    private final BooleanProperty unusedFlagEnabledProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty overflowFlagEnabledProperty = new SimpleBooleanProperty(false);

    {
        this.carryFlagEnabledProperty.set(((this.getValue().byteValue() & (1)) > 0));
        this.zeroFlagEnabledProperty.set(((this.getValue().byteValue() & (1 << 1)) > 0));
        this.interruptFlagEnabledProperty.set(((this.getValue().byteValue() & (1 << 2)) > 0));
        this.decimalFlagEnabledProperty.set(((this.getValue().byteValue() & (1 << 3)) > 0));
        this.breakFlagEnabledProperty.set(((this.getValue().byteValue() & (1 << 4)) > 0));
        this.unusedFlagEnabledProperty.set(((this.getValue().byteValue() & (1 << 5)) > 0));
        this.overflowFlagEnabledProperty.set(((this.getValue().byteValue() & (1 << 6)) > 0));
    }

    public ProcessorStatus() {
        super((byte) 0b00110000);
    }

    public BooleanProperty carryFlagEnabledProperty() {
        return carryFlagEnabledProperty;
    }

    public boolean isCarryFlagEnabled() {
        return carryFlagEnabledProperty.get();
    }

    public void setCarryFlagEnabled(boolean carryFlagEnabled) {
        enableFlag(carryFlagEnabledProperty, carryFlagEnabled, 0);
    }

    public BooleanProperty zeroFlagEnabledProperty() {
        return zeroFlagEnabledProperty;
    }

    public boolean isZeroFlagEnabled() {
        return zeroFlagEnabledProperty.get();
    }

    public void setZeroFlagEnabled(boolean zeroFlagEnabled) {
        enableFlag(zeroFlagEnabledProperty, zeroFlagEnabled, 1);
    }

    public BooleanProperty interruptFlagEnabledProperty() {
        return interruptFlagEnabledProperty;
    }

    public boolean isInterruptFlagEnabled() {
        return interruptFlagEnabledProperty.get();
    }

    public void setInterruptFlagEnabled(boolean interruptFlagEnabled) {
        enableFlag(interruptFlagEnabledProperty, interruptFlagEnabled, 2);
    }

    public BooleanProperty decimalFlagEnabledProperty() {
        return decimalFlagEnabledProperty;
    }

    public boolean isDecimalFlagEnabled() {
        return decimalFlagEnabledProperty.get();
    }

    public void setDecimalFlagEnabled(boolean interruptFlagEnabled) {
        enableFlag(decimalFlagEnabledProperty, interruptFlagEnabled, 3);
    }

    public BooleanProperty breakFlagEnabledProperty() {
        return breakFlagEnabledProperty;
    }

    public boolean isBreakFlagEnabled() {
        return breakFlagEnabledProperty.get();
    }

    public void setBreakFlagEnabled(boolean breakFlagEnabled) {
        enableFlag(breakFlagEnabledProperty, breakFlagEnabled, 4);
    }

    public BooleanProperty overflowFlagEnabledProperty() {
        return overflowFlagEnabledProperty;
    }

    public boolean isOverflowFlagEnabled() {
        return overflowFlagEnabledProperty.get();
    }

    public void setOverflowFlagEnabled(boolean overflowFlagEnabled) {
        enableFlag(overflowFlagEnabledProperty, overflowFlagEnabled, 6);
    }

    private void enableFlag(BooleanProperty property, boolean value, int index) {
        property.set(value);
        if (value) {
            enableBit((byte) index);
        } else {
            clearBit((byte) index);
        }
    }

    private void enableBit(byte index) {
        this.setValue((byte) (this.getValue().byteValue() | (1 << index)));
    }

    private void clearBit(byte index) {
        this.setValue((byte) (this.getValue().byteValue() & ~(1 << index)));
    }

}
