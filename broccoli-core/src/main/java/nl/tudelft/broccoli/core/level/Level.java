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

package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.config.Configuration;

/**
 * Implementors of the {@link Level} interface provide a factory interface to create a specific
 * configuration of a {@link GameSession} representing an in-game level.
 *
 * <p>A {@link Level} is the abstract configuration of a game and its board, while a
 * {@link GameSession} represents a playing session using the configuration provided by the level.
 * </p>
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public interface Level {
    /**
     * Create a new {@link GameSession} with this {@link Level}'s configuration.
     *
     * @param config The game configuration to use.
     * @return A {@link GameSession} for this {@link Level}.
     */
    GameSession create(Configuration config);

    /**
     * Return the {@link LevelFactory} of this level.
     *
     * @return The factory that this level was created by.
     */
    LevelFactory getFactory();

    /**
     * Return the index of this level.
     *
     * @return The index of this level.
     */
    int getIndex();
}
