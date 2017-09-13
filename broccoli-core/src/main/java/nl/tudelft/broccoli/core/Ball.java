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

import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.Track;

/**
 * An in-game ball that travels over {@link Track}s to be accepted by {@link Receptor}s.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public final class Ball implements Entity {
    /**
     * The type of the ball.
     */
    private Type type;

    /**
     * This enumeration describes the ball types available.
     */
    public enum Type {
        PINK,
        GREEN,
        BLUE,
        YELLOW,
        JOKER, // R3.3
    }

    /**
     * Construct a {@link Ball} instance.
     *
     * @param type The type of the ball to create.
     */
    private Ball(Type type) {
        this.type = type;
    }

    /**
     * Return a {@link Ball} instance with the given type.
     *
     * @param type The type of ball to get.
     * @return An instance of {@link Ball} with the given type.
     */
    public static Ball of(Type type) {
        return new Ball(type);
    }

    /**
     * Return the type of the ball.
     *
     * @return The type of the ball.
     */
    public Type getType() {
        return type;
    }

    /**
     * Determine whether the this {@link Ball} instance is compatible with the given ball.
     *
     * <p>Compatibility is determined by comparing the type (colour) of the ball, but if either of
     * the balls is a joker, the result is always <code>true</code>.</p>
     *
     * @param other The other ball to determine compatibility with.
     * @return <code>true</code> if the ball is compatible with the given ball, <code>false</code>
     *         otherwise.
     */
    public boolean isCompatible(Ball other) {
        return other != null && (type.equals(Type.JOKER) || other.type.equals(Type.JOKER)
            || type.equals(other.type));
    }
}
