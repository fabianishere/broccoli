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

import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * A class to test the {@link Tile} class
 */
class TileTest {
    Grid grid;
    HorizontalTrack htrack;
    VerticalTrack vtrack;
    Receptor receptor;

    /**
     * Sets up a grid to test.
     */
    @BeforeEach
    void setup(){
        grid = new Grid(2, 2);
        htrack = new HorizontalTrack();
        vtrack = new VerticalTrack();
        receptor = new Receptor();

        grid.place(0,0,htrack);
        grid.place(1,0,vtrack);
        grid.place(0,1, receptor);
    }

    /**
     * Tests the isOccupied method with something not occupied.
     */
    @Test
    void isNotOccupied() {
        assertThat(grid.get(1,1).isOccupied()).isFalse();
    }

    /**
     * Tests the isOccupied method with something occupied.
     */
    @Test
    void isOccupied() {
        assertThat(grid.get(0,0).isOccupied()).isTrue();
    }

    /**
     * Tests the get method with the TOP direction.
     */
    @Test
    void getTOP() {
        assertThat(grid.get(0,0).get(Direction.TOP).getTileable()).isEqualTo(receptor);
    }

    /**
     * Tests the get method with the BOTTOM direction.
     */
    @Test
    void getBOTTOM() {
        assertThat(grid.get(0,1).get(Direction.BOTTOM).getTileable()).isEqualTo(htrack);
    }

    /**
     * Tests the get method with the RIGHT direction.
     */
    @Test
    void getRIGHT() {
        assertThat(grid.get(0,0).get(Direction.RIGHT).getTileable()).isEqualTo(vtrack);
    }

    /**
     * Tests the get method with the LEFT direction.
     */
    @Test
    void getLEFT() {
        assertThat(grid.get(1,0).get(Direction.LEFT).getTileable()).isEqualTo(htrack);
    }

    /**
     * Tests the get method with null.
     */
    @Test
    void getDirectionNull() {
        assertThatThrownBy(() -> grid.get(1,0).get(null).getTileable()).isInstanceOf(Exception.class);
    }

    /**
     * Tests the onGrid method.
     */
    @Test
    void getGridTest() {
        assertThat(grid.get(0,0).getGrid()).isEqualTo(grid);
    }
}
