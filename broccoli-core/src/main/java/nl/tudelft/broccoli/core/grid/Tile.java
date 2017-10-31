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

import nl.tudelft.broccoli.core.Empty;
import nl.tudelft.broccoli.core.Entity;

/**
 * A tile of a {@link Grid}.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class Tile implements Entity {
    /**
     * The entity that is placed on the grid.
     */
    Tileable tileable;

    /**
     * Construct a {@link Tile} instance.
     */
    public Tile() {
        this.tileable = new Empty();
        this.tileable.setTile(this);
    }

    /**
     * Return the tileable {@link Entity} which is placed on this {@link Tile}.
     *
     * @return The entity that is placed on this tile or {@link Empty} if the tile is empty.
     */
    public Tileable getTileable() {
        return tileable;
    }

    /**
     * Determine whether the tile is currently occupied.
     *
     * @return <code>true</code> if an entity is currently placed on this tile, <code>false</code>
     *         otherwise.
     */
    public boolean isOccupied() {
        return !(tileable instanceof Empty);
    }

    /**
     * Return the {@link Tile} that is directly connected to this {@link Tile} in the given
     * {@link Direction}.
     *
     * @param direction The direction to get the {@link Tile} of.
     * @return The tile that is directly connected to this tile in the given direction.
     * @throws IllegalArgumentException if the specified {@link Direction} is <code>null</code>.
     */
    public Tile get(Direction direction) {
        Grid grid = getGrid();
        int x = getX();
        int y = getY();

        if (direction == null) {
            throw new IllegalArgumentException();
        }

        switch (direction) {
            case TOP:
                return grid.get(x, y + 1);
            case RIGHT:
                return grid.get(x + 1, y);
            case BOTTOM:
                return grid.get(x, y - 1);
            case LEFT:
                return grid.get(x - 1, y);
            default:
                throw new IllegalArgumentException("The specified direction is not supported");
        }
    }

    /**
     * The x-coordinate of this tile on the grid.
     *
     * @return The x-coordinate of this tile on the grid as integer between [0, width - 1].
     */
    public abstract int getX();

    /**
     * The y-coordinate of this tile on the grid.
     *
     * @return The y-coordinate of this tile on the grid as integer between [0, height - 1].
     */
    public abstract int getY();

    /**
     * Return the {@link Grid} this {@link Tile} is part of.
     *
     * @return The non-null grid this tile is part of.
     */
    public abstract Grid getGrid();
}
