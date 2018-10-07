/*
 * Copyright 2018 Laszlo Balazs-Csiki and Contributors
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

package pixelitor.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;
import pixelitor.Canvas;

/**
 * Custom AssertJ assertions for {@link Canvas} objects.
 * Based partially on the code generated by CustomAssertionGenerator.
 */
public class CanvasAssert extends AbstractAssert<CanvasAssert, Canvas> {
    /**
     * Creates a new <code>{@link CanvasAssert}</code> to make assertions on actual Canvas.
     *
     * @param actual the Canvas we want to make assertions on.
     */
    public CanvasAssert(Canvas actual) {
        super(actual, CanvasAssert.class);
    }

    public CanvasAssert hasImBounds(java.awt.Rectangle bounds) {
        isNotNull();

        String msg = "\nExpecting bounds of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        java.awt.Rectangle actualBounds = actual.getImBounds();
        if (!Objects.areEqual(actualBounds, bounds)) {
            failWithMessage(msg, actual, bounds, actualBounds);
        }

        return this;
    }

    public CanvasAssert hasImHeight(int height) {
        isNotNull();

        String msg = "\nExpecting height of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        int actualHeight = actual.getImHeight();
        if (actualHeight != height) {
            failWithMessage(msg, actual, height, actualHeight);
        }

        return this;
    }

    public CanvasAssert hasImWidth(int width) {
        isNotNull();

        String msg = "\nExpecting width of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        int actualWidth = actual.getImWidth();
        if (actualWidth != width) {
            failWithMessage(msg, actual, width, actualWidth);
        }

        return this;
    }

    /**
     * Verifies that the actual Canvas's component space width is equal to the given one.
     */
    public CanvasAssert hasCoWidth(int coWidth) {
        isNotNull();

        String msg = "\nExpecting component space width of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        int actualCoWidth = actual.getCoWidth();
        if (actualCoWidth != coWidth) {
            failWithMessage(msg, actual, coWidth, actualCoWidth);
        }

        return this;
    }

    /**
     * Verifies that the actual Canvas's component space height is equal to the given one.
     */
    public CanvasAssert hasCoHeight(int coHeight) {
        isNotNull();

        String msg = "\nExpecting coHeight of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        int actualCoHeight = actual.getCoHeight();
        if (actualCoHeight != coHeight) {
            failWithMessage(msg, actual, coHeight, actualCoHeight);
        }

        return this;
    }

    /**
     * Verifies that the actual Canvas's component space size is equal to the given one.
     */
    public CanvasAssert hasCoSize(java.awt.Dimension coSize) {
        isNotNull();

        String msg = "\nExpecting coSize of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        java.awt.Dimension actualCoSize = actual.getCoSize();
        if (!Objects.areEqual(actualCoSize, coSize)) {
            failWithMessage(msg, actual, coSize, actualCoSize);
        }

        return this;
    }
}
