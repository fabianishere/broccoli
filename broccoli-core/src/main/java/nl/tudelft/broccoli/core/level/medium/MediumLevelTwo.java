package nl.tudelft.broccoli.core.level.medium;

import nl.tudelft.broccoli.core.Announcer;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.level.*;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.FilterTrack;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;

public class MediumLevelTwo extends AbstractMediumLevel {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public GameSession create(Configuration config) {
        return new MediumLevelTwo.MediumGameTwo(config);
    }

    /**
     * Return the index of this level.
     *
     * @return The index of this level.
     */
    @Override
    public int getIndex() {
        return 2;
    }

    /**
     * A very basic, static {@link GameSession} created by {@link AbstractMediumLevel} instances.
     */
    private class MediumGameTwo extends AbstractGameSession {

        /**
         * Construct a {@link MediumGameTwo} instance.
         *
         * @param config The game configuration to use.
         */
        public MediumGameTwo(Configuration config) {
            super(config, 6 ,6);
            Grid grid = getGrid();

            grid.place(0, 5, new Nexus(getNexusContext()));
            grid.place(1, 5, new Nexus(getNexusContext()));
            grid.place(2, 5, new Nexus(getNexusContext()));
            grid.place(3, 5, new Nexus(getNexusContext()));
            grid.place(4, 5, new Nexus(getNexusContext()));
            grid.place(5, 5, new SpawningNexus(getNexusContext(), Direction.RIGHT));

            grid.place(5, 4, new Announcer());
            grid.place(5, 3, new TimerTile(config.get(TimerTile.MAX_TIME)));

            grid.place(0, 4, new Receptor());
            grid.place(0, 3, new VerticalTrack());

            grid.place(1, 4, new HorizontalTrack());
            grid.place(2, 4, new Receptor());
            grid.place(2, 3, new VerticalTrack());

            grid.place(0, 2, new FilterTrack(new VerticalTrack(), MarbleType.PINK));
            grid.place(0, 1, new VerticalTrack());

            grid.place(1, 2, new HorizontalTrack());
            grid.place(2, 1, new VerticalTrack());
            grid.place(2, 2, new FilterTrack(new VerticalTrack(), MarbleType.GREEN));


            grid.place(4, 4, new Receptor());
            grid.place(3, 4, new HorizontalTrack());
            grid.place(4, 3, new VerticalTrack());
            grid.place(4, 2, new FilterTrack(new VerticalTrack(), MarbleType.BLUE));
            grid.place(4, 1, new VerticalTrack());
            grid.place(4, 0, new Receptor());
            grid.place(2, 0, new Receptor());
            grid.place(3, 0, new HorizontalTrack());
            grid.place(3, 2, new HorizontalTrack());


            grid.place(0, 0, new Receptor());
            grid.place(1, 0, new HorizontalTrack());
        }

        /**
         * Return the {@link Level} this game represents.
         *
         * @return The level of this game.
         */
        public Level getLevel() {
            return MediumLevelTwo.this;
        }
    }
}

