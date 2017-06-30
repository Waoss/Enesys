package com.waoss.enesys.cpu.registers;

public final class Registers {

    private Registers() {
    }

    public static void requireUnsignedByte(Number value) {
        if (!(value instanceof Byte)) {
            throw new NumberFormatException("Value must be a byte!");
        }
    }
}
