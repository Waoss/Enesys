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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.waoss.enesys.cpu.registers.ProcessorStatus;

import java.io.IOException;

public class ConsoleTypeAdapter extends TypeAdapter<Console> {

    @Override
    public void write(JsonWriter out, Console value) throws IOException {
        out.beginObject();
        out.name("x").value(value.getXRegister().getValue());
        out.name("y").value(value.getYRegister().getValue());
        out.name("a").value(value.getARegister().getValue());
        writeFlags(out, value.getProcessorStatus());
        out.endObject();
    }

    private void writeFlags(JsonWriter out, ProcessorStatus status) throws IOException {
        status.flags().forEach(e -> {
            try {
                out.name(e.getName()).value(e.get());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Override
    public Console read(JsonReader in) throws IOException {
        in.beginObject();
        String name = in.nextName();
        return null;
    }
}
