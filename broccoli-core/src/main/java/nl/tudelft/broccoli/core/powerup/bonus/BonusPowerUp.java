package nl.tudelft.broccoli.core.powerup.bonus;

import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.receptor.Receptor;

/**
 * This power-up gives you 100 points extra as soon as it is activated.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class BonusPowerUp implements PowerUp {
    /**
     * This method will be invoked when the player has activated the power-up.
     *
     * @param receptor The receptor that has obtained the power-up.
     */
    @Override
    public void activate(Receptor receptor) {
        receptor.getTile().getGrid().getSession().getProgress().score(100);
    }
}
