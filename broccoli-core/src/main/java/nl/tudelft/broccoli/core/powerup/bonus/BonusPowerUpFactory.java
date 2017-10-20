package nl.tudelft.broccoli.core.powerup.bonus;

import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.powerup.PowerUpFactory;

/**
 * A {@link PowerUpFactory} that generates {@link BonusPowerUp}s.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class BonusPowerUpFactory extends PowerUpFactory {
    /**
     * Create a {@link PowerUp} instance.
     *
     * @return The created {@link PowerUp} instance.
     */
    @Override
    public PowerUp create() {
        return new BonusPowerUp();
    }
}
