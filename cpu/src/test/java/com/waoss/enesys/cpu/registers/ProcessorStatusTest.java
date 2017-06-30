package com.waoss.enesys.cpu.registers;

import org.junit.Test;

public class ProcessorStatusTest {

    ProcessorStatus processorStatus = new ProcessorStatus();

    @Test
    public void isCarryFlagEnabled() throws Exception {
        assert !processorStatus.isCarryFlagEnabled();
    }

    @Test
    public void setCarryFlagEnabled() throws Exception {
        processorStatus.setCarryFlagEnabled(true);
    }

}