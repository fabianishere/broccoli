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

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Direction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Test class for the {@Link Track} class.
 */
public class TrackTest {

    /**
     * Test if an exception is thrown when checking progress from a null ball.
     */
    @Test
    public void progressOfException() {
        HorizontalTrack horTrack = new HorizontalTrack();
        assertThatThrownBy(() -> horTrack.progressOf(null)).isInstanceOf(Exception.class);
    }

    /**
     * Check the progress of a ball.
     */
    @Test
    public void progressOfTest() {
        HorizontalTrack horTrack = new HorizontalTrack();
        Ball ball = Ball.of(Ball.Type.BLUE);
        horTrack.accept(Direction.LEFT, ball);
        assertThat(horTrack.progressOf(ball)).isEqualTo(0.f);
    }

    /**
     * Test if an exception gets thrown when a track not on the grid gets updated.
     */
    @Test
    public void updateException() {
        HorizontalTrack horTrack = new HorizontalTrack();
        assertThatThrownBy(() -> horTrack.update(0)).isInstanceOf(Exception.class);
    }
}
