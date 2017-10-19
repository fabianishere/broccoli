package nl.tudelft.broccoli.core.receptor;

import nl.tudelft.broccoli.core.grid.TileableListener;

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
}
