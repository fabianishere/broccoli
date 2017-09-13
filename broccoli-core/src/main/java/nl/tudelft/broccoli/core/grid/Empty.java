package nl.tudelft.broccoli.core.grid;

import nl.tudelft.broccoli.core.Ball;

public class Empty extends Tileable {

    public Empty() {}

    @Override
    public boolean accepts(Direction direction) {
        return false;
    }

    @Override
    public void accept(Direction direction, Ball ball) {
        throw new IllegalArgumentException("An empty tile cannot accept any marbles");
    }
}
