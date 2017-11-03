package nl.tudelft.broccoli.core.level.easy;

import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.level.*;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;

public class EasyLevelTwo extends AbstractEasyLevel {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public GameSession create(Configuration config) {
        return new EasyLevelTwo.EasyGameTwo(config);
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
     * A very basic, static {@link GameSession} created by {@link AbstractEasyLevel} instances.
     */
    private class EasyGameTwo extends AbstractGameSession {

        /**
         * Construct a {@link EasyGameTwo} instance.
         *
         * @param config The game configuration to use.
         */
        public EasyGameTwo(Configuration config) {
            super(config, 6, 6);
            Grid grid = getGrid();
            initNexus();
            initInfo();

            grid.place(0, 4, new Receptor());
            grid.place(0, 3, new VerticalTrack());
            grid.place(0, 0, new Receptor());
            grid.place(1, 0, new HorizontalTrack());

            grid.place(1, 4, new HorizontalTrack());
            grid.place(2, 4, new Receptor());
            grid.place(2, 3, new VerticalTrack());

            grid.place(0, 2, new VerticalTrack());
            grid.place(0, 1, new VerticalTrack());

            grid.place(1, 2, new HorizontalTrack());
            grid.place(2, 1, new VerticalTrack());
            grid.place(2, 2, new VerticalTrack());


            grid.place(3, 0, new HorizontalTrack());
            grid.place(3, 2, new HorizontalTrack());
            grid.place(4, 4, new Receptor());
            grid.place(3, 4, new HorizontalTrack());
            grid.place(4, 3, new VerticalTrack());
            grid.place(4, 2, new VerticalTrack());
            grid.place(4, 1, new VerticalTrack());
            grid.place(4, 0, new Receptor());
            grid.place(2, 0, new Receptor());
        }

        /**
         * Return the {@link Level} this game represents.
         *
         * @return The level of this game.
         */
        public Level getLevel() {
            return EasyLevelTwo.this;
        }
    }
}
