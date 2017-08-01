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

/**
 * <p>This package is practically the register file of the CPU.The 6502 had only three general purpose registers :
 * <ul>
 * <li>The Accumulator {@link com.waoss.enesys.cpu.registers.AccumalativeRegister}</li>
 * <li>The X register {@link com.waoss.enesys.cpu.registers.XRegister}</li>
 * <li>The Y register {@link com.waoss.enesys.cpu.registers.YRegister}</li>
 * </ul><br>Except this, there was the {@link com.waoss.enesys.cpu.registers.ProgramCounter} and the {@link
 * com.waoss.enesys.cpu.registers.StackPointer}.All these are different implementations of {@link
 * com.waoss.enesys.cpu.registers.Register}.</p>
 */

package com.waoss.enesys.cpu.registers;