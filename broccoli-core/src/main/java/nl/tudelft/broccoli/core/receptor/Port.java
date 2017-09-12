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

import nl.tudelft.broccoli.core.Rail;

/**
 * A fixed port on a {@link Receptor} which creates the connection between a {@link Rail} and a
 * single slot of the receptor.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class Port {
    /**
     * The {@link Rail} this port is connected with.
     */
    private Rail rail;

    /**
     * The elements of this enumeration describe the local orientation of a {@link Port} relative to
     * the {@link Receptor}.
     */
    public enum Orientation {
        UP, LEFT, DOWN, RIGHT,
    }

    /**
     * Return the {@link Slot} this port is currently connected to.
     *
     * <p>The implementation of this method is provided by a {@link Receptor} which provides a
     * concrete implementation of this class.
     *
     * @return A non-null slot to which this port is currently connected to.
     */
    public abstract Slot getSlot();

    /**
     * Return the {@link Receptor} of this port.
     *
     * <p>The implementation of this method is provided by a {@link Receptor} which provides a
     * concrete implementation of this class.
     *
     * @return The receptor this port is part of.
     */
    public abstract Receptor getReceptor();

    /**
     * Return the local orientation of this port relative to the receptor this port is part of.
     *
     * <p>The implementation of this method is provided by a {@link Receptor} which provides a
     * concrete implementation of this class.
     *
     * @return The local orientation of the port relative to the receptor.
     */
    public abstract Orientation getOrientation();

    /**
     * Return the {@link Rail} this port is connected to.
     *
     * <p>In case the {@link Port} is not connected to any rail, this method will return
     * <code>null</code> and {@link Port#isConnected()} will return <code>false</code>.
     *
     * @return The rail this port is connected to or <code>null</code> if the port is not connected
     *         to any rail.
     */
    public Rail getRail() {
        return rail;
    }

    /**
     * Determine whether this port is connected to a {@link Rail}.
     *
     * @return <code>true</code> if this port is connected to a rail, <code>false</code> otherwise.
     */
    public boolean isConnected() {
        return rail != null;
    }

    /**
     * Connect the port with a {@link Rail}.
     *
     * @param rail The rail to connect to.
     */
    public void connect(Rail rail) {
        this.rail = rail;

        // Create a symmetric relation if there isn't one already
        if (!rail.isConnected(this)) {
            rail.connect(this);
        }
    }
}
