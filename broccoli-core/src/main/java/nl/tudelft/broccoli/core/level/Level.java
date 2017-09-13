package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.Rail;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.receptor.Receptor;

import java.util.logging.FileHandler;

public class Level {
    private int level;
    private Grid grid;
    //private Nexus nexus;

    public Level(int level) {
        // Level should be loaded using this number. For now I hardcoded a level.
        this.level = level;

        grid = new Grid(3, 2);

        Receptor receptor0 = new Receptor();
        Receptor receptor1 = new Receptor();

        Rail rail0 = new Rail();

        grid.place(receptor0, 0, 1);
        grid.place(rail0, 1, 1);
        grid.place(receptor1, 2, 1);
    }
}
