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
import nl.tudelft.broccoli.core.Marble;

import java.util.HashSet;
import java.util.Set;

/**
 * An in-game {@link Entity} that can be placed on a tile.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class Tileable implements Entity {
    /**
     * The message printed when a tileable entity is not placed on a grid.
     */
    private static final String TILEABLE_UNPLACED = "The entity is not placed on a tile";

    /**
     * The {@link Tile} this entity is placed on.
     */
    private Tile tile;

    /**
     * The {@link TileableListener}s of this tileable entity.
     */
    private Set<TileableListener> listeners = new HashSet<>();

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
    public abstract boolean allowsConnection(Direction direction);

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
    public abstract boolean accepts(Direction direction, Marble marble);

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
    public abstract void accept(Direction direction, Marble marble);

    /**
     * Determine whether this entity is connected with the neighbour at the given direction. This
     * means both entities allow a connection at that point.
     *
     * @param direction The direction of the neighbour relative to this entity.
     * @return <code>true</code> if a ball is able to travel to that direction, <code>false</code>
     *         otherwise.
     */
    public boolean isConnected(Direction direction) {
        if (tile == null) {
            throw new IllegalStateException(TILEABLE_UNPLACED);
        } else if (!allowsConnection(direction)) {
            return false;
        }

        Tile neighbour = tile.get(direction);
        return neighbour != null && neighbour.getTileable().allowsConnection(direction.inverse());
    }

    /**
     * Determine whether the neighbour at the given direction accepts a ball onto its tile at the
     * moment of execution.
     *
     * @param direction The direction of the neighbour relative to this entity.
     * @param marble The {@link Marble} to be released.
     * @return <code>true</code> if the tileable entity accepts the ball onto its tile,
     *         <code>false</code> otherwise.
     */
    public boolean isReleasable(Direction direction, Marble marble) {
        if (tile == null) {
            throw new IllegalStateException(TILEABLE_UNPLACED);
        }

        Tile neighbour = tile.get(direction);
        return neighbour != null && neighbour.getTileable().accepts(direction.inverse(), marble);
    }

    /**
     * Release a {@link Marble} onto a neighbour tile.
     *
     * <p>Be aware that the direction parameter is seen from the origin of this {@link Tileable}
     * meaning the direction may need to be inverted.</p>
     *
     * @param direction The direction of the neighbour to which the marble should be send.
     * @param marble The marble that wants to be accepted onto the tile of a neighbour.
     */
    public void release(Direction direction, Marble marble) {
        if (tile == null) {
            throw new IllegalStateException(TILEABLE_UNPLACED);
        }

        Tile neighbour = tile.get(direction);

        if (neighbour != null) {
            informRelease(direction, marble);
            neighbour.getTileable().accept(direction.inverse(), marble);
        }
    }

    /**
     * Return the {@link Tile} this entity is placed on.
     *
     * @return The tile this entity is placed on or <code>null</code> if this entity does not belong
     *         to a grid.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Set the {@link Tile} the entity is placed on.
     *
     * @param tile The tile to place the entity on.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Return the {@link TileableListener}s of this {@link Tileable}.
     *
     * @return A set of listeners of this instance.
     */
    public Set<TileableListener> getListeners() {
        return listeners;
    }

    /**
     * Add a {@link TileableListener} to this {@link Tileable}.
     *
     * @param listener The listener to add.
     * @return <code>true</code> if the listener collection has changed, <code>false</code>
     *         otherwise.
     */
    public boolean addListener(TileableListener listener) {
        return listeners.add(listener);
    }

    /**
     * Remove a {@link TileableListener} from this {@link Tileable}.
     *
     * @param listener The listener to remove.
     * @return <code>true</code> if the listener collection has changed, <code>false</code>
     *         otherwise.
     */
    public boolean removeListener(TileableListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Determine whether the {@link Tileable} is placed on a grid.
     *
     * @return <code>true</code> if the tileable is placed on a grid, <code>false</code> otherwise.
     */
    public boolean onGrid() {
        return tile != null;
    }

    /**
     * Inform the listeners of this {@link Tileable} that it has accepted a marble.
     *
     * @param direction The direction from which the marble has been accepted.
     * @param marble The marble the tileable has accepted.
     */
    protected void informAcceptation(Direction direction, Marble marble) {
        for (TileableListener listener : listeners) {
            listener.ballAccepted(this, direction, marble);
        }
    }

    /**
     * Inform the listeners of this {@link Tileable} that it has disposed a marble.
     *
     * @param direction The direction from which the marble has been disposed.
     * @param marble The marble the tileable has disposed.
     */
    protected void informDispose(Direction direction, Marble marble) {
        for (TileableListener listener : listeners) {
            listener.ballDisposed(this, direction, marble);
        }
    }

    /**
     * Inform the listeners of this {@link Tileable} that it has released a marble.
     *
     * @param direction The direction from which the marble has been released.
     * @param marble The marble the tileable has released.
     */
    protected void informRelease(Direction direction, Marble marble) {
        for (TileableListener listener : listeners) {
            listener.ballReleased(this, direction, marble);
        }
    }
}
