package nl.tudelft.broccoli.core.level.medium;

import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.level.LevelFactory;

/**
 * A factory class which produces {@link AbstractMediumLevel} instances, based on a given level
 * index.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class MediumLevelFactory extends LevelFactory {
    /**
     * Create an {@link AbstractMediumLevel} for the given index.
     *
     * @param level The index of the level.
     * @return The {@link Level} that is generated.
     */
    public AbstractMediumLevel create(int level) {
        switch (level) {
            case 1:
                return new MediumLevelOne();
            case 2:
                return new MediumLevelTwo();
            case 3:
                return new MediumLevelThree();
            default:
                return null;
        }
    }
}
