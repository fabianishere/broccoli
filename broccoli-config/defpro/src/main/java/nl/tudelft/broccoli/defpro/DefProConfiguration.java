package nl.tudelft.broccoli.defpro;

import nl.tu.delft.defpro.api.IDefProAPI;
import nl.tu.delft.defpro.exception.NotExistingVariableException;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.ListProperty;
import nl.tudelft.broccoli.core.config.Property;

import java.util.List;

/**
 * A {@link Configuration} implementation backed by the Defensive Programming Reader API.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class DefProConfiguration implements Configuration {
    /**
     * The underlying {@link IDefProAPI} instance to get the property values.
     */
    private final IDefProAPI api;

    /**
     * Construct a {@link DefProConfiguration} instance.
     *
     * @param api The backing {@link IDefProAPI} instance to use.
     */
    public DefProConfiguration(IDefProAPI api) {
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
                value = api.getBooleanValueOf(key);
            } else if (Double.class.isAssignableFrom(type)) {
                value = api.getRealValueOf(key);
            } else if (Integer.class.isAssignableFrom(type)) {
                value = api.getIntegerValueOf(key);
            } else if (String.class.isAssignableFrom(type)) {
                value = api.getStringValueOf(key);
            } else if (property instanceof ListProperty) {
                ListProperty listProperty = (ListProperty) property;
                value = getList(listProperty, (List) listProperty.getDefault());
            }
        } catch (NotExistingVariableException ignored) {
            // We ignore the exception that has been thrown since we just return the default value.
        }

        // We do not permit null configuration values and return the default value instead.
        if (value == null) {
            return defaultValue;
        }

        return (T) value;
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
        try {
            return api.getStringValueOf(property.getKey()) != null;
        } catch (NotExistingVariableException ignored) {
            return false;
        }
    }

    /**
     * Return the value of the given list property.
     *
     * @param property The property to get the value of.
     * @param defaultValue The value of the property in case it does not exist in this object.
     * @return The value of the property.
     */
    private List getList(ListProperty property, List defaultValue) {
        Class type = property.getElementType();

        try {
            if (Boolean.class.isAssignableFrom(type)) {
                return api.getListBoolValueOf(property.getKey());
            } else if (Double.class.isAssignableFrom(type)) {
                return api.getListRealValueOf(property.getKey());
            } else if (Integer.class.isAssignableFrom(type)) {
                return api.getListIntValueOf(property.getKey());
            } else if (String.class.isAssignableFrom(type)) {
                return api.getListStringValueOf(property.getKey());
            }
        } catch (NotExistingVariableException ignored) {
            // We ignore the exception that has been thrown since we just return the default value.
        }

        return defaultValue;
    }
}
