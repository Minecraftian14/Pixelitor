/*
 * Copyright 2019 Laszlo Balazs-Csiki and Contributors
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

package pixelitor.layers;

import pixelitor.gui.CompositionView;
import pixelitor.gui.OpenComps;
import pixelitor.gui.PixelitorWindow;
import pixelitor.utils.CompActivationListener;
import pixelitor.utils.Icons;
import pixelitor.utils.test.RandomGUITest;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * An Action that adds a new text layer to the active composition.
 */
public class AddTextLayerAction extends AbstractAction
    implements CompActivationListener {

    public static final AddTextLayerAction INSTANCE = new AddTextLayerAction();

    private AddTextLayerAction() {
        super("Add Text Layer", Icons.load("add_text_layer.png"));
        putValue(SHORT_DESCRIPTION, "Adds a new text layer.");
        setEnabled(false);
        OpenComps.addActivationListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!RandomGUITest.isRunning()) {
            TextLayer.createNew(PixelitorWindow.getInstance());
        }
    }

    @Override
    public void allCompsClosed() {
        setEnabled(false);
    }

    @Override
    public void compActivated(CompositionView oldIC, CompositionView newIC) {
        setEnabled(true);
    }
}