package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.Announcer;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.*;

/**
 * A very basic, static {@link Level} used for testing purposes.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
class EasyLevel implements Level {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public GameSession create(Configuration config) {
        return new EasyGame(config);
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
        return Difficulty.EASY;
    }

    /**
     * A very basic, static {@link GameSession} created by {@link EasyLevel} instances.
     */
    private class EasyGame extends AbstractGameSession {

        /**
         * Construct a {@link EasyGame} instance.
         *
         * @param config The game configuration to use.
         */
        public EasyGame(Configuration config) {
            super(config, 4, 4);
            Grid grid = getGrid();

            grid.place(0, 3, new Nexus(getNexusContext()));
            grid.place(1, 3, new Nexus(getNexusContext()));
            grid.place(2, 3, new Nexus(getNexusContext()));
            grid.place(3, 3, new SpawningNexus(getNexusContext(), Direction.RIGHT));

            grid.place(3, 2, new Announcer());
            grid.place(3, 1, new TimerTile(config.get(TimerTile.MAX_TIME)));

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
         * Return the {@link Level} this game represents.
         *
         * @return The level of this game.
         */
        public Level getLevel() {
            return EasyLevel.this;
        }
    }
}
