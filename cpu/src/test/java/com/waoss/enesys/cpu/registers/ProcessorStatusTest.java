package com.waoss.enesys.cpu.registers;

import org.junit.Test;

public class ProcessorStatusTest {

    ProcessorStatus processorStatus = new ProcessorStatus();

    @Test
    public void test() throws Exception {
        processorStatus.setCarryFlagEnabled(true);
        processorStatus.setZeroFlagEnabled(true);
        processorStatus.setInterruptFlagEnabled(true);
        processorStatus.setDecimalFlagEnabled(true);
        assert processorStatus.isCarryFlagEnabled();
        assert processorStatus.isZeroFlagEnabled();
        assert processorStatus.isInterruptFlagEnabled();
        assert processorStatus.isDecimalFlagEnabled();
    }

}