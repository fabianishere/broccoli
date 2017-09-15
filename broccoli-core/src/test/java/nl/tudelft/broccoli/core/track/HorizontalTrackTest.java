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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Test class that tests the {@Link HorizontalTrack} class.
 */
public class HorizontalTrackTest {

    private HorizontalTrack horTrack = new HorizontalTrack();

    /**
     * Test if an unconnected rail returns false.
     */
    @Test
    public void isNotConnected() {
        assertThat(horTrack.isConnected()).isFalse();
    }

    /**
     * Test if a connected rail returns true.
     */
    @Test
    public void isConnected() {
        HorizontalTrack hz1 = new HorizontalTrack();
        HorizontalTrack hz2 = new HorizontalTrack();

        Grid grid = new Grid(3, 1);
        grid.place(0, 0, hz1);
        grid.place(1, 0, horTrack);
        grid.place(2, 0, hz2);

        assertThat(horTrack.isConnected()).isTrue();
    }

    /**
     * Test if a horizontal track accepts a ball from the left.
     */
    @Test
    public void acceptsLeft() {
        assertThat(horTrack.accepts(Direction.LEFT)).isTrue();
    }

    /**
     * Test if a horizontal track accepts a ball from the right.
     */
    @Test
    public void acceptsRight() {
        assertThat(horTrack.accepts(Direction.RIGHT)).isTrue();
    }

    /**
     * Test if a horizontal track does not accept a ball from the top.
     */
    @Test
    public void notAcceptsTop() {
        assertThat(horTrack.accepts(Direction.TOP)).isFalse();
    }

    /**
     * Test if a horizontal track does not accept a ball from the bottom.
     */
    @Test
    public void notAcceptsBottom() {
        assertThat(horTrack.accepts(Direction.BOTTOM)).isFalse();
    }
}
