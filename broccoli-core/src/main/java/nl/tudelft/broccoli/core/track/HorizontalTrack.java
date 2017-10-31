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

package nl.tudelft.broccoli.core.track;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.core.grid.Tileable;

/**
 * A horizontal {@link Track} on the {@link Grid}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class HorizontalTrack extends Track {
    /**
     * Determine whether this horizontal track is connected at both endpoints.
     *
     * @return <code>true</code> if both horizontal endpoints are connected to a port,
     *         <code>false</code> otherwise.
     */
    public boolean isConnected() {
        Tile tile = getTile();
        if (tile == null) {
            throw new IllegalStateException("The track is not placed on a grid");
        }

        Tile left = tile.get(Direction.LEFT);
        Tile right = tile.get(Direction.RIGHT);

        return left != null && right != null;
    }

    /**
     * Determine whether a certain {@link MarbleType} is allowed to pass the middle
     * of the {@link Track}.
     *
     * @param direction The {@link Direction} the {@link Marble} i coming from.
     * @param marble The {@link Marble} that is passing by.
     * @return <code>true</code> if allowed to pass the middle. <code>false</code> otherwise.
     */
    @Override
    public boolean passesMidpoint(Direction direction, Marble marble) {
        return true;
    }

    /**
     * Determine whether this tileable entity has a connection at the given direction with the
     * entity next to this entity in the given direction.
     *
     * <p>This means the entity is able to have a {@link Marble} travel from the given direction
     * onto the tile.</p>
     *
     * <p>Be aware that the direction parameter is seen from the origin of this {@link Tileable}
     * meaning the direction may need to be inverted.</p>
     *
     * @param direction The direction from the origin of the tile to a possible port of the entity.
     * @return <code>true</code> if a ball is able to travel from that direction, <code>false</code>
     *         otherwise.
     */
    @Override
    public boolean allowsConnection(Direction direction) {
        switch (direction) {
            case LEFT:
            case RIGHT:
                return true;
            default:
                return false;
        }
    }

    /**
     * Determine whether this tileable entity accepts a ball onto its tile at the time of the
     * execution.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @return <code>true</code> if the tileable entity accepts the ball onto its tile,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean accepts(Direction direction, Marble marble) {
        return allowsConnection(direction);
    }

    /**
     * Accept a {@link Marble} onto the tile of this tileable entity.
     *
     * @param direction The direction from which a marble wants to be accepted onto this tileable
     *                  entity.
     * @param marble      The marble that wants to be accepted onto the tile of this tileable
     *                    entity.
     */
    @Override
    public void accept(Direction direction, Marble marble) {
        switch (direction) {
            case LEFT:
            case RIGHT:
                informAcceptation(direction, marble);
                break;
            default:
                throw new IllegalArgumentException("The track does not accept balls from the given "
                    + "direction");
        }
    }
}
