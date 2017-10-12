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

package nl.tudelft.broccoli.core.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A loader that loads {@link Configuration} from an external source.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public interface ConfigurationLoader {
    /**
     * A stub {@link Configuration} instance.
     */
    Configuration STUB = new Configuration() {
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
     * Load the given {@link File} as a {@link Configuration} instance.
     *
     * @param file The file to load to read the configuration from.
     * @return The {@link Configuration} that has been loaded.
     * @throws FileNotFoundException if the file was not found.
     */
    Configuration load(File file) throws FileNotFoundException;

    /**
     * Load the given {@link InputStream} as a {@link Configuration} instance.
     *
     * @param input The input stream to read the configuration from.
     * @return The {@link Configuration} that has been loaded.
     * @throws IOException if the loader failed to read from the input stream.
     */
    Configuration load(InputStream input) throws IOException;

    /**
     * Try to load the given file as {@link Configuration} instance or return a stubbed instance
     * if the loading failed.
     *
     * @param file The file to load.
     * @return The {@link Configuration} that has been loaded or a stub.
     */
    default Configuration tryLoad(File file) {
        try {
            return load(file);
        } catch (FileNotFoundException ignored) {
            return STUB;
        }
    }

    /**
     * Try to load the given {@link InputStream} as {@link Configuration} instance or return a
     * stubbed instance if the loading failed.
     *
     * @param input The input stream to read the configuration from.
     * @return The {@link Configuration} that has been loaded or a stub.
     */
    default Configuration tryLoad(InputStream input) {
        try {
            return load(input);
        } catch (IOException ignored) {
            return STUB;
        }
    }
}
