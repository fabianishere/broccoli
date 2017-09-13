package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;

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
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public GameSession create() {
        return new SimpleGame();
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
         * The grid of this game.
         */
        private Grid grid;

        /**
         * Construct a {@link SimpleGame} instance.
         */
        public SimpleGame() {
            grid = new Grid(4, 2);

            grid.place(0, 1, new Receptor());
            grid.place(1, 1, new HorizontalTrack());
            grid.place(2, 1, new Receptor());
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
