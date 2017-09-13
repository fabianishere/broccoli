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
import nl.tudelft.broccoli.core.receptor.Port;

import java.util.HashMap;
import java.util.Map;

/**
 * A rail over which multiple balls can travel to one of the endpoints.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Rail extends Tileable {
    /**
     * The origin point port this rail is connected to.
     */
    private Port origin;

    /**
     * The destination port this rail is connected to.
     */
    private Port destination;

    /**
     * The progress of the balls on the rails.
     */
    private Map<Ball, Double> progress = new HashMap<>();

    /**
     * Return the {@link Port} at the opposite end of the rail from the specified port.
     *
     * @param port The port to get the opposite of.
     * @return The port at the opposite end of the rail or <code>null</code> if the rail is not
     *         connected at the opposite end of the rail.
     * @throws IllegalArgumentException if the specified port is not connected to the rail.
     */
    public Port opposite(Port port) {
        if (port == null || !isConnected(port)) {
            throw new IllegalArgumentException("The specified port is not connected to the rail");
        } else if (port.equals(origin)) {
            return destination;
        } else {
            return origin;
        }
    }

    /**
     * Determine whether the given {@link Port} is an endpoint of the rail.
     *
     * @param port The port to check.
     * @return <code>true</code> if the port is an endpoint of the rail, <code>false</code>
     *         otherwise.
     */
    public boolean isConnected(Port port) {
        return port != null && (port.equals(origin) || port.equals(destination));
    }

    /**
     * Determine whether this rail is connected at both endpoints.
     *
     * @return <code>true</code> if both endpoints are connected to a port, <code>false</code>
     *         otherwise.
     */
    public boolean isConnected() {
        return origin != null && destination != null;
    }

    /**
     * Connect a {@link Port} to an unconnected endpoint of this rail.
     *
     * @param port The port to connect to an unconnected endpoint of the rail.
     * @throws IllegalStateException if the rail is connected on both ends.
     */
    public void connect(Port port) {
        if (isConnected(port)) {
            throw new IllegalStateException("The rail is fully connected");
        }

        if (origin == null) {
            origin = port;
        } else if (destination == null) {
            destination = port;
        }

        // Create a symmetric relation if there isn't one already
        if (!this.equals(port.getRail())) {
            port.connect(this);
        }
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
     * Accept a {@link Ball} onto the tile of this tileable entity.
     *
     * @param direction The direction from which a ball wants to be accepted onto this tileable
     *                  entity.
     * @param ball The ball that wants to be accepted onto the tile of this tileable entity.
     */
    @Override
    public void accept(Direction direction, Ball ball) { }

    /**
     * Accept a ball onto this rail from the given {@link Port}.
     *
     * @param port The port to accept the ball from.
     * @param ball The ball to accept onto this rail.
     * @throws IllegalArgumentException if the port given is not connected to this rail
     */
    public void accept(Port port, Ball ball) {
        if (!isConnected(port)) {
            throw new IllegalArgumentException("The specified port is not connected to this rail");
        }
        double direction = port.equals(origin) ? 0.0 : -0.0;
        progress.putIfAbsent(ball, direction);
    }

    /**
     * Return the progress of a {@link Ball} on this rail.
     *
     * @param ball The ball to get the progress of.
     * @return A double with domain [-1, 1] where the sign describes the direction and the absolute
     *         value describes the progress.
     * @throws IllegalArgumentException if the ball is not on the rail currently.
     */
    public double progressOf(Ball ball) {
        if (!progress.containsKey(ball)) {
            throw new IllegalArgumentException("The ball is not traveling on the rail");
        }
        return progress.get(ball);
    }

    /**
     * Update the internal state of the entity.
     *
     * <p>This method should be invoked at a static interval to allow entities to update its state
     * over time.</p>
     *
     * @param scale The scale at which the time passes, which is used to slow down or speed up an
     *              entity.
     */
    public void update(double scale) {
        for (Ball ball : progress.keySet()) {
            double progress = this.progress.get(ball);
            // If progress is positive, the direction is to the right, otherwise to the left
            double signum = progress >= 0.0 ? 1.0 : -1.0;

            // Update the progress
            progress += signum * 0.1 * scale;

            // The ball has reached an endpoint, try to get accepted by that endpoint
            if (Math.abs(progress) >= 1) {
                Port port = signum >= 0.0 ? destination : origin;

                if (port.getSlot().isOccupied()) {
                    this.progress.put(ball, -0.0 * signum);
                } else {
                    port.getSlot().accept(ball);
                    this.progress.remove(ball);
                }
            } else {
                this.progress.put(ball, progress);
            }
        }
    }
}
