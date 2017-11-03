package nl.tudelft.broccoli.core.level;

/**
 * An abstract factory class which produces {@link Level} instances, based on a given index.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 */
public abstract class LevelFactory {
    /**
     * Create a {@link Level} for the given index.
     *
     * @param level The index of the level to create.
     * @return The {@link Level} that is generated.
     */
    public abstract Level create(int level);
}
