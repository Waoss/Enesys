package com.waoss.enesys.cpu.registers;

import org.junit.Test;

public class RegisterTest {

    Register<Byte> register = new AccumalativeRegister();

    @Test
    public void setValue() throws Exception {
        register.setValue((byte) 100);
    }

    @Test
    public void getValue() throws Exception {
        System.out.println(register.getValue());
    }

}