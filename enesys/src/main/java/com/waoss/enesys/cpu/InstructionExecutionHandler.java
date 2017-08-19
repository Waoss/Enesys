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

import com.waoss.enesys.cpu.instructions.Instruction;

/**
 * Implementations of this interface are executed by the CPU every time an instruction is processed.<br>
 * It is a functional interface and it's function is {@link #handle(CentralProcessor, Instruction)}
 */
@FunctionalInterface
public interface InstructionExecutionHandler {

    /**
     * This is the actual method called by the CPU and also the function which makes it a Functional Interface
     *
     * @param centralProcessor
     *         The processor that executed the handler
     * @param executed
     *         The executed that was executed
     */
    void handle(CentralProcessor centralProcessor, Instruction executed);
}
