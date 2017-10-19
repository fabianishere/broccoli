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
        Marble marble = new Marble(MarbleType.PINK);
        assertThat(marble.getType()).isEqualTo(MarbleType.PINK);
    }

    /**
     * Test if creating a blue ball gives a blue ball.
     */
    @Test
    public void createBlueBall() {
        Marble marble = new Marble(MarbleType.BLUE);
        assertThat(marble.getType()).isEqualTo(MarbleType.BLUE);
    }

    /**
     * Test if creating a green ball gives a green ball.
     */
    @Test
    public void createGreenBall() {
        Marble marble = new Marble(MarbleType.GREEN);
        assertThat(marble.getType()).isEqualTo(MarbleType.GREEN);
    }

    /**
     * Test if creating a yellow ball gives a yellow ball.
     */
    @Test
    public void createYellowBall() {
        Marble marble = new Marble(MarbleType.YELLOW);
        assertThat(marble.getType()).isEqualTo(MarbleType.YELLOW);
    }

    /**
     * Test if creating a joker ball gives a joker.
     */
    @Test
    public void createJoker() {
        Marble marble = new Marble(MarbleType.JOKER);
        assertThat(marble.getType()).isEqualTo(MarbleType.JOKER);
    }

    /**
     * Test if passing a null gives it type null.
     */
    @Test
    public void createBallNull() {
        Marble marble = new Marble((MarbleType) null);
        assertThat(marble.getType()).isEqualTo(null);
    }

    /**
     * Test if two balls of the same colour give true.
     */
    @Test
    public void isCompatibleSameColour() {
        Marble greenMarble1 = new Marble(MarbleType.GREEN);
        Marble greenMarble2 = new Marble(MarbleType.GREEN);
        assertThat(greenMarble1.isCompatible(greenMarble2)).isTrue();
    }

    /**
     * Test if two balls of different colours give false.
     */
    @Test
    public void isIncompatibleDifferentColour() {
        Marble green = new Marble(MarbleType.GREEN);
        Marble blue = new Marble(MarbleType.BLUE);
        assertThat(green.isCompatible(blue)).isFalse();
    }

    /**
     * Test if call made from joker with another colour gives true.
     */
    @Test
    public void isCompatibleJokerFirst() {
        Marble joker = new Marble(MarbleType.JOKER);
        Marble greenMarble = new Marble(MarbleType.GREEN);
        assertThat(joker.isCompatible(greenMarble)).isTrue();
    }

    /**
     * Test if passing a joker with another colour gives true.
     */
    @Test
    public void isCompatibleJokerSecond() {
        Marble joker = new Marble(MarbleType.JOKER);
        Marble greenMarble = new Marble(MarbleType.GREEN);
        assertThat(greenMarble.isCompatible(joker)).isTrue();
    }

    /**
     * Test if giving two jokers gives true.
     */
    @Test
    public void isCompatibleBothJokers() {
        Marble joker1 = new Marble(MarbleType.JOKER);
        Marble joker2 = new Marble(MarbleType.JOKER);
        assertThat(joker1.isCompatible(joker2)).isTrue();
    }

    /**
     * Test if passing a null gives false.
     */
    @Test
    public void isCompatibleNull() {
        Marble joker = new Marble(MarbleType.JOKER);
        assertThat(joker.isCompatible(null)).isFalse();
    }
}


