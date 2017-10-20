package nl.tudelft.broccoli.core.powerup;

import java.util.NavigableMap;
import java.util.Random;

/**
 * A {@link PowerUpFactory} that selects a {@link PowerUp} based on the given probability density
 * function representation.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class RandomPowerUpFactory extends PowerUpFactory {
    /**
     * The {@link Random} instance to use.
     */
    private final Random random;

    /**
     * The cumulative probability density function of the {@link PowerUp} implementations
     * represented as {@link NavigableMap}.
     */
    private final NavigableMap<Double, PowerUpFactory> cdf;

    /**
     * Construct a {@link RandomPowerUpFactory} instance.
     *
     * @param random The random instance to use.
     * @param cdf The cumulative probability density function of the {@link PowerUp} implementations
     *            represented as {@link NavigableMap}.
     */
    public RandomPowerUpFactory(Random random, NavigableMap<Double, PowerUpFactory> cdf) {
        this.random = random;
        this.cdf = cdf;
    }

    /**
     * Create a {@link PowerUp} instance.
     *
     * @return The created {@link PowerUp} instance.
     */
    @Override
    public PowerUp create() {
        return cdf.floorEntry(random.nextDouble()).getValue().create();
    }
}
