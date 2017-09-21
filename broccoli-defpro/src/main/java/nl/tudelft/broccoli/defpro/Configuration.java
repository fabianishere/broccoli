package nl.tudelft.broccoli.defpro;

import nl.tu.delft.defpro.api.APIProvider;
import nl.tu.delft.defpro.api.IDefProAPI;
import nl.tudelft.broccoli.core.nexus.Nexus;

import java.io.File;

/**
 * An Configuration class used to read the external config file and used to store them during runtime. This class
 * should not throw an Exception ever. The configuration file is modifiable by the end user and could be filled with
 * mistakes.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class Configuration {
    /**
     * The {@link Nexus} spawns jokers when true.
     */
    private boolean jokersAllowed = true;

    /**
     * The amount of time in seconds the player has to complete the level.
     */
    private int timeLimit = Integer.MAX_VALUE;

    /**
     * Load the configuration at the given location into this {@link Configuration} object.
     *
     * @param file The file to read the configuration from.
     */
    public void load(File file) {
        if (file == null) {
            throw new IllegalArgumentException("The file given was null");
        } else if (!file.exists()) {
            throw new IllegalArgumentException("The given file does not exist");
        }
        try {
            // the url is hardcoded for now
            IDefProAPI config = APIProvider.getAPI(file.getAbsolutePath());

            jokersAllowed = config.getBooleanValueOf("jokersAllowed");
            timeLimit = config.getIntegerValueOf("timeLimit");
        } catch (Exception e) {
            System.out.println("Config file could not be loaded. Default values were used.");
        }
    }

    /**
     * Load the configuration file at the default location (config.txt).
     * If the file does not exists, the existing configuration object is unchanged.
     */
    public void load() {
        load(new File("config.txt"));
    }

    /**
     * Return whether jokers are spawned by the {@link Nexus}.
     *
     * @return whether jokers are spawned by the {@link Nexus}.
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
