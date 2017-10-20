package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.grid.Grid;

/**
 * An abstract {@link GameSession} class which already implements some of the methods for the user.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 */
public abstract class AbstractGameSession implements GameSession {
    /**
     * The game configuration.
     */
    private final Configuration config;

    /**
     * The grid of this game.
     */
    private final Grid grid;

    /**
     * The progress of the game.
     */
    private final Progress progress;

    /**
     * Construct a {@link AbstractGameSession} instance.
     *
     * @param config The game configuration to use.
     */
    public AbstractGameSession(Configuration config) {
        this.config = config;
        this.grid = new Grid(this, config.get(Grid.WIDTH), config.get(Grid.HEIGHT));
        this.progress = new Progress(grid);
    }

    /**
     * Return the {@link Configuration} of the game.
     *
     * @return The configuration of the game.
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Return the playing grid of this game.
     *
     * @return The {@link Grid} on which the game is played.
     */
    @Override
    public Grid getGrid() {
        return grid;
    }

    /**
     * Return the {@link Progress} of the player in the game.
     *
     * @return The progress of the player in the game.
     */
    @Override
    public Progress getProgress() {
        return progress;
    }
}

