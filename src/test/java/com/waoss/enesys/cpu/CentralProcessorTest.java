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
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.logging.Logger;

public class CentralProcessorTest {

    @NotNull Console targetConsole = new Console();
    @NotNull CentralProcessor target = new CentralProcessor(targetConsole);
    @NotNull Logger logger = Logger.getLogger("com.waoss.enesys.cpu");

    @Test
    public void registerLoading() throws Exception {
        logger.info("Loading into the X register");
        testBiArgumented(0x0600, 0xa2, 0x0601, 0x44);
    }

    @Test
    public void branching() throws Exception {
        logger.info("Testing branching");
        testBiArgumented(0x0600, 0x90, 0x0601, 0x01);
    }

    @Test
    public void carryFlagSetting() throws Exception {
        logger.info("Setting the carry flag");
        testUniArgumented(0x0600, 0x38);
        assert target.getProcessorStatus().isCarryFlagEnabled();
        logger.info("Branching successful");
    }

    @Test
    public void decimalFlagSetting() throws Exception {
        logger.info("Setting the decimal flag");
        testUniArgumented(0x0600, 0xf8);
        assert target.getProcessorStatus().isDecimalFlagEnabled();
        logger.info("Branching successful");
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