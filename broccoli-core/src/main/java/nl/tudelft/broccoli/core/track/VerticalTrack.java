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

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tile;

/**
 * A vertical {@link Track} on the {@link Grid}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class VerticalTrack extends Track {
    /**
     * Determine whether this rail is connected at both endpoints.
     *
     * @return <code>true</code> if both endpoints are connected to a port, <code>false</code>
     *         otherwise.
     */
    public boolean isConnected() {
        Tile tile = getTile();
        if (tile == null) {
            throw new IllegalStateException("The track is not placed on a grid");
        }

        Tile left = tile.get(Direction.TOP);
        Tile right = tile.get(Direction.BOTTOM);

        return left != null && right != null;
    }

    /**
     * Determine whether this tileable entity accepts a ball onto its tile.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @return <code>true</code> if the tileable entity accepts the ball onto its tile,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean accepts(Direction direction) {
        switch (direction) {
            case TOP:
            case BOTTOM:
                return true;
            default:
                return false;
        }
    }

    /**
     * Accept a {@link Ball} onto the tile of this tileable entity.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @param ball      The ball that wants to be accepted onto the tile of this tileable entity.
     */
    @Override
    public void accept(Direction direction, Ball ball) {
        float progress = -0.f;
        switch (direction) {
            case TOP:
                progress = 0.f;
            case BOTTOM:
                this.progress.putIfAbsent(ball, progress);
                break;
            default:
                throw new IllegalArgumentException("The track does not accept balls from the given "
                    + "direction");
        }
    }
}
