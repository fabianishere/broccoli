package nl.tudelft.broccoli.core.level;

/**
 * An factory class which produces {@link Level} instances, based on a given {@link Difficulty}.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 */
public class LevelFactory {
    /**
     * Create a {@link Level} for the given {@link Difficulty}.
     *
     * @param difficulty The difficulty of the level.
     * @return The {@link Level} that is generated.
     */
    public Level create(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return new EasyLevel();
            case MEDIUM:
                return new MediumLevel();
            case HARD:
                return new HardLevel();
            default:
                // UNREACHABLE
                return null;
        }
    }
}
