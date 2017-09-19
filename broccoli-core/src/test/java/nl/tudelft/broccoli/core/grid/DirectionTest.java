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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * A class to test the {@link Direction} enum
 */
class DirectionTest {
    /**
     * Tests the inverse method with Top.
     */
    @Test
    void inverseTestTop(){
        assertThat(Direction.TOP.inverse()).isEqualTo(Direction.BOTTOM);
    }

    /**
     * Tests the inverse method with Bottom.
     */
    @Test
    void inverseTestBottom(){
        assertThat(Direction.BOTTOM.inverse()).isEqualTo(Direction.TOP);
    }

    /**
     * Tests the inverse method with Left.
     */
    @Test
    void inverseTestLeft(){
        assertThat(Direction.LEFT.inverse()).isEqualTo(Direction.RIGHT);
    }

    /**
     * Tests the inverse method with Right.
     */
    @Test
    void inverseTestRight(){
        assertThat(Direction.RIGHT.inverse()).isEqualTo(Direction.LEFT);
    }

    /**
     * Tests the inverse method with Null.
     */
    @Test
    void inverseTestNull(){
        assertThat(((Direction) null).inverse()).isEqualTo(null);
    }
}
