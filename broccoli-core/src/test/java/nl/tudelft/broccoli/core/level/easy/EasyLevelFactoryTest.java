package nl.tudelft.broccoli.core.level.easy;

import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.core.level.LevelFactoryTest;

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
}
