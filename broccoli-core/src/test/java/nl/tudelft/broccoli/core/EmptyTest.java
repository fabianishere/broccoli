/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Fabian Mastenbroek, Christian Slothouber,
 * Earth Grob, Matthijs Rijm, Bas Musters
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nl.tudelft.broccoli.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for an empty {@link Tileable} object.
 */
public class EmptyTest {
    /**
     * The object under test.
     */
    private Empty empty;

    /**
     * The marble to use.
     */
    private Marble marble;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        empty = new Empty();
        marble = new Marble(MarbleType.GREEN);
    }

    /**
     * Tests if the empty tile accepts a ball from the left.
     */
    @Test
    public void acceptsLeftFalse() {
        assertThat(empty.accepts(Direction.LEFT, marble)).isFalse();
    }

    /**
     * Tests if the empty tile accepts a ball from the right.
     */
    @Test
    public void acceptsRightFalse() {
        assertThat(empty.accepts(Direction.RIGHT, marble)).isFalse();
    }

    /**
     * Tests if the empty tile accepts a ball from above.
     */
    @Test
    public void acceptsTopFalse() {
        assertThat(empty.accepts(Direction.TOP, marble)).isFalse();
    }

    /**
     * Tests if the empty tile accepts a ball from the bottom.
     */
    @Test
    public void acceptsBottomFalse() {
        assertThat(empty.accepts(Direction.BOTTOM, marble)).isFalse();
    }

    /**
     * Test if the empty throws an exception whenever it is called.
     */
    @Test
    public void acceptLeftIllegalArgumentException() {
        assertThatThrownBy(() -> empty.accept(Direction.LEFT, null)).isInstanceOf(Exception.class);
    }
}
