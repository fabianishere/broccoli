package nl.tudelft.broccoli.core.powerup;

/**
 * An abstract factory class for creating {@link PowerUp} instances.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class PowerUpFactory {
    /**
     * Create a {@link PowerUp} instance.
     *
     * @return The created {@link PowerUp} instance.
     */
    public abstract PowerUp create();
}
