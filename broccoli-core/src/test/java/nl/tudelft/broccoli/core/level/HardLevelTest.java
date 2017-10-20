package nl.tudelft.broccoli.core.level;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.level.*;
import org.junit.Test;

/**
 * Testing class that tests the {@link EasyLevel} class.
 */
public class HardLevelTest extends LevelTest {
    /**
     * Return the {@link Level} instance to test.
     *
     * @return The level to test.
     */
    @Override
    public Level createLevel() {
        return new HardLevel();
    }

    /**
     * Test the difficulty of the level.
     */
    @Test
    public void getDifficulty() {
        assertThat(level.getDifficulty()).isEqualTo(Difficulty.HARD);
    }
}
