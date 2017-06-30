package com.waoss.enesys.mem;

public abstract class Memory {

    protected Memory() {
    }

    public abstract byte read(short address);

    public abstract void write(short address, byte value);
}
