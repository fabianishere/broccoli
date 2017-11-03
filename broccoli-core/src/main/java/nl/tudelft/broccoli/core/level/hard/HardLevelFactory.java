package nl.tudelft.broccoli.core.level.hard;

import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.level.LevelFactory;

/**
 * A factory class which produces {@link AbstractHardLevel} instances, based on a given level index.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class HardLevelFactory extends LevelFactory {
    /**
     * Create a {@link AbstractHardLevel} for the given index.
     *
     * @param level The index of the level.
     * @return The {@link Level} that is generated.
     */
    public AbstractHardLevel create(int level) {
        switch (level) {
            case 1:
                return new HardLevelOne();
            case 2:
                return new HardLevelTwo();
            case 3:
                return new HardLevelThree();
            default:
                return null;
        }
    }
}
