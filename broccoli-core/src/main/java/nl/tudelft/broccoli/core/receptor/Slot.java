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
import nl.tudelft.broccoli.core.track.Track;

/**
 * A slot on a {@link Receptor} which stores {@link Ball}s.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public interface Slot {
    /**
     * Return the {@link Ball} that is stored in this slot.
     *
     * @return The ball that is stored in this slot or <code>null</code> if the slot is unoccupied.
     */
    Ball getBall();

    /**
     * Determine whether the slot is occupied with a {@link Ball}.
     *
     * @return <code>true</code> if the slot is occupied with a ball, <code>false</code> otherwise.
     */
    default boolean isOccupied() {
        return getBall() != null;
    }

    /**
     * Return the {@link Receptor} of this slot.
     *
     * @return The receptor of this slot.
     */
    Receptor getReceptor();

    /**
     * Accept a ball into this slot if the slot is not already occupied.
     *
     * @param ball The ball to accept in this slot.
     * @throws IllegalStateException if the slot is already occupied.
     */
    void accept(Ball ball);

    /**
     * Release the current ball in the slot to the {@link Track} the port of this slot is connected
     * to.
     *
     * @throws IllegalStateException if the slot is currently unoccupied or the port of this slot is
     *                               not connected to a rail.
     */
    void release();
}
