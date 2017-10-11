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

import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;
import org.junit.Test;

/**
 * A class to test the {@link Grid} class.
 */
public class GridTest {
    /**
     * The constructor should throw an exception if given negative values.
     */
    @Test
    public void negativeConstructorTest() {
        assertThatThrownBy(() -> {
            new Grid(-1,-1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * The constructor should throw an exception if given 0 values.
     */
    @Test
    public void emptyConstructorTest() {
        assertThatThrownBy(() -> {
            new Grid(0,0);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Tests the getWidth and getHeight methods.
     */
    @Test
    public void gettersTest() {
        Grid grid = new Grid(3,2);
        assertThat(grid.getWidth()).isEqualTo(3);
        assertThat(grid.getHeight()).isEqualTo(2);
    }

    /**
     * Tests the in points of the onGrid method.
     */
    @Test
    public void onGridInPointsTest() {
        Grid grid = new Grid(3,2);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                assertThat(grid.onGrid(x,y)).isTrue();
            }
        }
    }

    /**
     * Tests the out points of the onGrid method.
     */
    @Test
    public void onGridOutPointsTest() {
        Grid grid = new Grid(3,2);
        int x = -1;
        int y = -1;

        while (y < 2) {
            while (x < 4) {
                assertThat(grid.onGrid(x, y)).isFalse();
                x++;
            }
            x = -1;
            y += 3;
        }

        x = -1;
        y = -1;

        while (x < 4) {
            while (y < 3) {
                assertThat(grid.onGrid(x, y)).isFalse();
                y++;
            }
            y = -1;
            x += 4;
        }
    }

    /**
     * Tests the place and get method with a few Tileables.
     */
    @Test
    public void placeGetTest() {
        Grid grid = new Grid(3,1);
        Tileable[] tileTypes = { new Receptor(), new HorizontalTrack(), new VerticalTrack() };
        for (int x = 0; x < 3; x++) {
            grid.place(x,0, tileTypes[x]);
            assertThat(grid.get(x,0).getTileable()).isEqualTo(tileTypes[x]);
        }
    }
}
