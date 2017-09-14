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

public class BallTest {

    @Test
    public void createPinkBall() {
        Ball ball = Ball.of(Ball.Type.PINK);
        assertThat(ball.getType()).isEqualTo(Ball.Type.PINK);
    }

    @Test
    public void createBlueBall() {
        Ball ball = Ball.of(Ball.Type.BLUE);
        assertThat(ball.getType()).isEqualTo(Ball.Type.BLUE);
    }

    @Test
    public void createGreenBall() {
        Ball ball = Ball.of(Ball.Type.GREEN);
        assertThat(ball.getType()).isEqualTo(Ball.Type.GREEN);
    }

    @Test
    public void createYellowBall() {
        Ball ball = Ball.of(Ball.Type.YELLOW);
        assertThat(ball.getType()).isEqualTo(Ball.Type.YELLOW);
    }

    @Test
    public void createBallNull() {
        assertThatThrownBy(() -> Ball.of(null)).isInstanceOf(Exception.class);
    }

    @Test
    public void isCompatibleSameColour() {
        Ball greenBall1 = Ball.of(Ball.Type.GREEN);
        Ball greenBall2 = Ball.of(Ball.Type.GREEN);
        assertThat(greenBall1.isCompatible(greenBall2)).isTrue();
    }

    @Test
    public void isCompatibleJokerFirst() {
        Ball joker = Ball.of(Ball.Type.JOKER);
        Ball greenBall = Ball.of(Ball.Type.GREEN);
        assertThat(joker.isCompatible(greenBall)).isTrue();
    }

    @Test
    public void isCompatibleJokerSecond() {
        Ball joker = Ball.of(Ball.Type.JOKER);
        Ball greenBall = Ball.of(Ball.Type.GREEN);
        assertThat(greenBall.isCompatible(joker)).isTrue();
    }

    @Test
    public void isCompatibleBothJokers() {
        Ball joker1 = Ball.of(Ball.Type.JOKER);
        Ball joker2 = Ball.of(Ball.Type.JOKER);
        assertThat(joker1.isCompatible(joker2)).isTrue();
    }

    @Test
    public void isCompatibleNull() {
        Ball joker = Ball.of(Ball.Type.JOKER);
        assertThat(joker.isCompatible(null)).isFalse();
    }
}


