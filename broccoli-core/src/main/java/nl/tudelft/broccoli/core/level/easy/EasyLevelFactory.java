package nl.tudelft.broccoli.core.level.easy;

import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.level.LevelFactory;

/**
 * A factory class which produces {@link AbstractEasyLevel} instances, based on a given level index.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class EasyLevelFactory extends LevelFactory {

    /**
     * Create a {@link AbstractEasyLevel} for the given index.
     *
     * @param level The index of the level.
     * @return The {@link Level} that is generated.
     */
    public AbstractEasyLevel create(int level) {
        switch (level) {
            case 1:
                return new EasyLevelOne();
            case 2:
                return new EasyLevelTwo();
            case 3:
                return new EasyLevelThree();
            default:
                return null;
        }
    }
}
