package nl.tudelft.broccoli.core.level.medium;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.level.LevelTest;
import org.junit.Test;

/**
 * Testing class that tests the {@link MediumLevelThree} class.
 */
public class MediumLevelThreeTest extends LevelTest {
    /**
     * Return the {@link Level} instance to test.
     *
     * @return The level to test.
     */
    @Override
    public Level createLevel() {
        return new MediumLevelThree();
    }

    /**
     * Test the difficulty of the level.
     */
    @Test
    public void getIndex() {
        assertThat(level.getIndex()).isEqualTo(3);
    }

    /**
     * Test the factory of the level.
     */
    @Test
    public void getFactory() {
        assertThat(level.getFactory()).isInstanceOf(MediumLevelFactory.class);
    }
}
