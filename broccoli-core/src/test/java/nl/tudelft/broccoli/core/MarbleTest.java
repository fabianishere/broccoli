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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for the {@link Marble} class.
 */
public class MarbleTest {
    /**
     * Test if creating a pink ball gives a pink ball.
     */
    @Test
    public void createPinkBall() {
        Marble marble = Marble.of(Marble.Type.PINK);
        assertThat(marble.getType()).isEqualTo(Marble.Type.PINK);
    }

    /**
     * Test if creating a blue ball gives a blue ball.
     */
    @Test
    public void createBlueBall() {
        Marble marble = Marble.of(Marble.Type.BLUE);
        assertThat(marble.getType()).isEqualTo(Marble.Type.BLUE);
    }

    /**
     * Test if creating a green ball gives a green ball.
     */
    @Test
    public void createGreenBall() {
        Marble marble = Marble.of(Marble.Type.GREEN);
        assertThat(marble.getType()).isEqualTo(Marble.Type.GREEN);
    }

    /**
     * Test if creating a yellow ball gives a yellow ball.
     */
    @Test
    public void createYellowBall() {
        Marble marble = Marble.of(Marble.Type.YELLOW);
        assertThat(marble.getType()).isEqualTo(Marble.Type.YELLOW);
    }

    /**
     * Test if creating a joker ball gives a joker.
     */
    @Test
    public void createJoker() {
        Marble marble = Marble.of(Marble.Type.JOKER);
        assertThat(marble.getType()).isEqualTo(Marble.Type.JOKER);
    }

    /**
     * Test if passing a null gives it type null.
     */
    @Test
    public void createBallNull() {
        Marble marble = Marble.of(null);
        assertThat(marble.getType()).isEqualTo(null);
    }

    /**
     * Test if two balls of the same colour give true.
     */
    @Test
    public void isCompatibleSameColour() {
        Marble greenMarble1 = Marble.of(Marble.Type.GREEN);
        Marble greenMarble2 = Marble.of(Marble.Type.GREEN);
        assertThat(greenMarble1.isCompatible(greenMarble2)).isTrue();
    }

    /**
     * Test if two balls of different colours give false.
     */
    @Test
    public void isIncompatibleDifferentColour() {
        Marble green = Marble.of(Marble.Type.GREEN);
        Marble blue = Marble.of(Marble.Type.BLUE);
        assertThat(green.isCompatible(blue)).isFalse();
    }

    /**
     * Test if call made from joker with another colour gives true.
     */
    @Test
    public void isCompatibleJokerFirst() {
        Marble joker = Marble.of(Marble.Type.JOKER);
        Marble greenMarble = Marble.of(Marble.Type.GREEN);
        assertThat(joker.isCompatible(greenMarble)).isTrue();
    }

    /**
     * Test if passing a joker with another colour gives true.
     */
    @Test
    public void isCompatibleJokerSecond() {
        Marble joker = Marble.of(Marble.Type.JOKER);
        Marble greenMarble = Marble.of(Marble.Type.GREEN);
        assertThat(greenMarble.isCompatible(joker)).isTrue();
    }

    /**
     * Test if giving two jokers gives true.
     */
    @Test
    public void isCompatibleBothJokers() {
        Marble joker1 = Marble.of(Marble.Type.JOKER);
        Marble joker2 = Marble.of(Marble.Type.JOKER);
        assertThat(joker1.isCompatible(joker2)).isTrue();
    }

    /**
     * Test if passing a null gives false.
     */
    @Test
    public void isCompatibleNull() {
        Marble joker = Marble.of(Marble.Type.JOKER);
        assertThat(joker.isCompatible(null)).isFalse();
    }
}


