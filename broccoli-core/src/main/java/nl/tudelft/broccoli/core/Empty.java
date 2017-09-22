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

package nl.tudelft.broccoli.core;

import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;

/**
 * An empty {@link Tileable} which does not accepts {@link Marble}s from any direction.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Empty extends Tileable {
    /**
     * Determine whether this tileable entity allows a connection at the given direction with the
     * entity next to this entity in the given direction.
     *
     * <p>This means the entity is able to have a {@link Marble} travel from the given direction onto
     * the tile.</p>
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
        return false;
    }

    /**
     * Determine whether this tileable entity accepts a ball onto its tile.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @return <code>true</code> if the tileable entity accepts the ball onto its tile,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean accepts(Direction direction) {
        return false;
    }

    /**
     * Accept a {@link Marble} onto the tile of this tileable entity.
     *
     * @param direction The direction from which a marble wants to be accepted onto this tileable
     *                  entity.
     * @param marble The marble that wants to be accepted onto the tile of this tileable entity.
     */
    @Override
    public void accept(Direction direction, Marble marble) {
        throw new IllegalArgumentException("An empty entity does not accept from any direction");
    }
}
