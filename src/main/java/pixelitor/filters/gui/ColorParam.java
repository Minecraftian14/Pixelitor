/*
 * Copyright 2015 Laszlo Balazs-Csiki
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

package pixelitor.filters.gui;

import com.jhlabs.image.ImageMath;
import pixelitor.utils.ImageUtils;

import javax.swing.*;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * A filter parameter for selecting a color
 */
public class ColorParam extends AbstractFilterParam {
    private final Color defaultColor;
    private Color color;

    private ParamGUI paramGUI;

    private final OpacitySetting opacitySetting;

    public ColorParam(String name, Color defaultColor, OpacitySetting opacitySetting) {
        super(name);

        this.defaultColor = defaultColor;
        this.color = defaultColor;
        this.opacitySetting = opacitySetting;
    }

    @Override
    public boolean isSetToDefault() {
        return color.equals(defaultColor);
    }

    @Override
    public JComponent createGUI() {
        ColorSelector gui = new ColorSelector(this);
        paramGUI = gui;
        return gui;
    }

    @Override
    public void reset(boolean triggerAction) {
        setColor(defaultColor, triggerAction);
    }

    @Override
    public int getNrOfGridBagCols() {
        return 2;
    }

    @Override
    public void randomize() {
        Color c = ImageUtils.getRandomColor(opacitySetting.allowOpacityAtRandomize);
        setColor(c, false);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor, boolean trigger) {
        if (newColor == null) {
            throw new IllegalArgumentException("newColor is null");
        }
        if (!color.equals(newColor)) {
            this.color = newColor;
            if (paramGUI != null) {
                paramGUI.updateGUI();
            }

            if (trigger) {
                if (adjustmentListener != null) {  // when called from randomize, this is null
                    adjustmentListener.paramAdjusted();
                }
            }
        }
    }

    public boolean allowOpacity() {
        return opacitySetting.allowOpacity;
    }

    @Override
    public void considerImageSize(Rectangle bounds) {
    }

    @Override
    public boolean canBeAnimated() {
        return true;
    }

    @Override
    public ParamState copyState() {
        return new CState(color);
    }

    @Override
    public void setState(ParamState state) {
        this.color = ((CState)state).color;
    }

    private static class CState implements ParamState {
        private final Color color;

        public CState(Color color) {
            this.color = color;
        }

        @Override
        public ParamState interpolate(ParamState endState, double progress) {
            // TODO - interpolating in HSB space would be better?

            int initialRGB = color.getRGB();
            int finalRGB = ((CState)endState).color.getRGB();
            int interpolatedRGB = ImageMath.mixColors((float) progress, initialRGB, finalRGB);
            return new CState(new Color(interpolatedRGB));
        }
    }

    @Override
    public void setEnabledLogically(boolean b) {
        // TODO
    }

    @Override
    public void setFinalAnimationSettingMode(boolean b) {
        // ignored because this filter parameter can be animated
    }

    @Override
    public String toString() {
        return String.format("%s[name = '%s', color = '%s']",
                getClass().getSimpleName(), getName(), color.toString());
    }

    public enum OpacitySetting {
        NO_OPACITY(false, false),
        USER_ONLY_OPACITY(true, false),
        FREE_OPACITY(true, true);

        private final boolean allowOpacity;
        private final boolean allowOpacityAtRandomize;

        private OpacitySetting(boolean allowOpacity, boolean allowOpacityAtRandomize) {
            this.allowOpacity = allowOpacity;
            this.allowOpacityAtRandomize = allowOpacityAtRandomize;
        }
    }
}
