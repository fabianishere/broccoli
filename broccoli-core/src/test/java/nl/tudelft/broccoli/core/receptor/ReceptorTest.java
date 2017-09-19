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

package nl.tudelft.broccoli.core.receptor;

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Direction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Test class for testing the {@link Receptor} class.
 */
class ReceptorTest {

    /**
     * Test if rotating once gives rotation one.
     */
    @Test
    void rotateOnce() {
        Receptor receptor = new Receptor();
        receptor.rotate(1);
        assertThat(receptor.getRotation()).isEqualTo(1);
    }

    /**
     * Test if not rotating gives rotation zero.
     */
    @Test
    void rotateZeroTimes() {
        Receptor receptor = new Receptor();
        receptor.rotate(0);
        assertThat(receptor.getRotation()).isEqualTo(0);
    }

    /**
     * Test if rotating four times gives rotation zero.
     */
    @Test
    void rotateFourTimes() {
        Receptor receptor = new Receptor();
        receptor.rotate(4);
        assertThat(receptor.getRotation()).isEqualTo(0);
    }

    /**
     * Test if the receptor starts as marked.
     */
    @Test
    void notMarkedOnStart() {
        Receptor receptor = new Receptor();
        assertThat(receptor.isMarked()).isFalse();
    }

    /**
     * Test if the receptor can accept a ball when it is empty.
     */
    @Test
    void acceptsWhenSlotEmpty() {
        Receptor receptor = new Receptor();
        assertThat(receptor.accepts(Direction.LEFT)).isTrue();
    }

    /**
     * Test if the receptor accepts a ball in an empty slot.
     */
    @Test
    void acceptBall() {
        Receptor receptor = new Receptor();
        Ball ball = Ball.of(Ball.Type.BLUE);
        receptor.accept(Direction.TOP, ball);
        assertThat(receptor.getSlot(Direction.TOP)).isEqualTo(ball);
    }

    /**
     * Test if the receptor does not accept a ball in an occupied slot.
     */
    @Test
    void notAcceptWhenSlotOccupied() {
        Receptor receptor = new Receptor();
        receptor.accept(Direction.TOP, Ball.of(Ball.Type.BLUE));
        assertThat(receptor.accepts(Direction.TOP)).isFalse();
    }
}
