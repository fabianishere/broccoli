package nl.tudelft.broccoli.core;

import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;

public class TimerTile extends Tileable {
    private int maxTime;

    public TimerTile(int time) {
        maxTime = time;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getTextureId() {
        return 4;
    }

    @Override
    public boolean allowsConnection(Direction direction) {
        return false;
    }

    @Override
    public boolean accepts(Direction direction) {
        return false;
    }

    @Override
    public void accept(Direction direction, Ball ball) {
        throw new IllegalArgumentException("An empty entity does not accept from any direction");
    }
}
