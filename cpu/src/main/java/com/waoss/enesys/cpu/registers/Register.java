package com.waoss.enesys.cpu.registers;

public abstract class Register<T extends Number> {

    public abstract Number getValue();

    public abstract void setValue(Number value);
}
