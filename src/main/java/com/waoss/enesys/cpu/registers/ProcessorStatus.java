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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Arrays;
import java.util.List;

/**
 * An instance of this class represents the "status" of a processor<br>
 * The processor status was a special register that stored meta-data of the 6502.<br>
 * They have been implemented as JavaFX properties
 */
public final class ProcessorStatus extends UnsignedByteRegister {

    /**
     * Carry Flag : set when the last operation resulted in some sort of carry
     */
    private final BooleanProperty carryFlagEnabledProperty = new SimpleBooleanProperty(false);
    /**
     * Zero Flag :set when the last operation yielded zero
     */
    private final BooleanProperty zeroFlagEnabledProperty = new SimpleBooleanProperty(false);
    /**
     * Interrupt Flag : not as sure when it's set(something to work on)
     */
    private final BooleanProperty interruptFlagEnabledProperty = new SimpleBooleanProperty(true);
    /**
     * Decimal Flag : no use to us(The NES disabled Binary Coded Decimals of the 6502)
     */
    private final BooleanProperty decimalFlagEnabledProperty = new SimpleBooleanProperty(false);
    /**
     * Break Flag : for break instructions(BRK)
     */
    private final BooleanProperty breakFlagEnabledProperty = new SimpleBooleanProperty(true);
    /**
     * Unused Flag : never used
     */
    private final BooleanProperty unusedFlagEnabledProperty = new SimpleBooleanProperty(true);
    /**
     * Overflow Flag : set when the last operation resulted in some sort of overflow
     */
    private final BooleanProperty overflowFlagEnabledProperty = new SimpleBooleanProperty(false);
    /**
     * Negative Flag : set when an operation yields a negative value
     */
    private final BooleanProperty negativeFlagEnabledProperty = new SimpleBooleanProperty(false);

    /*
     * Sets the values of all the properties.
     * The strange left shift just tells us where some bit is.
     */
    {
        this.carryFlagEnabledProperty.set(((this.getValue() & (1)) > 0));
        this.zeroFlagEnabledProperty.set(((this.getValue() & (1 << 1)) > 0));
        this.interruptFlagEnabledProperty.set(!((this.getValue() & (1 << 2)) > 0));
        this.decimalFlagEnabledProperty.set(((this.getValue() & (1 << 3)) > 0));
        this.breakFlagEnabledProperty.set(((this.getValue() & (1 << 4)) > 0));
        this.unusedFlagEnabledProperty.set(((this.getValue() & (1 << 5)) > 0));
        this.overflowFlagEnabledProperty.set(((this.getValue() & (1 << 6)) > 0));
        this.negativeFlagEnabledProperty.set(((this.getValue() & (1 << 7)) > 0));
    }

    public ProcessorStatus() {
        super((byte) 0b00110100);
    }

    public final BooleanProperty carryFlagEnabledProperty() {
        return carryFlagEnabledProperty;
    }

    public boolean isCarryFlagEnabled() {
        return carryFlagEnabledProperty.get();
    }

    public void setCarryFlagEnabled(boolean carryFlagEnabled) {
        enableFlag(carryFlagEnabledProperty, carryFlagEnabled, 0);
    }

    public final BooleanProperty zeroFlagEnabledProperty() {
        return zeroFlagEnabledProperty;
    }

    public boolean isZeroFlagEnabled() {
        return zeroFlagEnabledProperty.get();
    }

    public void setZeroFlagEnabled(boolean zeroFlagEnabled) {
        enableFlag(zeroFlagEnabledProperty, zeroFlagEnabled, 1);
    }

    public final BooleanProperty interruptFlagEnabledProperty() {
        return interruptFlagEnabledProperty;
    }

    public boolean isInterruptFlagEnabled() {
        return interruptFlagEnabledProperty.get();
    }

    public void setInterruptFlagEnabled(boolean interruptFlagEnabled) {
        enableFlag(interruptFlagEnabledProperty, !interruptFlagEnabled, 2);
    }

    public final BooleanProperty decimalFlagEnabledProperty() {
        return decimalFlagEnabledProperty;
    }

    public boolean isDecimalFlagEnabled() {
        return decimalFlagEnabledProperty.get();
    }

    public void setDecimalFlagEnabled(boolean interruptFlagEnabled) {
        enableFlag(decimalFlagEnabledProperty, interruptFlagEnabled, 3);
    }

    public final BooleanProperty breakFlagEnabledProperty() {
        return breakFlagEnabledProperty;
    }

    public boolean isBreakFlagEnabled() {
        return breakFlagEnabledProperty.get();
    }

    public void setBreakFlagEnabled(boolean breakFlagEnabled) {
        enableFlag(breakFlagEnabledProperty, breakFlagEnabled, 4);
    }

    public final BooleanProperty overflowFlagEnabledProperty() {
        return overflowFlagEnabledProperty;
    }

    public boolean isOverflowFlagEnabled() {
        return overflowFlagEnabledProperty.get();
    }

    public void setOverflowFlagEnabled(boolean overflowFlagEnabled) {
        enableFlag(overflowFlagEnabledProperty, overflowFlagEnabled, 6);
    }

    public final BooleanProperty negativeFlagEnabledProperty() {
        return negativeFlagEnabledProperty;
    }

    public boolean isNegativeFlagEnabled() {
        return negativeFlagEnabledProperty.get();
    }

    public void setNegativeFlagEnabled(boolean negativeFlagEnabled) {
        enableFlag(negativeFlagEnabledProperty, negativeFlagEnabled, 7);
    }

    /**
     * Returns a string representation of the Processor Status<br>A binary string with zeroes and ones representing every bit of the status.
     *
     * @return a string representation of the Processor Status
     */
    @Override
    public String toString() {
        final String[] result = {""};
        flags().forEach(flag -> {
            if (flag) {
                result[0] += "1";
            } else {
                result[0] += "0";
            }
        });
        return result[0];
    }

    /**
     * Returns a list representation of all the flags
     *
     * @return a list representation of all the flags
     */
    public List<Boolean> flags() {
        return Arrays.asList(
                isCarryFlagEnabled(),
                isZeroFlagEnabled(),
                isInterruptFlagEnabled(),
                isDecimalFlagEnabled(),
                isBreakFlagEnabled(),
                isOverflowFlagEnabled(),
                isNegativeFlagEnabled());
    }

    private void enableFlag(BooleanProperty property, boolean value, int index) {
        property.set(value);
        if (value) {
            enableBit((byte) index);
        } else {
            clearBit((byte) index);
        }
        flags().set(index, value);
    }

    private void enableBit(byte index) {
        this.setValue((byte) (this.getValue().byteValue() | (1 << index)));
    }

    private void clearBit(byte index) {
        this.setValue((byte) (this.getValue().byteValue() & ~(1 << index)));
    }

}
