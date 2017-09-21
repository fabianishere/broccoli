package nl.tudelft.broccoli.defpro;

import nl.tu.delft.defpro.api.APIProvider;
import nl.tu.delft.defpro.api.IDefProAPI;

/**
 * An Configuration class used to read the extern config file and used to store them during runtime. This class
 * should not throw an Exception ever. The configuration file is modifiable by the end user and could be filled with
 * mistakes.
 *
 * @author Christov
 */
public class Configuration {
    /**
     * The {@link nl.tudelft.broccoli.core.nexus.Nexus} spawns jokers when true.
     */
    private boolean jokersAllowed = true;

    /**
     * The amount of time in seconds the player has to complete the level.
     */
    private int timeLimit = Integer.MAX_VALUE;
    // TODO add configurations of type float, string and list

    /**
     * Construct a {@link Configuration} instance.
     */
    public Configuration() {
        try {
            // the url is hardcoded for now
            IDefProAPI config = APIProvider.getAPI("C:/Users/Christov/Documents/TI/SEM/broccoli/broccoli-defpro/src" +
                    "/main/java/nl/tudelft/broccoli/defpro/config.txt");

            jokersAllowed = config.getBooleanValueOf("jokersAllowed");
            timeLimit = config.getIntegerValueOf("timeLimit");
        } catch (Exception e) {
            System.out.println("Config file could not be loaded. Default values were used.");
        }
    }

    /**
     * Return whether jokers are spawned by the {@link nl.tudelft.broccoli.core.nexus.Nexus}.
     *
     * @return whether jokers are spawned by the {@link nl.tudelft.broccoli.core.nexus.Nexus}.
     */
    public boolean isJokersAllowed() {
        return jokersAllowed;
    }

    /**
     * Return the time limit.
     *
     * @return the time limit.
     */
    public int getTimeLimit() {
        return timeLimit;
    }
}
