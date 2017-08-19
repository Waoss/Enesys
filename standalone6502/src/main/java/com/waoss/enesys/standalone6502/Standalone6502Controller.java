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

package com.waoss.enesys.standalone6502;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Standalone6502Controller extends Window implements Initializable {

    private final Emulator emulator = new Emulator();
    private final FileChooser fileChooser = new FileChooser();
    @FXML
    public Canvas screen;

    private static Integer[] readFully(InputStream inputStream) throws IOException {
        Vector<Integer> integerVector = new Vector<>();
        int b = 0;
        while (b != - 1) {
            b = inputStream.read();
            integerVector.add(b);
        }
        Integer[] result = new Integer[integerVector.size()];
        return integerVector.toArray(result);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    public void loadBinariesButtonOnAction(ActionEvent actionEvent) {
        final File file = fileChooser.showOpenDialog(this);
        try {
            emulator.setBinaries(ArrayUtils.toPrimitive(readFully(new FileInputStream(file))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        emulator.executeBinaries();
    }
}
