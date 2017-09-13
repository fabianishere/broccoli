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
import nl.tudelft.broccoli.core.Entity;
import nl.tudelft.broccoli.core.Rail;

import java.util.Arrays;

/**
 * An object with four {@link Slot}s (R1.2a) which accepts {@link Ball}s in open slots (R1.2b) via
 * {@link Port}s, which are connected to {@link Rail}s.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Receptor implements Entity {
    /**
     * The ports of this receptor.
     */
    private Port[] ports = {
        new InternalPort(Port.Orientation.UP), new InternalPort(Port.Orientation.LEFT),
        new InternalPort(Port.Orientation.DOWN), new InternalPort(Port.Orientation.RIGHT),
    };

    /**
     * The slots of this receptor.
     */
    private Ball[] slots = new Ball[4];

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
     * Return one of the four {@link Port}s of this receptor, with the given local orientation,
     * relative to this receptor.
     *
     * @param orientation The local orientation of the port relative to the receptor
     * @return A non-null port with the specified local orientation.
     */
    public Port getPort(Port.Orientation orientation) {
        // This method assumes the Port.Orientation enumeration is defined in a counterclockwise
        // order, having four values
        return ports[orientation.ordinal()];
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
        Ball head = slots[0];

        if (head == null) {
            return false;
        }

        for (int i = 1; i < slots.length; i++) {
            if (!head.isCompatible(slots[i])) {
                return false;
            }
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
     * The internal {@link Port} implementation for a {@link Receptor} instance.
     */
    protected class InternalPort extends Port implements Slot {
        /**
         * The orientation of the port.
         */
        private Orientation orientation;

        /**
         * Construct a {@link InternalPort} instance.
         *
         * @param orientation The orientation of the port.
         */
        InternalPort(Orientation orientation) {
            this.orientation = orientation;
        }

        /**
         * Return the {@link Slot} this port is currently connected to.
         *
         * @return A non-null slot to which this port is currently connected to.
         */
        @Override
        public Slot getSlot() {
            return this;
        }

        /**
         * Return the {@link Receptor} of this port.
         *
         * @return The receptor this port is part of.
         */
        @Override
        public Receptor getReceptor() {
            return Receptor.this;
        }

        /**
         * Return the local orientation of this port relative to the receptor this port is part of.
         *
         * <p>The implementation of this method is provided by a {@link Receptor} which provides a
         * concrete implementation of this class.
         *
         * @return The local orientation of the port relative to the receptor.
         */
        @Override
        public Orientation getOrientation() {
            return orientation;
        }

        /**
         * Return the {@link Ball} that is stored in this slot.
         *
         * @return The ball that is stored in this slot or <code>null</code> if the slot is
         *         unoccupied.
         */
        @Override
        public Ball getBall() {
            return slots[index()];
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
            slots[index()] = ball;

            // Mark the receptor if all balls are of the same color (R1.2e)
            if (shouldMark()) {
                mark();
            }
        }

        /**
         * Release the current ball in the slot to the {@link Rail} the port of this slot is
         * connected to.
         *
         * @throws IllegalStateException if the slot is currently unoccupied or the port of this
         *                               slot is not connected to a rail.
         */
        @Override
        public void release() {
            if (!isOccupied()) {
                throw new IllegalStateException("The slot is not occupied");
            } else if (!isConnected()) {
                throw new IllegalStateException("The port of the slot is not connected to a rail");
            }
            int index = index();
            Ball ball = slots[index];
            slots[index] = null;
            getRail().accept(this, ball);
        }

        /**
         * Return the index of the slot within the slots array.
         *
         * @return The index of the slot within the slots array.
         */
        private int index() {
            return (orientation.ordinal() + rotation) % slots.length;
        }
    }
}
