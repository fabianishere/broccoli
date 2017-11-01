package nl.tudelft.broccoli.core.level.medium;

import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.core.level.LevelFactoryTest;

/**
 * Test class for the {@link MediumLevelFactory}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class MediumLevelFactoryTest extends LevelFactoryTest {

    /**
     * Return the {@link LevelFactory} instance to test.
     *
     * @return The level factory to test.
     */
    @Override
    protected LevelFactory createFactory() {
        return new MediumLevelFactory();
    }
}

