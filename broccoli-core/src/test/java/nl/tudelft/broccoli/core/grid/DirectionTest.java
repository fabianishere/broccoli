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

package nl.tudelft.broccoli.core.grid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

/**
 * A class to test the {@link Direction} enum.
 */
public class DirectionTest {
    /**
     * Tests the inverse method with Top.
     */
    @Test
    public void inverseTestTop() {
        assertThat(Direction.TOP.inverse()).isEqualTo(Direction.BOTTOM);
    }

    /**
     * Tests the inverse method with Bottom.
     */
    @Test
    public void inverseTestBottom() {
        assertThat(Direction.BOTTOM.inverse()).isEqualTo(Direction.TOP);
    }

    /**
     * Tests the inverse method with Left.
     */
    @Test
    public void inverseTestLeft() {
        assertThat(Direction.LEFT.inverse()).isEqualTo(Direction.RIGHT);
    }

    /**
     * Tests the inverse method with Right.
     */
    @Test
    public void inverseTestRight() {
        assertThat(Direction.RIGHT.inverse()).isEqualTo(Direction.LEFT);
    }

    /**
     * Tests the from method with a negative index.
     */
    @Test
    public void fromNegative() {
        assertThat(Direction.from(-1)).isEqualTo(Direction.LEFT);
    }

    /**
     * Tests the from method with an index that overflows.
     */
    @Test
    public void fromLarge() {
        assertThat(Direction.from(4)).isEqualTo(Direction.TOP);
    }

    /**
     * Test the conversion from the string "LEFT" to {@link Direction#LEFT}.
     */
    @Test
    public void valueOfLeft() {
        assertThat(Direction.valueOf("LEFT")).isEqualTo(Direction.LEFT);
    }

    /**
     * Test the conversion from the string "RIGHT" to {@link Direction#RIGHT}.
     */
    @Test
    public void valueOfRight() {
        assertThat(Direction.valueOf("RIGHT")).isEqualTo(Direction.RIGHT);
    }

    /**
     * Test the conversion from the string "TOP" to {@link Direction#TOP}.
     */
    @Test
    public void valueOfTop() {
        assertThat(Direction.valueOf("TOP")).isEqualTo(Direction.TOP);
    }

    /**
     * Test the conversion from the string "RIGHT" to {@link Direction#RIGHT}.
     */
    @Test
    public void valueOfBottom() {
        assertThat(Direction.valueOf("BOTTOM")).isEqualTo(Direction.BOTTOM);
    }

    /**
     * Test the conversion of an invalid string.
     */
    @Test
    public void valueOfInvalid() {
        assertThatThrownBy(() -> Direction.valueOf("test")).isInstanceOf(IllegalArgumentException
            .class);
    }

    /**
     * Test the conversion of an <code>null</code>string.
     */
    @Test
    public void valueOfNull() {
        assertThatThrownBy(() -> Direction.valueOf(null))
            .isInstanceOf(NullPointerException.class);
    }
}
