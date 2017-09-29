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

package nl.tudelft.broccoli.core.grid;

/**
 * This enumeration describes the direction at which another tile is located relative to a tile.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public enum Direction {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT;

    /**
     * An array containing the possible values of this enumeration.
     */
    private static final Direction[] VALUES = Direction.values();

    /**
     * Return the inverse {@link Direction} given a direction.
     *
     * @return The inverse direction.
     */
    public Direction inverse() {
        switch (this) {
            case TOP:
                return BOTTOM;
            case RIGHT:
                return LEFT;
            case BOTTOM:
                return TOP;
            case LEFT:
                return RIGHT;
            default:
                return null;
        }
    }

    /**
     * Rotate a {@link Direction} by <code>n</code> amount of 90 degree clockwise turns and return
     * the new direction.
     *
     * @param turns The amount of turns to make.
     * @return The resulting direction.
     */
    public Direction rotate(int turns) {
        return VALUES[Math.floorMod(ordinal() + turns, VALUES.length)];
    }

    /**
     * Return a {@link Direction} given an clockwise index.
     *
     * @param index The index of the direction to get.
     * @return The resulting direction.
     */
    public static Direction from(int index) {
        return VALUES[Math.floorMod(index, VALUES.length)];
    }
}
