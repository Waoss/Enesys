package com.waoss.enesys.cpu.registers;

public class StackPointer extends UnsignedByteRegister {

    public StackPointer() {
        super((byte) 0xfd);
    }

    public void pushUpdate() {
        this.setValue(this.getValue().byteValue() - 1);
    }

    public void pullUpdate() {
        this.setValue(this.getValue().byteValue() + 1);
    }
}
