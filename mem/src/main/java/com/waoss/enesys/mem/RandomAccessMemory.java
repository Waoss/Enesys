package com.waoss.enesys.mem;

import java.nio.ByteBuffer;

public final class RandomAccessMemory extends Memory {

    private ByteBuffer buffer = ByteBuffer.allocate(2000);

    public RandomAccessMemory() {
        super();
    }

    @Override
    public byte read(short address) {
        return buffer.get(address);
    }

    @Override
    public void write(short address, byte value) {
        buffer.put(address, value);
    }
}
