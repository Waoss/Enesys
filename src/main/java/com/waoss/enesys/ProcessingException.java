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

package com.waoss.enesys;

/**
 * An exception a processor(CPU,APU,PPU) may throw on any shit
 */
public class ProcessingException extends RuntimeException {

    /**
     * Because all three processors deal with opcodes an exception for an invalid opcode appears legit to me.
     *
     * @param invalidOpCode
     *         The invalid opcode.
     */
    public ProcessingException(int invalidOpCode) {
        super("Invalid Operation Code : " + invalidOpCode);
    }

    /**
     * Throw an exception with a message using the constructor of {@link RuntimeException}
     *
     * @param message
     *         the message
     */
    public ProcessingException(String message) {
        super(message);
    }
}
