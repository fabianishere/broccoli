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

package nl.tudelft.broccoli.core.nexus;

import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Direction;

import java.util.Random;

/**
 * A part of the nexus which spawns new balls.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class SpawningNexus extends Nexus {
    /**
     * The possible type of balls.
     */
    private static final Ball.Type[] BALLS = Ball.Type.values();

    /**
     * The {@link Random} instance used for determining the color of the spawned ball.
     */
    private Random random;

    /**
     * The direction from which new balls will be spawned.
     */
    private Direction direction;

    /**
     * Construct a {@link SpawningNexus} instance.
     *
     * @param context The nexus context instance to use.
     * @param random The random number generator to use for selecting the colors.
     * @param direction The direction from which the balls will be spawned.
     */
    public SpawningNexus(NexusContext context, Random random, Direction direction) {
        super(context);
        this.random = random;
        this.direction = direction;
    }

    /**
     * Spawn a new {@link Ball} onto this {@link SpawningNexus}.
     *
     * @return The ball that has been spawned.
     * @throws IllegalStateException if the nexus is occupied by a ball.
     */
    public Ball spawn() {
        if (getContext().isOccupied()) {
            throw new IllegalStateException("The nexus is already occupied by a ball");
        }

        // We skip the Joker ball for now
        Ball ball = Ball.of(BALLS[random.nextInt(BALLS.length - 1)]);
        accept(direction, ball);
        getContext().setOccupied(true);
        return ball;
    }

    /**
     * Return the {@link Random} instance used for selecting the color of a new {@link Ball}.
     *
     * @return The random instance to generate the ball color.
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Return the {@link Direction} from which the balls are spawned.
     *
     * @return The direction from which the balls are spawned.
     */
    public Direction getDirection() {
        return direction;
    }
}