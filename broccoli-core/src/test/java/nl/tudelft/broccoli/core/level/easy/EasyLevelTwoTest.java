package nl.tudelft.broccoli.core.level.easy;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.level.LevelTest;
import org.junit.Test;

/**
 * Testing class that tests the {@link EasyLevelTwo} class.
 */
public class EasyLevelTwoTest extends LevelTest {
    /**
     * Return the {@link Level} instance to test.
     *
     * @return The level to test.
     */
    @Override
    public Level createLevel() {
        return new EasyLevelTwo();
    }

    /**
     * Test the difficulty of the level.
     */
    @Test
    public void getIndex() {
        assertThat(level.getIndex()).isEqualTo(2);
    }

    /**
     * Test the factory of the level.
     */
    @Test
    public void getFactory() {
        assertThat(level.getFactory()).isInstanceOf(EasyLevelFactory.class);
    }
}
