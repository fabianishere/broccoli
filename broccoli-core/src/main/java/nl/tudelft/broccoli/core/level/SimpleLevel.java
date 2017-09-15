package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;

import java.util.Random;

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
            grid = new Grid(4, 4);

            NexusContext context = new NexusContext();
            grid.place(0, 3, new Nexus(context));
            grid.place(1, 3, new Nexus(context));
            grid.place(2, 3, new Nexus(context));
            grid.place(3, 3, new SpawningNexus(context, new Random(), Direction.RIGHT));

            TimerTile timer = new TimerTile(300);
            grid.place(3, 2, timer);

            Receptor receptorA = new Receptor();
            receptorA.getSlot(Direction.TOP).accept(Ball.of(Ball.Type.BLUE));
            grid.place(0, 2, receptorA);
            grid.place(0, 1, new VerticalTrack());

            Receptor receptorB = new Receptor();
            receptorB.getSlot(Direction.TOP).accept(Ball.of(Ball.Type.BLUE));

            grid.place(1, 2, new HorizontalTrack());
            grid.place(2, 2, receptorB);
            grid.place(2, 1, new VerticalTrack());

            Receptor receptorC = new Receptor();
            receptorC.getSlot(Direction.TOP).accept(Ball.of(Ball.Type.BLUE));

            grid.place(0, 0, receptorC);
            grid.place(1, 0, new HorizontalTrack());

            Receptor receptorD = new Receptor();
            receptorD.getSlot(Direction.TOP).accept(Ball.of(Ball.Type.BLUE));

            grid.place(2, 0, receptorD);
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
         * Determine whether the game has been won.
         *
         * @return <code>true</code> if all receptors have been marked, <code>false</code> otherwise.
         */
        @Override
        public boolean isWon() {
            // TODO We don't want to check every tile on the grid
            for (int j = 0; j < grid.getHeight(); j++) {
                for (int i = 0; i < grid.getWidth(); i++) {
                    Tile tile = grid.get(i, j);

                    if (tile.getTileable() instanceof Receptor) {
                        Receptor receptor = (Receptor) tile.getTileable();
                        if (!receptor.isMarked()) {
                            return false;
                        }
                    }
                }
            }

            return true;
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
