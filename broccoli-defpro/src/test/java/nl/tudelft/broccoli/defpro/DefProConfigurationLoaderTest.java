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

package nl.tudelft.broccoli.defpro;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.config.BooleanProperty;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Test suite for {@link DefProConfigurationLoader} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class DefProConfigurationLoaderTest {
    /**
     * The {@link ConfigurationLoader} instance to test.
     */
    private DefProConfigurationLoader loader;

    /**
     * Set up the test.
     */
    @Before
    public void setUp() throws Exception {
        loader = new DefProConfigurationLoader();
    }

    @Test
    public void nonExistentFile() throws Exception {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);

        assertThatThrownBy(() -> loader.load(file)).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    public void stubReturnsFalse() throws Exception {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);

        assertThat(loader.tryLoad(file).exists(new BooleanProperty("a"))).isFalse();
    }

    @Test
    public void stubReturnsDefault() throws Exception {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);

        assertThat(loader.tryLoad(file).get(new BooleanProperty("a"))).isFalse();
    }

    @Test
    public void existentFile() throws Exception {
        File file = File.createTempFile("defpro", "test");
        loader.load(file);
        file.deleteOnExit();
    }
}
