package nl.tudelft.broccoli.core;


import nl.tudelft.broccoli.core.config.IntegerProperty;
import nl.tudelft.broccoli.core.config.Property;

/**
 * A tile that shows the remaining time in the level.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class TimerTile extends Empty {
    /**
     * The configuration property for the maximum amount of time allowed on the level.
     */
    public static final Property<Integer> MAX_TIME = new IntegerProperty("timer.max", 100);

    /**
     * The maximum time allowed.
     */
    private int time;

    /**
     * Construct a {@link TimerTile} instance.
     *
     * @param time The maximum amount of time allowed on the level.
     */
    public TimerTile(int time) {
        this.time = time;
    }

    /**
     * Return the maximum amount of time allowed for the level.
     *
     * @return The maximum amount of time allowed for the level.
     */
    public int getMaxTime() {
        return time;
    }

}
