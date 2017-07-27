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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * An instance of this class represents the "status" of a processor<br>
 * The processor status was a special register that stored meta-data of the 6502.<br>
 * They have been implemented as JavaFX properties
 */
public final class ProcessorStatus extends UnsignedIntRegister {

    /**
     * Carry Flag : set when the last operation resulted in some sort of carry
     */
    private final BooleanProperty carryFlagEnabled = new SimpleBooleanProperty(this, "carryFlagEnabled", false);
    /**
     * Zero Flag :set when the last operation yielded zero
     */
    private final BooleanProperty zeroFlagEnabled = new SimpleBooleanProperty(this, "zeroFlagEnabled", false);
    /**
     * Interrupt Flag : not as sure when it's set(something to work on)
     */
    private final BooleanProperty interruptFlagEnabled = new SimpleBooleanProperty(this, "interruptFlagEnabled", true);
    /**
     * Decimal Flag : no use to us(The NES disabled Binary Coded Decimals of the 6502)
     */
    private final BooleanProperty decimalFlagEnabled = new SimpleBooleanProperty(this, "decimalFlagEnabled", false);
    /**
     * Break Flag : for break instructions(BRK)
     */
    private final BooleanProperty breakFlagEnabled = new SimpleBooleanProperty(this, "breakFlagEnabled", true);
    /**
     * Unused Flag : never used
     */
    private final transient BooleanProperty unusedFlagEnabled = new SimpleBooleanProperty(this, "unusedFlagEnabled", true);
    /**
     * Overflow Flag : set when the last operation resulted in some sort of overflow
     */
    private final BooleanProperty overflowFlagEnabled = new SimpleBooleanProperty(this, "overflowFlagEnabled", false);
    /**
     * Negative Flag : set when an operation yields a negative value
     */
    private final BooleanProperty negativeFlagEnabled = new SimpleBooleanProperty(this, "negativeFlagEnabled", false);

    /*
     * Sets the values of all the properties.
     * The strange left shift just tells us where some bit is.
     */ {
        this.carryFlagEnabled.set(((this.getValue() & (1)) > 0));
        this.zeroFlagEnabled.set(((this.getValue() & (1 << 1)) > 0));
        this.interruptFlagEnabled.set(!((this.getValue() & (1 << 2)) > 0));
        this.decimalFlagEnabled.set(((this.getValue() & (1 << 3)) > 0));
        this.breakFlagEnabled.set(((this.getValue() & (1 << 4)) > 0));
        this.unusedFlagEnabled.set(((this.getValue() & (1 << 5)) > 0));
        this.overflowFlagEnabled.set(((this.getValue() & (1 << 6)) > 0));
        this.negativeFlagEnabled.set(((this.getValue() & (1 << 7)) > 0));
    }

    /**
     * Creates a new ProcessorStatus with the default values for all the flags
     */
    public ProcessorStatus() {
        super(0b00110100);
    }

    /**
     * Returns the property that contains the flag
     *
     * @return the property that contains the flag
     */
    @NotNull
    public final BooleanProperty carryFlagEnabledProperty() {
        return carryFlagEnabled;
    }

    /**
     * Returns true if the carry flag is enabled
     *
     * @return true if the carry flag is enabled
     */
    public boolean isCarryFlagEnabled() {
        return carryFlagEnabled.get();
    }

    /**
     * Enables/disables the flag
     *
     * @param carryFlagEnabled to enable or to disable
     */
    public void setCarryFlagEnabled(boolean carryFlagEnabled) {
        enableFlag(this.carryFlagEnabled, carryFlagEnabled, 0);
    }

    /**
     * Returns the property that contains the flag
     *
     * @return the property that contains the flag
     */
    @NotNull
    public final BooleanProperty zeroFlagEnabledProperty() {
        return zeroFlagEnabled;
    }

    /**
     * Returns true if the zero flag is enabled
     *
     * @return true if the zero flag is enabled
     */
    public boolean isZeroFlagEnabled() {
        return zeroFlagEnabled.get();
    }

    /**
     * Enables/disables the flag
     *
     * @param zeroFlagEnabled to enable or to disable
     */
    public void setZeroFlagEnabled(boolean zeroFlagEnabled) {
        enableFlag(this.zeroFlagEnabled, zeroFlagEnabled, 1);
    }

    /**
     * Returns the property that contains the flag
     *
     * @return the property that contains the flag
     */
    @NotNull
    public final BooleanProperty interruptFlagEnabledProperty() {
        return interruptFlagEnabled;
    }

    /**
     * Returns true if the interrupt flag is enabled
     *
     * @return true if the interrupt flag is enabled
     */
    public boolean isInterruptFlagEnabled() {
        return interruptFlagEnabled.get();
    }

    /**
     * Enables/disables the flag
     *
     * @param interruptFlagEnabled to enable or to disable
     */
    public void setInterruptFlagEnabled(boolean interruptFlagEnabled) {
        enableFlag(this.interruptFlagEnabled, !interruptFlagEnabled, 2);
    }

    /**
     * Returns the property that contains the flag
     *
     * @return the property that contains the flag
     */
    @NotNull
    public final BooleanProperty decimalFlagEnabledProperty() {
        return decimalFlagEnabled;
    }

    /**
     * Returns true if the decimal flag is enabled
     *
     * @return true if the decimal flag is enabled
     */
    public boolean isDecimalFlagEnabled() {
        return decimalFlagEnabled.get();
    }

    /**
     * Enables/disables the flag
     *
     * @param interruptFlagEnabled to enable or to disable
     */
    public void setDecimalFlagEnabled(boolean interruptFlagEnabled) {
        enableFlag(decimalFlagEnabled, interruptFlagEnabled, 3);
    }

    /**
     * Returns the property that contains the flag
     *
     * @return the property that contains the flag
     */
    @NotNull
    public final BooleanProperty breakFlagEnabledProperty() {
        return breakFlagEnabled;
    }

    /**
     * Returns true if the break flag is enabled
     *
     * @return true if the break flag is enabled
     */
    public boolean isBreakFlagEnabled() {
        return breakFlagEnabled.get();
    }

    /**
     * Enables/disables the flag
     *
     * @param breakFlagEnabled to enable or to disable
     */
    public void setBreakFlagEnabled(boolean breakFlagEnabled) {
        enableFlag(this.breakFlagEnabled, breakFlagEnabled, 4);
    }

    /**
     * Returns the property that contains the flag
     *
     * @return the property that contains the flag
     */
    @NotNull
    public final BooleanProperty overflowFlagEnabledProperty() {
        return overflowFlagEnabled;
    }

    /**
     * Returns true if the overflow flag is enabled
     *
     * @return true if the overflow flag is enabled
     */
    public boolean isOverflowFlagEnabled() {
        return overflowFlagEnabled.get();
    }

    /**
     * Enables/disables the flag
     *
     * @param overflowFlagEnabled to enable or to disable
     */
    public void setOverflowFlagEnabled(boolean overflowFlagEnabled) {
        enableFlag(this.overflowFlagEnabled, overflowFlagEnabled, 6);
    }

    /**
     * Returns the property that contains the flag
     *
     * @return the property that contains the flag
     */
    @NotNull
    public final BooleanProperty negativeFlagEnabledProperty() {
        return negativeFlagEnabled;
    }

    /**
     * Returns true if the negative flag is enabled
     *
     * @return true if the negative flag is enabled
     */
    public boolean isNegativeFlagEnabled() {
        return negativeFlagEnabled.get();
    }

    /**
     * Enables/disables the flag
     *
     * @param negativeFlagEnabled to enable or to disable
     */
    public void setNegativeFlagEnabled(boolean negativeFlagEnabled) {
        enableFlag(this.negativeFlagEnabled, negativeFlagEnabled, 7);
    }

    /**
     * Returns a string representation of the Processor Status<br>A binary string with zeroes and ones representing every bit of the status.
     *
     * @return a string representation of the Processor Status
     */
    @Override
    public String toString() {
        @NotNull final String[] result = {""};
        flags().forEach(flag -> {
            if (flag.get()) {
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
    public List<BooleanProperty> flags() {
        return Arrays.asList(
                carryFlagEnabledProperty(),
                zeroFlagEnabledProperty(),
                interruptFlagEnabledProperty(),
                decimalFlagEnabledProperty(),
                breakFlagEnabledProperty(),
                overflowFlagEnabledProperty(),
                negativeFlagEnabledProperty());
    }

    private void enableFlag(@NotNull BooleanProperty property, boolean value, int index) {
        property.set(value);
        if (value) {
            enableBit((byte) index);
        } else {
            clearBit((byte) index);
        }
    }

    private void enableBit(byte index) {
        this.setValue((this.getValue() | (1 << index)));
    }

    private void clearBit(byte index) {
        this.setValue((this.getValue() & ~(1 << index)));
    }

}
