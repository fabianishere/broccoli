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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

class BallTest {
    /**
     * Test if creating a pink ball gives a pink ball.
     */
    @Test
    void createPinkBall() {
        Ball ball = Ball.of(Ball.Type.PINK);
        assertThat(ball.getType()).isEqualTo(Ball.Type.PINK);
    }

    /**
     * Test if creating a blue ball gives a blue ball.
     */
    @Test
    void createBlueBall() {
        Ball ball = Ball.of(Ball.Type.BLUE);
        assertThat(ball.getType()).isEqualTo(Ball.Type.BLUE);
    }

    /**
     * Test if creating a green ball gives a green ball.
     */
    @Test
    void createGreenBall() {
        Ball ball = Ball.of(Ball.Type.GREEN);
        assertThat(ball.getType()).isEqualTo(Ball.Type.GREEN);
    }

    /**
     * Test if creating a yellow ball gives a yellow ball.
     */
    @Test
    void createYellowBall() {
        Ball ball = Ball.of(Ball.Type.YELLOW);
        assertThat(ball.getType()).isEqualTo(Ball.Type.YELLOW);
    }

    /**
     * Test if creating a joker ball gives a joker.
     */
    @Test
    void createJoker() {
        Ball ball = Ball.of(Ball.Type.JOKER);
        assertThat(ball.getType()).isEqualTo(Ball.Type.JOKER);
    }

    /**
     * Test if passing a null throws an error.
     */
    @Test
    void createBallNull() {
        assertThatThrownBy(() -> Ball.of(null)).isInstanceOf(Exception.class);
    }

    /**
     * Test if two balls of the same colour give true.
     */
    @Test
    void isCompatibleSameColour() {
        Ball greenBall1 = Ball.of(Ball.Type.GREEN);
        Ball greenBall2 = Ball.of(Ball.Type.GREEN);
        assertThat(greenBall1.isCompatible(greenBall2)).isTrue();
    }

    /**
     * Test if call made from joker with another colour gives true.
     */
    @Test
    void isCompatibleJokerFirst() {
        Ball joker = Ball.of(Ball.Type.JOKER);
        Ball greenBall = Ball.of(Ball.Type.GREEN);
        assertThat(joker.isCompatible(greenBall)).isTrue();
    }

    /**
     * Test if passing a joker with another colour gives true.
     */
    @Test
    void isCompatibleJokerSecond() {
        Ball joker = Ball.of(Ball.Type.JOKER);
        Ball greenBall = Ball.of(Ball.Type.GREEN);
        assertThat(greenBall.isCompatible(joker)).isTrue();
    }

    /**
     * Test if giving two jokers gives true.
     */
    @Test
    void isCompatibleBothJokers() {
        Ball joker1 = Ball.of(Ball.Type.JOKER);
        Ball joker2 = Ball.of(Ball.Type.JOKER);
        assertThat(joker1.isCompatible(joker2)).isTrue();
    }

    /**
     * Test if passing a null gives false.
     */
    @Test
    void isCompatibleNull() {
        Ball joker = Ball.of(Ball.Type.JOKER);
        assertThat(joker.isCompatible(null)).isFalse();
    }
}


