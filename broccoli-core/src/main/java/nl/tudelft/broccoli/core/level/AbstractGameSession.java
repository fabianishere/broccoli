package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.powerup.PowerUpFactory;
import nl.tudelft.broccoli.core.powerup.RandomPowerUpFactory;
import nl.tudelft.broccoli.core.powerup.bonus.BonusPowerUpFactory;
import nl.tudelft.broccoli.core.powerup.joker.JokerPowerUpFactory;
import nl.tudelft.broccoli.core.receptor.Receptor;

import java.util.*;
import java.util.stream.Collectors;

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
     * The {@link NexusContext} instance.
     */
    private final NexusContext nexusContext;

    /**
     * The {@link PowerUpFactory} to use.
     */
    private final PowerUpFactory powerUpFactory;

    /**
     * Construct a {@link AbstractGameSession} instance.
     *
     * @param config The game configuration to use.
     */
    public AbstractGameSession(Configuration config) {
        this.config = config;
        this.grid = new Grid(this, config.get(Grid.WIDTH), config.get(Grid.HEIGHT));
        this.progress = new Progress(grid);

        // Read the initial sequence of balls from the configuration
        List<String> initial = config.get(SpawningNexus.INITIAL_SEQUENCE);
        Queue<MarbleType> queue = new ArrayDeque<>(initial.stream()
            .map(str -> {
                try {
                    return MarbleType.valueOf(str);
                } catch (IllegalArgumentException e) {
                    return MarbleType.BLUE;
                }
            }).collect(Collectors.toList())
        );

        this.nexusContext = new NexusContext(queue, new Random(),
            config.get(SpawningNexus.JOKER_PROBABILITY));

        NavigableMap<Double, PowerUpFactory> cdf = new TreeMap<>();
        cdf.put(0.0, new BonusPowerUpFactory());
        cdf.put(0.8, new JokerPowerUpFactory());
        this.powerUpFactory = new RandomPowerUpFactory(new Random(), cdf);
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

    /**
     * Return the {@link NexusContext} of the game session.
     *
     * @return The context of the nexus instances.
     */
    @Override
    public NexusContext getNexusContext() {
        return nexusContext;
    }

    /**
     * Return the {@link PowerUpFactory} used by this {@link GameSession} to create new
     * {@link PowerUp}s to be assigned to {@link Receptor}s.
     *
     * @return The power-up factory of the game session.
     */
    @Override
    public PowerUpFactory getPowerUpFactory() {
        return powerUpFactory;
    }
}

