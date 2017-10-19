package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.*;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A very basic, static {@link Level} used for testing purposes.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class SimpleLevel implements Level {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public GameSession create(Configuration config) {
        return new SimpleGame(config);
    }

    /**
     * Return the name of this level.
     *
     * @return A string representing the name of this level.
     */
    @Override
    public String getName() {
        return "simple";
    }

    /**
     * A very basic, static {@link GameSession} created by {@link SimpleLevel} instances.
     */
    private class SimpleGame implements GameSession {
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
         * Construct a {@link SimpleGame} instance.
         *
         * @param config The game configuration to use.
         */
        public SimpleGame(Configuration config) {
            this.config = config;
            this.grid = new Grid(this, config.get(Grid.WIDTH), config.get(Grid.HEIGHT));
            this.progress = new Progress(grid);

            NexusContext context = new NexusContext();
            grid.place(0, 3, new Nexus(context));
            grid.place(1, 3, new Nexus(context));
            grid.place(2, 3, new Nexus(context));

            // Read the initial sequence of balls from the configuration
            List<String> initialStrings = config.get(SpawningNexus.INITIAL_SEQUENCE);
            Queue<MarbleType> initial = new ArrayDeque<>(initialStrings.stream()
                .map(str -> {
                    try {
                        return MarbleType.valueOf(str);
                    } catch (IllegalArgumentException e) {
                        return MarbleType.BLUE;
                    }
                }).collect(Collectors.toList())
            );

            grid.place(3, 3, new SpawningNexus(context, new Random(), Direction.RIGHT,
                config.get(SpawningNexus.JOKER_PROBABILITY), initial));

            TimerTile timer = new TimerTile(config.get(TimerTile.MAX_TIME));
            grid.place(3, 2, timer);

            grid.place(0, 2, new Receptor());
            grid.place(0, 1, new VerticalTrack());

            grid.place(1, 2, new HorizontalTrack());
            grid.place(2, 2, new Receptor());
            grid.place(2, 1, new VerticalTrack());

            grid.place(0, 0, new Receptor());
            grid.place(1, 0, new FilterTrack(new HorizontalTrack(), MarbleType.GREEN));

            grid.place(2, 0, new Receptor());
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
         * Return the {@link Level} this game represents.
         *
         * @return The level of this game.
         */
        @Override
        public Level getLevel() {
            return SimpleLevel.this;
        }
    }
}
