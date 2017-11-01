package nl.tudelft.broccoli.core.level.medium;

import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.level.LevelFactory;

/**
 * A abstract class {@link Level} that represents levels with medium difficulty.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class AbstractMediumLevel implements Level {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public abstract GameSession create(Configuration config);

    /**
     * Return the {@link LevelFactory} of this level.
     *
     * @return The factory that this level was created by.
     */
    @Override
    public LevelFactory getFactory() {
        return new MediumLevelFactory();
    }

    /**
     * Return the index of this level.
     *
     * @return The index of this level.
     */
    @Override
    public abstract int getIndex();
}
