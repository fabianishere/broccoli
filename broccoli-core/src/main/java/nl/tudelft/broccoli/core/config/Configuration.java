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
 * A {@link Configuration} instance provides the configuration of the game via a uniform interface,
 * which converts {@link Property} instances into values.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public interface Configuration {
    /**
     * Return the value of the given property as specified in the configuration provided by this
     * interface. If the property does not exits, the method will return the default value of the
     * property.
     *
     * @param property The property to get the value of.
     * @param <T> The shape of the value of the property.
     * @return The value of the property.
     */
    default <T> T get(Property<T> property) {
        return get(property, property.getDefault());
    }

    /**
     * Return the value of the given property as specified in the configuration provided by this
     * interface. If the property does not exits, the method will return the given value.
     *
     * @param property The property to get the value of.
     * @param defaultValue The value of the property in case it does not exist in this object.
     * @param <T> The shape of the value of the property.
     * @return The value of the property.
     */
    <T> T get(Property<T> property, T defaultValue);

    /**
     * Determine whether the given property exists in this {@link Configuration} instance.
     *
     * @param property The property to check whether it exists in this {@link Configuration}
     *                 instance.
     * @return <code>true</code> if the property exists, <code>false</code> otherwise.
     */
    boolean exists(Property<?> property);
}
