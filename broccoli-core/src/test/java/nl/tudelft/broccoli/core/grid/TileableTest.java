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

import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;
import org.junit.Before;
import org.junit.Test;

/**
 * A class to test the abstract {@link Tileable} class.
 */
public class TileableTest {
    Grid grid;
    HorizontalTrack htrack;
    VerticalTrack vtrack;

    /**
     * Sets up a grid an onGrid tile and an off the grid tile ☺.
     */
    @Before
    public void setUp() {
        grid = new Grid(1, 1);
        htrack = new HorizontalTrack();
        vtrack = new VerticalTrack();

        grid.place(0,0,htrack);
    }

    /**
     * Tests if the onGrid method.
     */
    @Test
    public void isOnGridTest() {
        assertThat(htrack.onGrid()).isTrue();
    }

    /**
     * Tests if the onGrid method.
     */
    @Test
    public void isNotOnGridTest() {
        assertThat(vtrack.onGrid()).isFalse();
    }

    /**
     * Tests the getTile method.
     */
    @Test
    public void getTileTest() {
        assertThat(htrack.getTile()).isEqualTo(grid.get(0,0));
    }
}
