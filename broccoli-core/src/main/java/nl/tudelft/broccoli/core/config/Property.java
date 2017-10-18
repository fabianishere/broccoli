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

/**
 * A configuration property which is read from a file.
 *
 * @param <T> The shape of the property value.
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class Property<T> {
    /**
     * The input type of the property.
     */
    private final Class<T> type;

    /**
     * The key of the property in the configuration.
     */
    private final String key;

    /**
     * The default property value.
     */
    private final T defaultValue;

    /**
     * Construct a {@link Property} instance.
     *
     * @param type The shape of the value of the property.
     * @param key The key of the property in the configuration object.
     * @param defaultValue The default value of the property.
     */
    public Property(Class<T> type, String key, T defaultValue) {
        this.type = type;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    /**
     * The shape of the value of the property.
     *
     * @return The shape of the value of the property.
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Return the key of the property in the configuration object.
     *
     * @return The key of the property.
     */
    public String getKey() {
        return key;
    }

    /**
     * Return the default property value.
     *
     * @return The default property value.
     */
    public T getDefault() {
        return defaultValue;
    }

    /**
     * Map the property input value to its output value.
     *
     * @param input The input value provided by the {@link Configuration} object.
     * @return The property output value.
     */
    public T map(T input) {
        return input;
    }
}
