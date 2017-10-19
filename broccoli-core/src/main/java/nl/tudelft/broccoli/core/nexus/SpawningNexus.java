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

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.config.DoubleProperty;
import nl.tudelft.broccoli.core.config.ListProperty;
import nl.tudelft.broccoli.core.config.Property;
import nl.tudelft.broccoli.core.grid.Direction;

import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * A part of the nexus which spawns new balls.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class SpawningNexus extends Nexus {
    /**
     * A configuration property to configure the probability of a joker occurring.
     */
    public static final Property<Double> JOKER_PROBABILITY =
        new DoubleProperty("nexus.joker", 0);

    /**
     * The initial sequence of balls.
     */
    public static final Property<List<String>> INITIAL_SEQUENCE =
        new ListProperty<>(String.class,"nexus.initial");

    /**
     * The possible type of marbles.
     */
    private static final MarbleType[] MARBLES = MarbleType.values();

    /**
     * The {@link Random} instance used for determining the color of the spawned ball.
     */
    private final Random random;

    /**
     * The direction from which new balls will be spawned.
     */
    private final Direction direction;

    /**
     * The probability of a joker occurring.
     */
    private final double joker;

    /**
     * The initial sequence of balls to spawn.
     */
    private final Queue<MarbleType> initial;

    /**
     * Construct a {@link SpawningNexus} instance.
     *
     * @param context The nexus context instance to use.
     * @param random The random number generator to use for selecting the colors.
     * @param direction The direction from which the balls will be spawned.
     * @param joker The probability of a joker occurring.
     * @param initial The initial sequence of balls to spawn.
     */
    public SpawningNexus(NexusContext context, Random random, Direction direction, double joker,
            Queue<MarbleType> initial) {
        super(context);
        this.random = random;
        this.direction = direction;
        this.joker = joker;
        this.initial = initial;
    }

    /**
     * Spawn a new {@link Marble} onto this {@link SpawningNexus}.
     *
     * @return The ball that has been spawned.
     * @throws IllegalStateException if the nexus is occupied by a ball.
     */
    public Marble spawn() {
        if (getContext().isOccupied()) {
            throw new IllegalStateException("The nexus is already occupied by a marble");
        }

        Marble marble;

        if (!initial.isEmpty()) {
            marble = new Marble(initial.poll());
        } else if (random.nextDouble() < joker) {
            marble = new Marble(MarbleType.JOKER);
        } else {
            marble = new Marble(MARBLES[random.nextInt(MARBLES.length - 1)]);
        }

        accept(direction, marble);
        getContext().setOccupied(true);
        return marble;
    }

    /**
     * Return the {@link Random} instance used for selecting the color of a new {@link Marble}.
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
