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
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;

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
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 */
class MediumLevel implements Level {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public GameSession create(Configuration config) {
        return new MediumGame(config);
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
     * Return the difficulty of this level.
     *
     * @return The difficulty of this level.
     */
    @Override
    public Difficulty getDifficulty() {
        return Difficulty.MEDIUM;
    }

    /**
     * A very basic, static {@link GameSession} created by {@link EasyLevel} instances.
     */
    private class MediumGame extends AbstractGameSession {
        /**
         * Construct a {@link MediumGame} instance.
         *
         * @param config The game configuration to use.
         */
        public MediumGame(Configuration config) {
            super(config);
            Grid grid = getGrid();

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

            grid.place(1, 2, new HorizontalTrack());
            grid.place(2, 2, new Receptor());
            grid.place(2, 1, new VerticalTrack());


            grid.place(2, 0, new Receptor());
        }

        /**
         * Return the {@link Level} this game represents.
         *
         * @return The level of this game.
         */
        public Level getLevel() {
            return MediumLevel.this;
        }
    }
}
