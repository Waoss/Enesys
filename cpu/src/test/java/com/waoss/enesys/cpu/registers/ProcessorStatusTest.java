package com.waoss.enesys.cpu.registers;

import org.junit.Test;

public class ProcessorStatusTest {

    ProcessorStatus processorStatus = new ProcessorStatus();

    @Test
    public void test() throws Exception {
//        Probably not required
//        But checking things, just to be sure
        long start = System.currentTimeMillis();
        processorStatus.setCarryFlagEnabled(true);
        processorStatus.setZeroFlagEnabled(true);
        processorStatus.setInterruptFlagEnabled(true);
        processorStatus.setDecimalFlagEnabled(true);
        processorStatus.setBreakFlagEnabled(true);
        processorStatus.setOverflowFlagEnabled(true);
        processorStatus.setNegativeFlagEnabled(true);
        assert processorStatus.isCarryFlagEnabled();
        assert processorStatus.isZeroFlagEnabled();
        assert processorStatus.isInterruptFlagEnabled();
        assert processorStatus.isDecimalFlagEnabled();
        assert processorStatus.isBreakFlagEnabled();
        assert processorStatus.isOverflowFlagEnabled();
        assert processorStatus.isNegativeFlagEnabled();
        System.out.println(processorStatus);
        processorStatus.setCarryFlagEnabled(false);
        processorStatus.setZeroFlagEnabled(false);
        processorStatus.setInterruptFlagEnabled(false);
        processorStatus.setDecimalFlagEnabled(false);
        processorStatus.setBreakFlagEnabled(false);
        processorStatus.setOverflowFlagEnabled(false);
        processorStatus.setNegativeFlagEnabled(false);
        assert !processorStatus.isCarryFlagEnabled();
        assert !processorStatus.isZeroFlagEnabled();
        assert !processorStatus.isInterruptFlagEnabled();
        assert !processorStatus.isDecimalFlagEnabled();
        assert !processorStatus.isBreakFlagEnabled();
        assert !processorStatus.isOverflowFlagEnabled();
        assert !processorStatus.isNegativeFlagEnabled();
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(processorStatus);
    }

}