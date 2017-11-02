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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.Property;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.core.powerup.PowerUpFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Testing class that tests the {@link Level} class.
 */
public abstract class LevelTest {
    /**
     * The level under test.
     */
    Level level;

    /**
     * The configuration of the level.
     */
    private Configuration config;

    /**
     * Return the {@link Level} instance to test.
     *
     * @return The level to test.
     */
    public abstract Level createLevel();

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        level = createLevel();
        config = mock(Configuration.class);
        when(config.exists(any())).thenReturn(false);
        when(config.get(any()))
            .thenAnswer((invocation) -> invocation.getArgumentAt(0, Property.class).getDefault());
        when(config.get(any(), any()))
            .thenAnswer((invocation) -> invocation.getArgumentAt(1, Object.class));
    }

    /**
     * Test if calling the create function gives an instance of a {@link GameSession}.
     */
    @Test
    public void createTest() {
        assertThat(level.create(config)).isInstanceOf(GameSession.class);
    }

    /**
     * Test to see if getting the name of the level is simple.
     */
    @Test
    public void getName() {
        assertThat(level.getName()).isEqualTo("simple");
    }

    /**
     * Test that the game is linked to the level that created it.
     */
    @Test
    public void getLevel() {
        assertThat(level.create(config).getLevel()).isEqualTo(level);
    }

    /**
     * Test that the game uses the initial sequence defined in the config.
     */
    @Test
    public void useInitialSequence() {
        when(config.exists(SpawningNexus.INITIAL_SEQUENCE)).thenReturn(true);
        when(config.get(SpawningNexus.INITIAL_SEQUENCE)).thenReturn(Arrays.asList("GREEN",
            "BLUE", "PINK", "JOKER", "YELLOW", "INVALID"));

        GameSession session = level.create(config);
        SpawningNexus nexus = (SpawningNexus) session.getGrid().get(3, 3).getTileable();
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.GREEN);
        nexus.getContext().setOccupied(false);
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.BLUE);
        nexus.getContext().setOccupied(false);
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.PINK);
        nexus.getContext().setOccupied(false);
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.JOKER);
        nexus.getContext().setOccupied(false);
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.YELLOW);
        nexus.getContext().setOccupied(false);
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.BLUE);
    }

    /**
     * Test that the game returns the correct grid.
     */
    @Test
    public void getGrid() {
        GameSession session = level.create(config);
        assertThat(session.getGrid().getSession()).isEqualTo(session);
    }

    /**
     * Test that the game returns the correct configuration.
     */
    @Test
    public void getConfig() {
        AbstractGameSession session = (AbstractGameSession) level.create(config);
        assertThat(session.getConfig()).isEqualTo(config);
    }

    /**
     * Test that the game is initially not won.
     */
    @Test
    public void getProgress() {
        GameSession session = level.create(config);
        assertThat(session.getProgress()).isNotNull();
    }


    /**
     * Test that the game session contains a {@link PowerUpFactory}.
     */
    @Test
    public void getPowerUpFactory() {
        GameSession session = level.create(config);
        assertThat(session.getPowerUpFactory()).isNotNull();
    }
}
