package nl.tudelft.broccoli.core.powerup;

import nl.tudelft.broccoli.core.receptor.Receptor;

/**
 * A power-up that can be applied to a {@link Receptor} as soon as it is obtained by that receptor.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public interface PowerUp {
    /**
     * This method will be invoked when the player has activated the power-up.
     *
     * @param receptor The receptor that has obtained the power-up.
     */
    void activate(Receptor receptor);
}
