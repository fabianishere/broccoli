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

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;

import java.util.HashMap;
import java.util.Map;

/**
 * A track over which multiple balls can travel to one of the other tiles.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class Track extends Tileable {
    /**
     * A map that tracks the progress of balls on the track.
     */
    Map<Ball, Float> progress = new HashMap<>();

    /**
     * Determine whether this rail is connected at both endpoints.
     *
     * @return <code>true</code> if both endpoints are connected to a port, <code>false</code>
     *         otherwise.
     */
    public abstract boolean isConnected();

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
     * @param delta The scale at which the time passes, which is used to slow down or speed up an
     *              entity.
     */
    public void update(float delta) {
        if (!onGrid()) {
            throw new IllegalStateException("The track is not placed on a grid");
        }

        for (Ball ball : progress.keySet()) {
            float progress = this.progress.get(ball);
            // If progress is positive, the direction is to the right, otherwise to the left
            float signum = progress >= 0.f ? 1.f : -1.f;

            // Update the progress
            progress += signum * 0.1f * delta;

            // Check whether the ball should be offered to the next tile
            if (Math.abs(progress) >= 1.f)  {
                Direction direction = progress > 0.f ? Direction.RIGHT : Direction.LEFT;

                if (neighbourAccepts(direction)) {
                    this.progress.put(ball, signum * -0.f);
                } else {
                    this.progress.remove(ball);
                    release(direction, ball);
                }

                continue;
            }

            this.progress.put(ball, progress);
        }
    }
}
