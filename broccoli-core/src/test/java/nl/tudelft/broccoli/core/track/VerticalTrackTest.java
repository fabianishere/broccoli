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

package nl.tudelft.broccoli.core.track;

import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Test class that tests the {@link VerticalTrack} class.
 */
class VerticalTrackTest {

    private VerticalTrack verTrack = new VerticalTrack();

    /**
     * Test if an unconnected rail returns false.
     */
    @Test
    void isNotConnected() {
        Grid grid = new Grid(1, 1);
        grid.place(0, 0, verTrack);
        assertThat(verTrack.isConnected()).isFalse();
    }

    /**
     * Test if a connected rail returns true.
     */
    @Test
    void isConnected() {
        VerticalTrack vert1 = new VerticalTrack();
        VerticalTrack vert2 = new VerticalTrack();

        Grid grid = new Grid(1, 3);
        grid.place(0, 0, vert1);
        grid.place(0, 1, verTrack);
        grid.place(0, 2, vert2);

        assertThat(verTrack.isConnected()).isTrue();
    }

    /**
     * Test if a vertical track does not accept a ball from the left.
     */
    @Test
    void notAcceptsLeft() {
        assertThat(verTrack.accepts(Direction.LEFT)).isFalse();
    }

    /**
     * Test if a vertical track does not accept a ball from the right.
     */
    @Test
    void notAcceptsRight() {
        assertThat(verTrack.accepts(Direction.RIGHT)).isFalse();
    }

    /**
     * Test if a vertical track accepts a ball from the top.
     */
    @Test
    void acceptsTop() {
        assertThat(verTrack.accepts(Direction.TOP)).isTrue();
    }

    /**
     * Test if a vertical track accepts a ball from the bottom.
     */
    @Test
    void acceptsBottom() {
        assertThat(verTrack.accepts(Direction.BOTTOM)).isTrue();
    }

    /**
     * Test if giving a ball from the left gives an exception.
     */
    @Test
    void acceptLeftException() {
        assertThatThrownBy(() -> verTrack.accept(Direction.LEFT, null))
                .isInstanceOf(Exception.class);
    }

    /**
     * Test if giving a ball from the right gives an exception.
     */
    @Test
    void acceptRightException() {
        assertThatThrownBy(() -> verTrack.accept(Direction.RIGHT, null))
                .isInstanceOf(Exception.class);
    }
}