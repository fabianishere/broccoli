package nl.tudelft.broccoli.core.grid;

public class Tile {
    private Tileable tileable;
    private int x;
    private int y;
    private Grid grid;

    public Tile(int x, int y, Grid grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.tileable = new Empty();
    }

    public Tileable getTileable() {
        return tileable;
    }

    public void setTileable(Tileable tileable) {
        this.tileable = tileable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Grid getGrid() {
        return grid;
    }

    public Tile get(Direction direction) {
        switch (direction) {
            case TOP:
                return grid.getTile(x, y + 1);
            case RIGHT:
                return grid.getTile(x + 1, y);
            case BOTTEM:
                return grid.getTile(x, y - 1);
            case LEFT:
                return grid.getTile(x - 1, y);
        }
        return null;
    }
}
