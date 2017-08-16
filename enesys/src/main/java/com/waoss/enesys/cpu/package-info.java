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
 * <p>This package contains classes and sub-packages that primarily deal with the CPU of the NES,which was a modified
 * 6502.The NES CPU had no stack overflow detection,nor did it have the floating point.The CPU had a rather small
 * register file.
 * It was quite primitive compared to modern CPUs(so no OS - no multithreading).The lack of features in the NES CPU
 * mean that the developers of the time had great skills; but more importantly,we have lesser shit to implement.Good for
 * us.</p>
 *
 * @see com.waoss.enesys.cpu.instructions
 */

package com.waoss.enesys.cpu;