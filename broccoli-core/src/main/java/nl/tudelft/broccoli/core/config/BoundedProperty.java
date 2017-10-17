package nl.tudelft.broccoli.core.config;

/**
 * A {@link Property} for which the value lies in a pre-specified domain.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class BoundedProperty<T extends Comparable<T>> extends Property<T> {
    /**
     * The lower limit.
     */
    private final T lower;

    /**
     * The upper limit.
     */
    private final T upper;

    /**
     * Construct a {@link BoundedProperty} instance.
     *
     * @param property The property to bound.
     * @param lower The lower limit of the property.
     * @param upper The upper limit of the property.
     */
    public BoundedProperty(Property<T> property, T lower, T upper) {
        super(property.getType(), property.getKey(), property.getDefault());
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * Map the property input value to its output value.
     *
     * @param input The input value provided by the {@link Configuration} object.
     * @return The property output value.
     */
    @Override
    public T map(T input) {
        if (input.compareTo(lower) < 0) {
            return lower;
        } else if (input.compareTo(upper) > 0) {
            return upper;
        }

        return input;
    }
}
