package nl.tudelft.broccoli.core.level.easy;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.core.level.LevelFactoryTest;
import org.junit.Test;

/**
 * Test class for the {@link EasyLevelFactory}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class EasyLevelFactoryTest extends LevelFactoryTest {

    /**
     * Return the {@link LevelFactory} instance to test.
     *
     * @return The level factory to test.
     */
    @Override
    protected LevelFactory createFactory() {
        return new EasyLevelFactory();
    }

    @Test
    public void createLevelOne() {
        assertThat(factory.create(1)).isInstanceOf(EasyLevelOne.class);
    }

    @Test
    public void createLevelTwo() {
        assertThat(factory.create(2)).isInstanceOf(EasyLevelTwo.class);
    }

    @Test
    public void createLevelThree() {
        assertThat(factory.create(3)).isInstanceOf(EasyLevelThree.class);
    }
}
