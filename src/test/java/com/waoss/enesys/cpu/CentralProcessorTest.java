/*
 * Enesys : An NES Emulator
 * Copyright (C) 2017  Rahul Chhabra and Waoss
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.waoss.enesys.cpu;

import com.waoss.enesys.Console;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CentralProcessorTest {

    Console targetConsole;
    CentralProcessor target;

    @Before
    public void initTargets() {
        targetConsole = new Console();
        target = new CentralProcessor(targetConsole);
    }

    @Test
    public void registerLoading() throws Exception {
        testBiArgumented(0x0600, 0xa2, 0x0601, 0x44);
    }

    @Test
    public void branching() throws Exception {
        testBiArgumented(0x0600, 0x90, 0x0601, 0x01);
    }

    @Test
    public void carryFlagSetting() throws Exception {
        testUniArgumented(0x0600, 0x38);
        assertTrue(target.getProcessorStatus().isCarryFlagEnabled());
    }

    @Test
    public void carryFlagClearing() throws Exception {
        testUniArgumented(0x0600, 0x18);
        assertFalse(target.getProcessorStatus().isCarryFlagEnabled());
    }

    @Test
    public void interruptClearing() throws Exception {
        testUniArgumented(0x0600, 0x58);
        assertFalse(target.getProcessorStatus().isInterruptFlagEnabled());
    }

    @Test
    public void interruptSetting() throws Exception {
        testUniArgumented(0x0600, 0x78);
        assertTrue(target.getProcessorStatus().isInterruptFlagEnabled());
    }

    @Test
    public void overflowFlagClearing() throws Exception {
        testUniArgumented(0x0600, 0xb8);
        assertFalse(target.getProcessorStatus().isOverflowFlagEnabled());
    }

    @Test
    public void decimalFlagSetting() throws Exception {
        testUniArgumented(0x0600, 0xf8);
        assert target.getProcessorStatus().isDecimalFlagEnabled();
    }

    @Test
    public void aslTest() throws Exception {
        testBiArgumented(0x0600, 0x0e, 0x0601, 0x05);
    }

    @Test
    public void compareTest() throws Exception {
        testBiArgumented(0x0600, 0xc9, 0x0601, 0x00);
        assertTrue(target.getProcessorStatus().isZeroFlagEnabled());
        assertFalse(target.getProcessorStatus().isCarryFlagEnabled());
    }

    @Test
    public void compareXTest() throws Exception {
        testBiArgumented(0x0600, 0xe0, 0x0601, 0x00);
        assertTrue(target.getProcessorStatus().isZeroFlagEnabled());
        assertFalse(target.getProcessorStatus().isCarryFlagEnabled());
    }

    private void testUniArgumented(int address, int instruction) throws Exception {
        testBiArgumented(address, instruction, 0, 0);
    }

    //Testing for two arguments
    private void testBiArgumented(int start, int startValue, int end, int endValue) throws Exception {
        targetConsole.getCompleteMemory().write(start, startValue);
        targetConsole.getCompleteMemory().write(end, endValue);
        startTarget();
    }

    private void startTarget() throws Exception {
        target.start();
        Thread.sleep(3000);
        target.interruptThread();
    }
}