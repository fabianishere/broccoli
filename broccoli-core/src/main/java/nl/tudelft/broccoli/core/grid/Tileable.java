package nl.tudelft.broccoli.core.grid;

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.Entity;

public abstract class Tileable implements Entity {
    private Tile tile;

    public abstract boolean accepts(Direction direction);
    public abstract void accept(Direction direction, Ball ball);

    public Tile getTile() {
        return tile;
    }

    public void place(Tile tile) {
        this.tile = tile;
    }
}
