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

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.core.track.Track;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.Direction;

import java.util.Arrays;

/**
 * An object with four {@link Slot}s (R1.2a) which accepts {@link Ball}s in open slots (R1.2b) via
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Receptor extends Tileable {
    /**
     * The slots of this receptor.
     */
    private Slot[] slots = {
        new InternalSlot(Direction.TOP), new InternalSlot(Direction.RIGHT),
        new InternalSlot(Direction.BOTTOM), new InternalSlot(Direction.LEFT),
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
        // This method assumes the Direction enumeration is defined in a counterclockwise
        // order, having four values
        return slots[direction.ordinal()];
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
            if (!slot.isOccupied() || !slot.getBall().isCompatible(slots[i].getBall())) {
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
        // Remove all balls from the slot
        Arrays.fill(slots, null);
    }

    /**
     * Internal {@link Slot} implementation of the {@link Receptor} class.
     */
    private class InternalSlot implements Slot {
        /**
         * The initial direction of this slot.
         */
        private Direction direction;

        /**
         * The ball stored in this slot.
         */
        private Ball ball;

        /**
         * Construct an {@link InternalSlot} instance.
         *
         * @param direction The initial direction of the slot.
         */
        InternalSlot(Direction direction) {
            this.direction = direction;
        }

        /**
         * Return the {@link Ball} that is stored in this slot.
         *
         * @return The ball that is stored in this slot or <code>null</code> if the slot is
         *         unoccupied.
         */
        @Override
        public Ball getBall() {
            return ball;
        }

        /**
         * Return the {@link Receptor} of this slot.
         *
         * @return The receptor of this slot.
         */
        @Override
        public Receptor getReceptor() {
            return Receptor.this;
        }

        /**
         * Accept a ball into this slot if the slot is not already occupied.
         *
         * @param ball The ball to accept in this slot.
         * @throws IllegalStateException if the slot is already occupied.
         */
        @Override
        public void accept(Ball ball) {
            if (isOccupied()) {
                throw new IllegalStateException("The slot is already occupied");
            }

            this.ball = ball;

            // Mark the receptor if all balls are of the same color (R1.2e)
            if (shouldMark()) {
                mark();
            }
        }

        /**
         * Release the current ball in the slot to the {@link Track} the port of this slot is
         * connected to.
         *
         * @throws IllegalStateException if the slot is currently unoccupied or the port of this
         *                               slot is not connected to a rail.
         */
        @Override
        public void release() {
            if (!isOccupied()) {
                throw new IllegalStateException("The slot is not occupied");
            } else if (!onGrid()) {
                throw new IllegalStateException("The receptor is not placed on a grid");
            }

            Direction direction = getDirection();
            Tile tile = getTile().get(direction);

            if (tile == null || !tile.getTileable().accepts(direction.inverse())) {
                throw new IllegalStateException("The slot cannot release the ball to its neighbor");
            }

            tile.getTileable().accept(direction.inverse(), ball);
            ball = null;
        }

        /**
         * Return the current direction of the {@link Slot} on the {@link Receptor}.
         *
         * @return The direction of the slot.
         */
        Direction getDirection() {
            return directions[(direction.ordinal() + rotation) % directions.length];
        }
    }

    /**
     * Determine whether this tileable entity accepts a ball onto its tile.
     *
     * <p>This class will only accept balls if the slot at that direction is not occupied yet.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @return <code>true</code> if the tileable entity accepts the ball onto its tile,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean accepts(Direction direction) {
        return !getSlot(direction).isOccupied();
    }

    /**
     * Accept the given {@link Ball} at the given direction.
     *
     * <p>This class will only accept balls if the slot at that direction is not occupied yet.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @param ball The ball that wants to be accepted onto the tile of this tileable entity.
     */
    @Override
    public void accept(Direction direction, Ball ball) {
        getSlot(direction).accept(ball);
    }
}
