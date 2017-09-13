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

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.Entity;

/**
 * An in-game {@link Entity} that can be placed on a tile.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class Tileable implements Entity {
    /**
     * The {@link Tile} this entity is placed on.
     */
    Tile tile;

    /**
     * Determine whether this tileable entity accepts a ball onto its tile.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @return <code>true</code> if the tileable entity accepts the ball onto its tile,
     *         <code>false</code> otherwise.
     */
    public abstract boolean accepts(Direction direction);

    /**
     * Accept a {@link Ball} onto the tile of this tileable entity.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @param ball The ball that wants to be accepted onto the tile of this tileable entity.
     */
    public abstract void accept(Direction direction, Ball ball);

    /**
     * Return the {@link Tile} this entity is placed on.
     *
     * @return The tile this entity is placed on or <code>null</code> if this entity does not belong
     *         to a grid.
     */
    public Tile getTile() {
        return tile;
    }
}
