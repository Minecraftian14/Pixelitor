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

package pixelitor.tools.util;

import pixelitor.Canvas;
import pixelitor.Composition;
import pixelitor.gui.CompositionView;
import pixelitor.gui.View;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * The "Pixelitor Point" represents an immutable point on an image both in
 * component (mouse) coordinates and image coordinates.
 * <p>
 * Component coordinates are relative to the {@link CompositionView},
 * image coordinates are relative to the {@link Canvas} (not necessarily
 * to the BufferedImage, as the image can be bigger than the canvas) and
 * take the position of the {@link Canvas} within the
 * {@link CompositionView} and the image zooming into account.
 */
public abstract class PPoint {
    View view;

    // All the coordinates are initialized in subclasses
    // coordinates in image space
    protected double imX;
    protected double imY;
    // coordinates in component (MouseEvent) space
    protected double coX;
    protected double coY;

    protected PPoint(View view) {
        assert view != null;
        this.view = view;
    }

    /**
     * Returns the x coordinate in component space
     */
    public double getCoX() {
        return coX;
    }

    /**
     * Returns the y coordinate in component space
     */
    public double getCoY() {
        return coY;
    }

    /**
     * Returns the x coordinate in image space
     */
    public double getImX() {
        return imX;
    }

    /**
     * Returns the y coordinate in image space
     */
    public double getImY() {
        return imY;
    }

    public CompositionView getCV() {
        return (CompositionView) view;
    }

    public View getView() {
        return view;
    }

    public PPoint mirrorVertically(int compWidth) {
        return new EagerImage(view, compWidth - getImX(), getImY());
    }

    public PPoint mirrorHorizontally(int compHeight) {
        return new EagerImage(view, getImX(), compHeight - getImY());
    }

    public PPoint mirrorBoth(int compWidth, int compHeight) {
        return new EagerImage(view, compWidth - getImX(), compHeight - getImY());
    }

    public void drawLineTo(PPoint end, Graphics2D g) {
        Line2D.Double line = new Line2D.Double(getImX(), getImY(), end.getImX(), end.getImY());
        g.draw(line);
    }

    /**
     * Returns the squared distance in image space
     */
    public double imDistSq(PPoint other) {
        double dx = getImX() - other.getImX();
        double dy = getImY() - other.getImY();
        return dx * dx + dy * dy;
    }

    /**
     * Returns the distance in image space
     */
    public double imDist(PPoint other) {
        return Math.sqrt(imDistSq(other));
    }

    /**
     * Returns the squared distance in component space
     */
    public double coDistSq(PPoint other) {
        double dx = getCoX() - other.getCoX();
        double dy = getCoY() - other.getCoY();
        return dx * dx + dy * dy;
    }

    /**
     * Returns the distance in component space
     */
    public double coDist(PPoint other) {
        return Math.sqrt(coDistSq(other));
    }

    public static PPoint lazyFromCo(double x, double y, View view) {
        return new Lazy(view, x, y);
    }

    public static PPoint eagerFromCo(double x, double y, View view) {
        return new Eager(view, x, y);
    }

    public static PPoint eagerFromIm(double imX, double imY, View view) {
        return new EagerImage(view, imX, imY);
    }

    public static PPoint lazyFromIm(double imX, double imY, View view) {
        return new LazyImage(view, imX, imY);
    }

    public Composition getComp() {
        return ((CompositionView) view).getComp();
    }

    /**
     * A lazy {@link PPoint}, which converts component
     * space coordinates to image space coordinates only
     * on demand
     */
    public static class Lazy extends PPoint {
        private boolean xConverted = false;
        private boolean yConverted = false;

        public Lazy(View view, double x, double y) {
            super(view);
            coX = x;
            coY = y;
            // image space coordinates are not yet initialized
        }

        @Override
        public double getImX() {
            if (!xConverted) {
                imX = view.componentXToImageSpace(coX);
                xConverted = true;
            }
            return imX;
        }

        @Override
        public double getImY() {
            if (!yConverted) {
                imY = view.componentYToImageSpace(coY);
                yConverted = true;
            }
            return imY;
        }
    }

    /**
     * An eager {@link PPoint}, which converts component
     * space coordinates to image space coordinates immediately
     */
    public static class Eager extends PPoint {
        public Eager(View view, double x, double y) {
            super(view);
            coX = x;
            coY = y;
            imX = view.componentXToImageSpace(coX);
            imY = view.componentYToImageSpace(coY);
        }
    }

    /**
     * A {@link PPoint} eagerly initialized with image-space coordinates
     */
    private static class EagerImage extends PPoint {
        public EagerImage(View view, double imX, double imY) {
            super(view);
            this.imX = imX;
            this.imY = imY;
            this.coX = (int) view.imageXToComponentSpace(imX);
            this.coY = (int) view.imageYToComponentSpace(imY);
        }
    }

    /**
     * A {@link PPoint} lazily initialized with image-space coordinates
     */
    private static class LazyImage extends PPoint {
        private boolean xConverted = false;
        private boolean yConverted = false;

        public LazyImage(View view, double imX, double imY) {
            super(view);
            this.imX = imX;
            this.imY = imY;
            // component space coordinates are not yet initialized
        }

        @Override
        public double getCoX() {
            if (!xConverted) {
                this.coX = view.imageXToComponentSpace(imX);
                xConverted = true;
            }
            return coX;
        }

        @Override
        public double getCoY() {
            if (!yConverted) {
                this.coY = view.imageYToComponentSpace(imY);
                yConverted = true;
            }
            return coY;
        }
    }
}
