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

import java.util.Collections;
import java.util.List;

/**
 * A {@link Property} which contains a list value.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ListProperty<T> extends Property<List<T>> {
    /**
     * The type of the elements in the list.
     */
    private final Class<T> elementType;

    /**
     * Construct a {@link ListProperty} instance.
     *
     * @param type         The shape of an element in the list.
     * @param key          The key of the property in the configuration object.
     * @param defaultValue The default value of the property.
     */
    @SuppressWarnings("unchecked")
    public ListProperty(Class<T> type, String key, List<T> defaultValue) {
        super((Class<List<T>>) (Class) List.class, key, defaultValue);
        this.elementType = type;
    }

    /**
     * Construct a {@link ListProperty} instance, initialised with the empty list.
     *
     * @param type The shape of an element in the list.
     * @param key  The key of the property in the configuration object.
     */
    public ListProperty(Class<T> type, String key) {
        this(type, key, Collections.emptyList());
    }

    /**
     * Return the type of an element in the list.
     *
     * @return The type of an element in the list.
     */
    public Class<T> getElementType() {
        return elementType;
    }
}
