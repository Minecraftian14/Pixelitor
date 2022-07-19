/*
 * Copyright 2022 Laszlo Balazs-Csiki and Contributors
 *
 * This file is part of Pixelitor. Pixelitor is free software: you
 * can redistribute it and/or modify it under the terms of the GNU
 * General Public License, version 3 as published by the Free
 * Software Foundation.
 *
 * Pixelitor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pixelitor. If not, see <http://www.gnu.org/licenses/>.
 */

package pixelitor.history;

import pixelitor.Composition;
import pixelitor.layers.Layer;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class IsolateEdit extends PixelitorEdit {
    private final Layer layer;
    private final boolean[] backupVisibility;

    public IsolateEdit(Composition comp, Layer layer, boolean[] backupVisibility) {
        super("Isolate", comp);
        this.layer = layer;
        this.backupVisibility = backupVisibility;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();

        int numLayers = comp.getNumLayers();
        for (int i = 0; i < numLayers; i++) {
            comp.getLayer(i).setVisible(backupVisibility[i]);
        }
        comp.update();
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();

        comp.isolate(layer, false);
    }

    public Layer getLayer() {
        return layer;
    }
}
