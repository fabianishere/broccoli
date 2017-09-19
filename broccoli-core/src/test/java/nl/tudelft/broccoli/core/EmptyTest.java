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

import nl.tudelft.broccoli.core.grid.Direction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

class EmptyTest {
    Empty empty = new Empty();

    /**
     * Tests if the empty tile accepts a ball from the left.
     */
    @Test
    void acceptsLeftFalse() {
        assertThat(empty.accepts(Direction.LEFT)).isFalse();
    }

    /**
     * Tests if the empty tile accepts a ball from the right.
     */
    @Test
    void acceptsRightFalse() {
        assertThat(empty.accepts(Direction.RIGHT)).isFalse();
    }

    /**
     * Tests if the empty tile accepts a ball from above.
     */
    @Test
    void acceptsTopFalse() {
        assertThat(empty.accepts(Direction.TOP)).isFalse();
    }

    /**
     * Tests if the empty tile accepts a ball from the bottom.
     */
    @Test
    void acceptsBottomFalse() {
        assertThat(empty.accepts(Direction.BOTTOM)).isFalse();
    }

    /**
     * Test if the empty throws an exception whenever it is called.
     */
    @Test
    void acceptLeftIllegalArgumentException() {
        assertThatThrownBy(() -> empty.accept(Direction.LEFT, null)).isInstanceOf(Exception.class);
    }
}
