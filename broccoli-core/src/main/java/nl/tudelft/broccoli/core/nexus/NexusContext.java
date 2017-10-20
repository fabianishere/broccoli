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

import java.util.Arrays;
import java.util.Queue;
import java.util.Random;

/**
 * A context for {@link Nexus} instances to check the state of other parts.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class NexusContext {
    /**
     * The possible type of marbles.
     */
    private static final MarbleType[] MARBLES = MarbleType.values();

    /**
     * A flag to indicate the nexus is currently occupied by a ball.
     */
    private boolean occupied;

    /**
     * The remaining sequence of balls to spawn.
     */
    private final Queue<MarbleType> queue;

    /**
     * The {@link Random} instance used for determining the color of the spawned ball.
     */
    private final Random random;

    /**
     * The probability of a joker occurring.
     */
    private final double joker;

    /**
     * Construct a {@link NexusContext} instance.
     *
     * @param queue The queue of balls to spawn.
     * @param random The random number generator to use for selecting the colors.
     * @param joker The probability that a joker is generated.
     */
    public NexusContext(Queue<MarbleType> queue, Random random, double joker) {
        this.queue = queue;
        this.random = random;
        this.joker = joker;

        // Generate the first ball to be spawned if the queue is empty initially.
        if (queue.isEmpty()) {
            queue.add(generate());
        }
    }

    /**
     * Peek into the queue of marbles to be spawned, without removing the element from the queue.
     *
     * @return The type of the marble to spawn next.
     */
    public MarbleType peek() {
        return queue.peek();
    }

    /**
     * Poll into the queue of marbles to be spawned, removing the element from the queue.
     *
     * @return The type of the marble to spawn next.
     */
    public MarbleType poll() {
        MarbleType type = queue.poll();

        if (queue.isEmpty()) {
            queue.add(generate());
        }

        return type;
    }

    /**
     * Add the given {@link MarbleType}s to the queue of marbles to be spawned.
     *
     * @param types The marble types to add.
     */
    public void add(MarbleType ...types) {
        queue.addAll(Arrays.asList(types));
    }

    /**
     * Generate a random {@link Marble} to be spawned by this {@link SpawningNexus}.
     *
     * @return The marble to be spawned.
     */
    private MarbleType generate() {
        if (random.nextDouble() < joker) {
            return MarbleType.JOKER;
        }

        return MARBLES[random.nextInt(MARBLES.length - 1)];
    }

    /**
     * Determine whether a part of the nexus is occupied by a ball.
     *
     * @return <code>true</code> if a part of the nexus is occupied by a ball, <code>false</code>
     *         otherwise.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Set the occupancy of the nexus by a ball.
     *
     * @param occupied A boolean to indicate the nexus is currently occupied by a ball.
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
