package nl.tudelft.broccoli.core.grid;

public class Grid {
    private Tile[][] tiles;
    private int width;
    private int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(i, j, this);
            }
        }
    }

    public void place(Tileable tileable, int x, int y) {
        tiles[x][y].setTileable(tileable);
        tileable.place(tiles[x][y]);
    }

    public Tile getTile(int x, int y) {
        if (onGrid(x, y)) {
            return tiles[x][y];
        }
        return null;
    }

    public boolean onGrid(int x, int y) {
        return x >= 0 && y >= 0 && width >= x && height >= y;
    }
}
