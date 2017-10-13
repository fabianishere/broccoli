/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Fabian Mastenbroek, Christian Slothouber,
 * Earth Grob, Matthijs Rijm, Bas Musters
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nl.tudelft.broccoli.core.grid;

import nl.tudelft.broccoli.core.Entity;
import nl.tudelft.broccoli.core.config.IntegerProperty;
import nl.tudelft.broccoli.core.config.Property;

/**
 * A grid system on which the entities of the game are placed.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Grid implements Entity {
    /**
     * A configuration property for the width of the grid.
     */
    public static final Property<Integer> WIDTH = new IntegerProperty("grid.width", 4);

    /**
     * A configuration property for the height of the grid.
     */
    public static final Property<Integer> HEIGHT = new IntegerProperty("grid.height", 4);

    /**
     * The tiles of the grid.
     */
    private final Tile[][] tiles;

    /**
     * The width of the grid.
     */
    private final int width;

    /**
     * The height of the grid.
     */
    private final int height;

    /**
     * Create a {@link Grid} instance of the given size.
     *
     * @param width The width of the grid.
     * @param height The height of the grid.
     */
    public Grid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("The given coordinates are not valid");
        }
        this.width = width;
        this.height = height;

        tiles = new Tile[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                tiles[j][i] = new InternalTile(i, j);
            }
        }
    }

    /**
     * Return the amount of tiles in the x-direction.
     *
     * @return The width of the board.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the amount of tiles in the y-direction.
     *
     * @return The height of the board.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Place the given tileable {@link Entity} at the given position on the grid.
     *
     * @param x The x-coordinate to place the entity at.
     * @param y The y-coordinate to place the entity at.
     * @param tileable The tileable entity to place at these coordinates.
     * @throws IllegalArgumentException if the coordinates given do not exist on the grid.
     */
    public void place(int x, int y, Tileable tileable) {
        if (!onGrid(x, y)) {
            throw new IllegalArgumentException("The given coordinates do not exist on this grid");
        }
        Tile tile = tiles[y][x];
        tile.tileable = tileable;
        tileable.setTile(tile);
    }

    /**
     * Return the {@link Tile} at the given coordinates.
     *
     * @param x The x-coordinate of the tile to get.
     * @param y The y-coordinate of the tile to get.
     * @return The tile at the given coordinates or <code>null</code> if the coordinates are not on
     *         the grid.
     */
    public Tile get(int x, int y) {
        if (onGrid(x, y)) {
            return tiles[y][x];
        }
        return null;
    }

    /**
     * Determine whether the given coordinates are on the grid.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return <code>true</code> if the point exists on the grid, <code>false</code> otherwise.
     */
    public boolean onGrid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * An internal {@link Tile} implementation to represent the tiles of this grid.
     */
    private class InternalTile extends Tile {
        /**
         * The x-coordinate of the position of the tile within the grid.
         */
        private final int x;

        /**
         * The y-coordinate of the position of the tile within the grid.
         */
        private final int y;

        /**
         * Construct a {@link InternalTile}.
         *
         * @param x The x-coordinate of the position of this tile.
         * @param y The y-coordinate of the position of this tile.
         */
        InternalTile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * The x-coordinate of this tile on the grid.
         *
         * @return The x-coordinate of this tile on the grid as integer between [0, width - 1].
         */
        public int getX() {
            return x;
        }

        /**
         * The y-coordinate of this tile on the grid.
         *
         * @return The y-coordinate of this tile on the grid as integer between [0, height - 1].
         */
        public int getY() {
            return y;
        }

        /**
         * Return the {@link Grid} this {@link Tile} is part of.
         *
         * @return The non-null grid this tile is part of.
         */
        public Grid getGrid() {
            return Grid.this;
        }
    }
}
