package nl.tudelft.broccoli.core.level.medium;

import nl.tudelft.broccoli.core.Teleporter;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.level.*;
import nl.tudelft.broccoli.core.level.easy.AbstractEasyLevel;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;

public class MediumLevelThree extends AbstractMediumLevel {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    @Override
    public GameSession create(Configuration config) {
        return new MediumLevelThree.MediumGameThree(config);
    }

    /**
     * Return the index of this level.
     *
     * @return The index of this level.
     */
    @Override
    public int getIndex() {
        return 3;
    }

    /**
     * A very basic, static {@link GameSession} created by {@link AbstractEasyLevel} instances.
     */
    private class MediumGameThree extends AbstractGameSession {

        /**
         * Construct a {@link MediumGameThree} instance.
         *
         * @param config The game configuration to use.
         */
        public MediumGameThree(Configuration config) {
            super(config, 6, 6);

            Teleporter teleporter1 = new Teleporter(new VerticalTrack());
            Teleporter teleporter2 = new Teleporter(new VerticalTrack());
            Teleporter teleporter3 = new Teleporter(new VerticalTrack());

            Teleporter receiver1 = new Teleporter(new VerticalTrack());
            Teleporter receiver2 = new Teleporter(new VerticalTrack());
            Teleporter receiver3 = new Teleporter(new VerticalTrack());

            teleporter1.setDestination(receiver1);
            teleporter2.setDestination(receiver2);
            teleporter3.setDestination(receiver3);

            receiver1.setDestination(teleporter1);
            receiver2.setDestination(teleporter2);
            receiver3.setDestination(teleporter3);

            Grid grid = getGrid();

            initNexus();
            initInfo();

            grid.place(0, 4, new Receptor());
            grid.place(0, 3, teleporter3);

            grid.place(1, 4, new HorizontalTrack());
            grid.place(2, 4, new Receptor());
            grid.place(2, 3, teleporter2);

            grid.place(0, 1, receiver2);

            grid.place(2, 1, receiver1);


            grid.place(4, 4, new Receptor());
            grid.place(3, 4, new HorizontalTrack());
            grid.place(4, 3, teleporter1);
            grid.place(4, 1, receiver3);
            grid.place(4, 0, new Receptor());
            grid.place(2, 0, new Receptor());


            grid.place(0, 0, new Receptor());
        }

        /**
         * Return the {@link Level} this game represents.
         *
         * @return The level of this game.
         */
        public Level getLevel() {
            return MediumLevelThree.this;
        }
    }
}

