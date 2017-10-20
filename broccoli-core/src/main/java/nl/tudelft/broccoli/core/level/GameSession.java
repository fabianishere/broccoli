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

import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.powerup.PowerUpFactory;
import nl.tudelft.broccoli.core.receptor.Receptor;

/**
 * A single playing session of a Gudeballs game.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public interface GameSession {
    /**
     * Return the playing grid of this game.
     *
     * @return The {@link Grid} on which the game is played.
     */
    Grid getGrid();

    /**
     * Return the {@link Progress} of the player in the game.
     *
     * @return The progress of the player in the game.
     */
    Progress getProgress();

    /**
     * Return the {@link Level} this game represents.
     *
     * @return The level of this game.
    */
    Level getLevel();

    /**
     * Return the {@link NexusContext} of the game session.
     *
     * @return The context of the nexus instances.
     */
    NexusContext getNexusContext();

    /**
     * Return the {@link PowerUpFactory} used by this {@link GameSession} to create new
     * {@link PowerUp}s to be assigned to {@link Receptor}s.
     *
     * @return The power-up factory of the game session.
     */
    PowerUpFactory getPowerUpFactory();
}
