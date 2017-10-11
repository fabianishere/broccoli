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

package nl.tudelft.broccoli.core.level;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.Property;
import org.junit.Test;

/**
 * Testing class that tests the {@link SimpleLevel} class.
 */
public class SimpleLevelTest {

    private SimpleLevel simpleLevel = new SimpleLevel();
    private Configuration config = new Configuration() {
        @Override
        public <T> T get(Property<T> property, T defaultValue) {
            return defaultValue;
        }

        @Override
        public boolean exists(Property<?> property) {
            return false;
        }
    };

    /**
     * Test if calling the create function gives an instance of a {@link GameSession}.
     */
    @Test
    public void createTest() {
        assertThat(simpleLevel.create(config)).isInstanceOf(GameSession.class);
    }

    /**
     * Test to see if getting the name of the level is simple.
     */
    @Test
    public void getNameTest() {
        assertThat(simpleLevel.getName()).isEqualTo("simple");
    }
}
