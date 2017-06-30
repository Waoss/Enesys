package com.waoss.enesys.mem;

import org.junit.Test;

public class RandomAccessMemoryTest {

    RandomAccessMemory ram = new RandomAccessMemory();

    @Test
    public void write() throws Exception {
        ram.write((short) 0x0200, (byte) 0xf);
    }

    @Test
    public void read() throws Exception {
        System.out.println(ram.read((short) 0x0200));
    }

}