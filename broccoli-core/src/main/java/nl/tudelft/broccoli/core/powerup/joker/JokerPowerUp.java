package nl.tudelft.broccoli.core.powerup.joker;

import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.receptor.Receptor;

/**
 * This power-up spawns two joker marbles as soon as it is activated.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class JokerPowerUp implements PowerUp {
    /**
     * This method will be invoked when the player has activated the power-up.
     *
     * @param receptor The receptor that has obtained the power-up.
     */
    @Override
    public void activate(Receptor receptor) {
        NexusContext context = receptor.getTile().getGrid().getSession().getNexusContext();
        context.add(MarbleType.JOKER, MarbleType.JOKER);
    }
}
