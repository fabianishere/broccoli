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

package nl.tudelft.broccoli.core.receptor;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.Track;

/**
 * An object with four {@link Slot}s (R1.2a) which accepts {@link Marble}s in open slots (R1.2b) via
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Receptor extends Tileable {
    /**
     * The slots of this receptor.
     */
    private Slot[] slots = {
        new Slot(Direction.TOP),
        new Slot(Direction.RIGHT),
        new Slot(Direction.BOTTOM),
        new Slot(Direction.LEFT),
    };

    /**
     * An array containing the possible directions for a faster translation.
     */
    private Direction[] directions = Direction.values();

    /**
     * The clockwise rotation of the slots on the receptor.
     */
    private int rotation = 0;

    /**
     * A flag to mark the receptor, when it has been completely filled with balls of the same colour
     * at least once.
     */
    private boolean marked = false;

    /**
     * A flag to mark the receptor locked, so that it does not accept balls anymore.
     */
    private boolean locked = false;

    /**
     * The points multiplier when a receptor is marked.
     */
    private int multiplier = 100;

    /**
     * Rotate the slots of the receptor clockwise by <code>n * 1/4 pi radians</code>.
     *
     * @param turns The amount of turns to make, where one turns equals <code>45 degrees</code> or
     *              <code>1/4 pi radians</code>. A negative amount will rotate the receptor
     *              counterclockwise.
     */
    public void rotate(int turns) {
        rotation = (rotation + turns) % slots.length;
    }

    /**
     * Return the current rotation of the receptor represented as an integer in [1, 3].
     *
     * @return The current rotation of the receptor represented as an integer in [1, 3] where the
     *         rotation is clockwise.
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Return one of the four {@link Slot}s of this receptor, with the given local orientation,
     * relative to this receptor.
     *
     * @param direction The local orientation of the port relative to the receptor
     * @return A non-null port with the specified local orientation.
     */
    public Slot getSlot(Direction direction) {
        // This method assumes the Direction enumeration is defined in a clockwise order
        return slots[Math.floorMod(direction.ordinal() - rotation, directions.length)];
    }

    /**
     * Determine whether the receptor has been marked. This means the receptor has been completely
     * filled with balls of the same colour at least once.
     *
     * @return <code>true</code> if the receptor has been marked, <code>false</code> otherwise.
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * Determine whether the receptor should be marked. This means the receptor has been completely
     * filled with balls of the same colour at least once. (R1.2e)
     *
     * @return <code>true</code> if the receptor should be been marked, <code>false</code>
     *         otherwise.
     */
    private boolean shouldMark() {
        Slot slot = slots[0];

        for (int i = 1; i < slots.length; i++) {
            if (!slot.isOccupied() || !slot.getMarble().isCompatible(slots[i].getMarble())) {
                return false;
            }
            slot = slots[i];
        }

        return true;
    }

    /**
     * Mark the receptor. This means the receptor has been completely filled with balls of the same
     * colour at least once. (R1.2e)
     */
    private void mark() {
        marked = true;

        // Add the scored points to the progress of the game.
        getTile().getGrid().getSession().getProgress().score(multiplier);

        for (Slot slot : slots) {
            slot.dispose();
        }

        informMarked();
    }

    /**
     * Determine whether this {@link Receptor} is locked and does not accept balls anymore until it
     * has been unlocked.
     *
     * @return <code>true</code> if the receptor is locked, <code>false</code> otherwise.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Lock this {@link Receptor} in order to disallow balls from entering and leaving this
     * receptor.
     */
    public void lock() {
        this.locked = true;
    }

    /**
     * Unlock this {@link Receptor} in order to allow balls again to enter and leave this receptor.
     */
    public void unlock() {
        this.locked = false;
    }

    /**
     * Internal {@link Slot} implementation of the {@link Receptor} class.
     */
    public class Slot {
        /**
         * The initial direction of this slot.
         */
        private Direction direction;

        /**
         * The marble stored in this slot.
         */
        private Marble marble;

        /**
         * Construct an {@link Slot} instance.
         *
         * @param direction The initial direction of the slot.
         */
        Slot(Direction direction) {
            this.direction = direction;
        }

        /**
         * Return the {@link Marble} that is stored in this slot.
         *
         * @return The marble that is stored in this slot or <code>null</code> if the slot is
         *         unoccupied.
         */
        public Marble getMarble() {
            return marble;
        }

        /**
         * Return the {@link Receptor} of this slot.
         *
         * @return The receptor of this slot.
         */
        public Receptor getReceptor() {
            return Receptor.this;
        }

        /**
         * Accept a marble into this slot if the slot is not already occupied.
         *
         * @param marble The marble to accept in this slot.
         * @throws IllegalStateException if the slot is already occupied.
         */
        public void accept(Marble marble) {
            if (isOccupied()) {
                throw new IllegalStateException("The slot is already occupied");
            } else if (isLocked()) {
                throw new IllegalStateException("The receptor is locked");
            }

            this.marble = marble;
            informAcceptation(getDirection(), marble);

            // Mark the receptor if all balls are of the same color (R1.2e)
            if (shouldMark()) {
                mark();
            }
        }

        /**
         * Release the current marble in the slot to the {@link Track} the port of this slot is
         * connected to.
         *
         * @throws IllegalStateException if the slot is currently unoccupied or the port of this
         *                               slot is not connected to a rail.
         */
        public void release() {
            if (!isOccupied()) {
                throw new IllegalStateException("The slot is not occupied");
            } else if (!onGrid()) {
                throw new IllegalStateException("The receptor is not placed on a grid");
            } else if (isLocked()) {
                throw new IllegalStateException("The receptor is locked");
            }

            Direction direction = getDirection();

            if (!isConnected(direction) || !isReleasable(direction, marble)) {
                throw new IllegalStateException("The slot cannot release the marble "
                        + "to its neighbor");
            }

            Receptor.this.release(direction, marble);
            marble = null;
        }

        /**
         * Dispose the marble in the slot from the environment.
         *
         * @throws IllegalStateException if the slot is currently unoccupied.
         */
        public void dispose() {
            informDispose(getDirection(), marble);
            marble = null;
        }

        /**
         * Return the current direction of the {@link Slot} on the {@link Receptor}.
         *
         * @return The direction of the slot.
         */
        public Direction getDirection() {
            return direction.rotate(rotation);
        }

        /**
         * Determine whether the slot is occupied with a {@link Marble}.
         *
         * @return <code>true</code> if the slot is occupied with a ball, <code>false</code> otherwise.
         */
        public boolean isOccupied() {
            return getMarble() != null;
        }
    }

    /**
     * Determine whether the neighbour at the given direction accepts a marble onto its tile at the
     * moment of execution.
     *
     * @param direction The direction of the neighbour relative to this entity.
     * @return <code>true</code> if the tileable entity accepts the marble onto its tile,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean isReleasable(Direction direction, Marble marble) {
        return !isLocked() && super.isReleasable(direction, marble);
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
     * @return <code>true</code> if a marble is able to travel from that direction,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean allowsConnection(Direction direction) {
        return true;
    }

    /**
     * Determine whether this tileable entity accepts a marble onto its tile.
     *
     * <p>This class will only accept balls if the slot at that direction is not occupied yet.
     *
     * @param direction The direction from which a marble wants to be accepted onto this tileable
     *                  entity.
     * @return <code>true</code> if the tileable entity accepts the marble onto its tile,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean accepts(Direction direction, Marble marble) {
        return !locked && !getSlot(direction).isOccupied();
    }

    /**
     * Accept the given {@link Marble} at the given direction.
     *
     * <p>This class will only accept balls if the slot at that direction is not occupied yet.
     *
     * @param direction The direction from which a marble wants to be accepted onto this tileable
     *                  entity.
     * @param marble The marble that wants to be accepted onto the tile of this tileable entity.
     */
    @Override
    public void accept(Direction direction, Marble marble) {
        getSlot(direction).accept(marble);
    }

    /**
     * Inform the listeners of this {@link Receptor} that it has been marked.
     */
    private void informMarked() {
        for (TileableListener listener : getListeners()) {
            if (listener instanceof ReceptorListener) {
                ((ReceptorListener) listener).receptorMarked(this);
            }
        }
    }
}
