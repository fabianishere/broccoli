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
import nl.tudelft.broccoli.core.grid.TileableListener;

/**
 * This type of {@link Track} only able to pass one type of {@link Marble}.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class FilterTrack extends Track implements TileableListener {

    /**
     * The only {@link Marble.Type} that is able to pass.
     */
    private Marble.Type marbleType;

    /**
     * The {@link Track} that is transformed to be a filtering track.
     */
    private Track track;

    /**
     * Constructs the {@link FilterTrack}.
     *
     * @param track The {@link Track} that is transformed to be a filtering track.
     * @param marbleType The only {@link Marble.Type} that is able to pass.
     */
    public FilterTrack(Track track, Marble.Type marbleType) {
        this.track = track;
        this.track.addListener(this);
        this.marbleType = marbleType;
    }

    /**
     * Returns the {@link Track}.
     *
     * @return The {@link Track}.
     */
    public Track getTrack() {
        return track;
    }

    /**
     * Return the {@link Marble.Type} that is able to pass.
     *
     * @return The {@link Marble.Type} that is able to pass
     */
    public Marble.Type getMarbleType() {
        return marbleType;
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
        return track.allowsConnection(direction);
    }

    /**
     * Determine whether this tileable entity accepts a ball onto its tile at the moment of
     * execution.
     *
     * <p>If {@link Tileable#allowsConnection(Direction)} returns <code>false</code> for any given
     * direction, this method should not be invoked for that direction.
     * </p>
     *
     * <p>Be aware that the direction parameter is seen from the origin of this {@link Tileable}
     * meaning the direction may need to be inverted.</p>
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @param marble The {@link Marble} to be accepted.
     * @return <code>true</code> if the tileable entity accepts the ball onto its tile,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean accepts(Direction direction, Marble marble) {
        return acceptsMarbleType(marble.getType()) && track.accepts(direction, marble);
    }

    /**
     * Checks if a certain {@link Marble.Type} is allowed to enter the {@link Track}.
     *
     * @param marbleType The {@link Marble.Type} that is being checked for passage.
     * @return <code>true</code> if allowed, <code>false</code> otherwise.
     */
    private boolean acceptsMarbleType(Marble.Type marbleType) {
        return this.marbleType.equals(marbleType);
    }

    /**
     * Accept a {@link Marble} onto the tile of this tileable entity.
     *
     * <p>Be aware that the direction parameter is seen from the origin of this {@link Tileable}
     * meaning the direction may need to be inverted.</p>
     *
     * @param direction The direction from which a marble wants to be accepted onto this tileable
     *                  entity.
     * @param marble The marble that wants to be accepted onto the tile of this tileable entity.
     */
    @Override
    public void accept(Direction direction, Marble marble) {
        track.accept(direction, marble);
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
     * This method is invoked when a {@link Tileable} has accepted a marble.
     *
     * @param tileable  The tileable that has accepted the marble.
     * @param direction The direction from which the marble was accepted.
     * @param marble    The marble that has been accepted.
     */
    @Override
    public void ballAccepted(Tileable tileable, Direction direction, Marble marble) {
        informAcceptation(direction, marble);
    }

    /**
     * This method is invoked when a {@link Tileable} has disposed a marble.
     *
     * @param tileable  The tileable that has disposed the marble.
     * @param direction The direction from which the marble was disposed.
     * @param marble    The marble that has been disposed.
     */
    @Override
    public void ballDisposed(Tileable tileable, Direction direction, Marble marble) {
        informDispose(direction, marble);
    }

    /**
     * This method is invoked when a {@link Tileable} has released a marble to a neighbour.
     *
     * @param tileable  The tileable that has released the marble.
     * @param direction The direction from which the marble was released.
     * @param marble    The marble that has been released.
     */
    @Override
    public void ballReleased(Tileable tileable, Direction direction, Marble marble) {
        informRelease(direction, marble);
    }
}
