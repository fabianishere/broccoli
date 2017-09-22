package nl.tudelft.broccoli.libgdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.Property;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * This class tests the tests the {@link Broccoli} class.
 */
public class BroccoliTest {

    /**
     * Trying to run the game without crashing.
     */
    @Test
    public void smokeTest() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Broccoli";
        config.width = 10;
        config.height = 10;
        config.resizable = false;

        Configuration gameConfig = new Configuration() {
            @Override
            public <T> T get(Property<T> property, T defaultValue) {
                return defaultValue;
            }

            @Override
            public boolean exists(Property<?> property) {
                return false;
            }
        };

        // This smoke test is so disgusting, you would not even consider giving
        // it to your mother-in-law.
        // But it shows that the game runs and exits without crashing.
        assertThatCode(() -> {
            Application app = new LwjglApplication(new Broccoli(gameConfig), config);
            Thread.sleep(800);
            app.exit();
        }).doesNotThrowAnyException();
    }
}
