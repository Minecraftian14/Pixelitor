/*
 * Copyright 2024 Laszlo Balazs-Csiki and Contributors
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

import org.assertj.core.api.AbstractObjectAssert;
import pixelitor.filters.Filter;
import pixelitor.filters.RandomFilterSource;

import static java.lang.String.format;

/**
 * Custom AssertJ assertions for {@link RandomFilterSource} objects.
 * Based partially on the code generated by CustomAssertionGenerator.
 */
public class RandomFilterSourceAssert extends AbstractObjectAssert<RandomFilterSourceAssert, RandomFilterSource> {

    /**
     * Creates a new <code>{@link RandomFilterSourceAssert}</code> to make assertions on actual RandomFilterSource.
     *
     * @param actual the RandomFilterSource we want to make assertions on.
     */
    public RandomFilterSourceAssert(RandomFilterSource actual) {
        super(actual, RandomFilterSourceAssert.class);
    }

    public RandomFilterSourceAssert lastFilterIsNull() {
        isNotNull();

        if (actual.getLastFilter() != null) {
            failWithMessage("The last filter is not null");
        }

        return this;
    }

    public RandomFilterSourceAssert lastFilterIsNotNull() {
        isNotNull();

        if (actual.getLastFilter() == null) {
            failWithMessage("The last filter is null");
        }

        return this;
    }

    public RandomFilterSourceAssert lastFilterIs(Filter expected) {
        isNotNull();

        Filter lastFilter = actual.getLastFilter();
        if (lastFilter != expected) {
            failWithMessage(format("""
                Expecting that the last filter is equal to the expected, but it is not.
                Expected: %s, actual last filer: %s""", expected.getName(), lastFilter.getName()));
        }

        return this;
    }

    public RandomFilterSourceAssert previousFilterIs(Filter expected) {
        isNotNull();

        Filter previous = actual.getPrevious();
        if (previous != expected) {
            failWithMessage(format("""
                Expecting that the previous filter is equal to the expected, but it is not.
                Expected: %s, actual previous filer: %s""", expected.getName(), previous.getName()));
        }

        return this;
    }

    public RandomFilterSourceAssert nextFilterIs(Filter expected) {
        isNotNull();

        Filter next = actual.getNext();
        if (next != expected) {
            failWithMessage(format("""
                Expecting that the next filter is equal to the expected, but it is not.
                Expected: %s, actual next filer: %s""", expected.getName(), next.getName()));
        }

        return this;
    }

    public RandomFilterSourceAssert hasNext() {
        isNotNull();

        if (!actual.hasNext()) {
            failWithMessage("Doesn't have next");
        }

        return this;
    }

    public RandomFilterSourceAssert doesNotHaveNext() {
        isNotNull();

        if (actual.hasNext()) {
            failWithMessage("Has next");
        }

        return this;
    }

    public RandomFilterSourceAssert hasPrevious() {
        isNotNull();

        if (!actual.hasPrevious()) {
            failWithMessage("Doesn't have previous");
        }

        return this;
    }

    public RandomFilterSourceAssert doesNotHavePrevious() {
        isNotNull();

        if (actual.hasPrevious()) {
            failWithMessage("Has previous");
        }

        return this;
    }
}
