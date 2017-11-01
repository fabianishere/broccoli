package nl.tudelft.broccoli.core.level;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.level.easy.AbstractEasyLevel;
import nl.tudelft.broccoli.core.level.easy.EasyLevelOne;
import org.junit.Test;

/**
 * Testing class that tests the {@link AbstractEasyLevel} class.
 */
public class AbstractEasyLevelTest extends LevelTest {
    /**
     * Return the {@link Level} instance to test.
     *
     * @return The level to test.
     */
    @Override
    public Level createLevel() {
        return new EasyLevelOne();
    }

    /**
     * Test the difficulty of the level.
     */
    @Test
    public void getIndex() {
        assertThat(level.getIndex()).isEqualTo(1);
    }
}
