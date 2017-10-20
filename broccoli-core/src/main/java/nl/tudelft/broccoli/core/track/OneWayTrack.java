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
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.core.grid.Tileable;

/**
 * A track that will only allow a {@link Marble} to travel in one way.
 * Uses an implementation of {@link Track} to get it's behaviour.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class OneWayTrack extends Track {
    /**
     * The direction from which a marble is allowed to enter.
     */
    private Direction direction;

    /**
     * The {@link Track} that is transformed to be a filtering track.
     */
    private Track track;

    /**
     * Construct a {@link OneWayTrack} instance.
     *
     * @param track The track to decorate.
     * @param direction The direction from which balls are allowed to enter the track.
     */
    public OneWayTrack(Track track, Direction direction) {
        if (!track.allowsConnection(direction)) {
            throw new IllegalArgumentException("Directions do not agree with track");
        }
        this.track = track;
        this.direction = direction;
    }

    /**
     * Return the track this one-way track inherits it's behaviour from.
     *
     * @return The track parameter.
     */
    public Track getTrack() {
        return this.track;
    }

    /**
     * Returns the direction this one-way track will output the marble to.
     *
     * @return The direction marbles will be released to.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Set the {@link Tile} the entity is placed on.
     *
     * @param tile The tile to place the entity on.
     */
    @Override
    public void setTile(Tile tile) {
        super.setTile(tile);
        track.setTile(tile);
    }

    /**
     * Determine whether this rail is connected at both endpoints.
     *
     * @return <code>true</code> if both endpoints are connected to a port, <code>false</code>
     *         otherwise.
     */
    @Override
    public boolean isConnected() {
        return track.isConnected();
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
        return this.track.allowsConnection(direction);
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
        return this.track.accepts(direction, marble);
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
        if (!accepts(direction, marble)) {
            throw new IllegalArgumentException("The track does not accept balls from the given "
                + "direction");
        }
        informAcceptation(direction, marble);
    }

    /**
     * Determine whether a certain {@link Marble} is allowed to pass the middle
     * of the {@link Track}.
     *
     * @param direction The {@link Direction} the {@link Marble} i coming from.
     * @param marble The {@link Marble} that is passing by.
     * @return <code>true</code> if allowed to pass the middle. <code>false</code> otherwise.
     */
    @Override
    public boolean passesMidpoint(Direction direction, Marble marble) {
        return direction.equals(getDirection());
    }
}
