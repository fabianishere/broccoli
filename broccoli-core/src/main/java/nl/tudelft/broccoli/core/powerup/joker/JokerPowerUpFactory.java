package nl.tudelft.broccoli.core.powerup.joker;

import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.powerup.PowerUpFactory;

/**
 * A {@link PowerUpFactory} that generates {@link JokerPowerUp}s.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class JokerPowerUpFactory extends PowerUpFactory {
    /**
     * Create a {@link PowerUp} instance.
     *
     * @return The created {@link PowerUp} instance.
     */
    @Override
    public PowerUp create() {
        return new JokerPowerUp();
    }
}
