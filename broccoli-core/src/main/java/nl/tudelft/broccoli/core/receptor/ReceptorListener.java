package nl.tudelft.broccoli.core.receptor;

import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.powerup.PowerUp;

/**
 * A listener interface for events related to a {@link Receptor} instance.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public interface ReceptorListener extends TileableListener {
    /**
     * This method is invoked when a {@link Receptor} was marked.
     *
     * @param receptor The receptor that has been marked.
     */
    default void receptorMarked(Receptor receptor) {}

    /**
     * This method is invoked when a {@link Receptor} has been assigned a {@link PowerUp} it can
     * activate.
     *
     * @param receptor The receptor to which the {@link PowerUp} is assigned.
     */
    default void receptorAssigned(Receptor receptor) {}
}
