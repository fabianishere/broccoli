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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tu.delft.defpro.api.IDefProAPI;
import nl.tu.delft.defpro.exception.NotExistingVariableException;
import nl.tudelft.broccoli.core.config.*;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * A test suite for the {@link DefProConfiguration} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class DefProConfigurationTest {
    /**
     * The {@link Configuration} instance to test.
     */
    private DefProConfiguration config;

    /**
     * The {@link IDefProAPI} mock to use.
     */
    private IDefProAPI api;

    /**
     * Set up the test.
     */
    @Before
    public void setUp() throws Exception {
        api = mock(IDefProAPI.class);
        config = new DefProConfiguration(api);
    }

    /**
     * Test whether the default value is returned for a property that is not supported.
     */
    @Test
    public void getUnsupportedProperty() {
        Object object = new Object();
        assertThat(config.get(new Property<Object>(Object.class, "", object) {}))
            .isEqualTo(object);
    }

    /**
     * Test whether an existing property makes the method return true.
     */
    @Test
    public void propertyExists() throws Exception {
        when(api.getStringValueOf("test")).thenReturn("");
        assertThat(config.exists(new StringProperty("test", ""))).isTrue();
    }


    /**
     * Test whether an non-existing property makes the method return false.
     */
    @Test
    public void propertyNotExists() throws Exception {
        when(api.getStringValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.exists(new StringProperty("test", ""))).isFalse();
    }

    /**
     * Test whether the correct boolean value is returned for the given key.
     */
    @Test
    public void getBooleanProperty() throws Exception {
        when(api.getBooleanValueOf("a")).thenReturn(true);
        assertThat(config.get(new BooleanProperty("a", false))).isTrue();
    }

    /**
     * Test whether the default boolean value is returned for the given key if it is non existing.
     */
    @Test
    public void getDefaultBooleanProperty() throws Exception {
        when(api.getBooleanValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new BooleanProperty("a", false))).isFalse();
    }

    /**
     * Test whether the correct double value is returned for the given key.
     */
    @Test
    public void getDoubleProperty() throws Exception {
        when(api.getRealValueOf("a")).thenReturn(1.0);
        assertThat(config.get(new DoubleProperty("a", 1.0))).isEqualTo(1.0);
    }

    /**
     * Test whether the default double value is returned for the given key if it is non existing.
     */
    @Test
    public void getDefaultDoubleProperty() throws Exception {
        when(api.getRealValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new DoubleProperty("a", 1.0))).isEqualTo(1.0);
    }

    /**
     * Test whether the correct integer value is returned for the given key.
     */
    @Test
    public void getIntegerProperty() throws Exception {
        when(api.getIntegerValueOf("a")).thenReturn(1);
        assertThat(config.get(new IntegerProperty("a", 1))).isEqualTo(1);
    }

    /**
     * Test whether the default integer value is returned for the given key if it is non existing.
     */
    @Test
    public void getDefaultIntegerProperty() throws Exception {
        when(api.getIntegerValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new IntegerProperty("a", 1))).isEqualTo(1);
    }

    /**
     * Test whether the correct string value is returned for the given key.
     */
    @Test
    public void getStringProperty() throws Exception {
        when(api.getStringValueOf("a")).thenReturn("b");
        assertThat(config.get(new StringProperty("a", "c"))).isEqualTo("b");
    }

    /**
     * Test whether the default string value is returned for the given key if it is non existing.
     */
    @Test
    public void getDefaultStringProperty() throws Exception {
        when(api.getStringValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new StringProperty("a", "c"))).isEqualTo("c");
    }

    /**
     * Test whether the correct list of booleans is returned for the given key.
     */
    @Test
    public void getUnsupportedListProperty() throws Exception {
        List<Object> list = Lists.newArrayList(new Object());
        assertThat(config.get(new ListProperty<>(Object.class, "a", list))).isEqualTo(list);
    }

    /**
     * Test whether the correct list of booleans is returned for the given key.
     */
    @Test
    public void getBooleanListProperty() throws Exception {
        List<Boolean> list = Lists.newArrayList(false, true);
        when(api.getListBoolValueOf("a")).thenReturn(list);
        assertThat(config.get(new ListProperty<>(Boolean.class, "a", Lists.emptyList())))
            .isEqualTo(list);
    }

    /**
     * Test whether the default list of booleans is returned for the given key if it is non
     * existing.
     */
    @Test
    public void getDefaultBooleanListProperty() throws Exception {
        when(api.getListRealValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new ListProperty<>(Boolean.class,"a", Lists.emptyList())))
            .isEqualTo(Lists.emptyList());
    }

    /**
     * Test whether the correct list of doubles is returned for the given key.
     */
    @Test
    public void getDoubleListProperty() throws Exception {
        List<Double> list = Lists.newArrayList(1.0, 2.0);
        when(api.getListRealValueOf("a")).thenReturn(list);
        assertThat(config.get(new ListProperty<>(Double.class, "a", Lists.emptyList())))
            .isEqualTo(list);
    }

    /**
     * Test whether the default list of doubles is returned for the given key if it is non existing.
     */
    @Test
    public void getDefaultDoubleListProperty() throws Exception {
        when(api.getListRealValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new ListProperty<>(Double.class,"a", Lists.emptyList())))
            .isEqualTo(Lists.emptyList());
    }

    /**
     * Test whether the correct list of integers is returned for the given key.
     */
    @Test
    public void getIntegerListProperty() throws Exception {
        List<Integer> list = Lists.newArrayList(1, 2);
        when(api.getListIntValueOf("a")).thenReturn(list);
        assertThat(config.get(new ListProperty<>(Integer.class, "a", Lists.emptyList())))
            .isEqualTo(list);
    }

    /**
     * Test whether the default list of integers is returned for the given key if it is non
     * existing.
     */
    @Test
    public void getDefaultIntegerListProperty() throws Exception {
        when(api.getListIntValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new ListProperty<>(Integer.class,"a", Lists.emptyList())))
            .isEqualTo(Lists.emptyList());
    }

    /**
     * Test whether the correct list of strings is returned for the given key.
     */
    @Test
    public void getStringListProperty() throws Exception {
        List<String> list = Lists.newArrayList("a", "b");
        when(api.getListStringValueOf("a")).thenReturn(list);
        assertThat(config.get(new ListProperty<>(String.class, "a", Lists.emptyList())))
            .isEqualTo(list);
    }

    /**
     * Test whether the default list of strings is returned for the given key if it is non
     * existing.
     */
    @Test
    public void getDefaultStringListProperty() throws Exception {
        when(api.getListStringValueOf(anyString())).thenThrow(new NotExistingVariableException(""));
        assertThat(config.get(new ListProperty<>(String.class,"a", Lists.emptyList())))
            .isEqualTo(Lists.emptyList());
    }
}
