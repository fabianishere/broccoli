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

package nl.tudelft.broccoli.lightbend;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;

import java.io.*;

/**
 * A {@link ConfigurationLoader} for the Lightbend Config library.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class LightbendConfigurationLoader implements ConfigurationLoader {
    /**
     * Load the given file as a {@link Configuration} instance.
     *
     * @param file The file to load.
     * @return The {@link Configuration} that has been loaded.
     * @throws FileNotFoundException if the file was not found.
     */
    @Override
    public Configuration load(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("The given file does not exist");
        }
        return new LightbendConfiguration(ConfigFactory.parseFile(file));
    }

    /**
     * Load the given {@link InputStream} as a {@link Configuration} instance.
     *
     * @param input The input stream to read the configuration from.
     * @return The {@link Configuration} that has been loaded.
     * @throws IOException if the loader failed to read from the input stream.
     */
    @Override
    public Configuration load(InputStream input) throws IOException {
        try {
            return new LightbendConfiguration(ConfigFactory.parseReader(
                new InputStreamReader(input, "UTF-8")));
        } catch (ConfigException.IO exception) {
            throw new IOException(exception);
        }
    }
}
