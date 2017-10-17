package nl.tudelft.broccoli.lightbend;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.ListProperty;
import nl.tudelft.broccoli.core.config.Property;

import java.util.List;

/**
 * A {@link Configuration} implementation backed by the Lightbend configuration library.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class LightbendConfiguration implements Configuration {
    /**
     * The underlying {@link Config} instance to get the property values with.
     */
    private final Config api;

    /**
     * Construct a {@link LightbendConfiguration} instance.
     *
     * @param api The backing {@link Config} instance to use.
     */
    public LightbendConfiguration(Config api) {
        this.api = api;
    }

    /**
     * Return the value of the given property as specified in the configuration provided by this
     * interface. If the property does not exits, the method will return the given value.
     *
     * @param property     The property to get the value of.
     * @param defaultValue The value of the property in case it does not exist in this object.
     * @return The value of the property.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Property<T> property, T defaultValue) {
        // Due to type erasure, we cannot have a nice API without having these unchecked casts in
        // the implementation. We checked them for you; they should work.
        Class<T> type = property.getType();
        String key = property.getKey();
        Object value = null;

        try {
            if (Boolean.class.isAssignableFrom(type)) {
                value = api.getBoolean(key);
            } else if (Double.class.isAssignableFrom(type)) {
                value = api.getDouble(key);
            } else if (Integer.class.isAssignableFrom(type)) {
                value = api.getInt(key);
            } else if (String.class.isAssignableFrom(type)) {
                value = api.getString(key);
            } else if (property instanceof ListProperty) {
                ListProperty listProperty = (ListProperty) property;
                value = getList(listProperty);
            }
        } catch (ConfigException ignored) {
            // We ignore the exception that has been thrown since we just return the default value.
        }

        // We do not permit null configuration values and return the default value instead.
        if (value == null) {
            return defaultValue;
        }

        return property.map((T) value);
    }

    /**
     * Determine whether the given property exists in this {@link Configuration} instance.
     *
     * @param property The property to check whether it exists in this {@link Configuration}
     *                 instance.
     * @return <code>true</code> if the property exists, <code>false</code> otherwise.
     */
    @Override
    public boolean exists(Property<?> property) {
        return api.hasPath(property.getKey());
    }

    /**
     * Return the value of the given list property.
     *
     * @param property The property to get the value of.
     * @return The value of the property.
     */
    private List getList(ListProperty property) throws ConfigException {
        Class type = property.getElementType();

        if (Boolean.class.isAssignableFrom(type)) {
            return api.getBooleanList(property.getKey());
        } else if (Double.class.isAssignableFrom(type)) {
            return api.getDoubleList(property.getKey());
        } else if (Integer.class.isAssignableFrom(type)) {
            return api.getIntList(property.getKey());
        } else if (String.class.isAssignableFrom(type)) {
            return api.getStringList(property.getKey());
        }

        return null;
    }
}
