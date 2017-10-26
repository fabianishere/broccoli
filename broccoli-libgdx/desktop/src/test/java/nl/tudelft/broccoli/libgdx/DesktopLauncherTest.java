package nl.tudelft.broccoli.libgdx;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.junit.Test;

/**
 * This class tests the tests the {@link Broccoli} class.
 */
public class DesktopLauncherTest {

    /**
     * Trying to run the game without crashing.
     */
    @Test
    public void smokeTest() {
        // This smoke test is so disgusting, you would not even consider giving
        // it to your mother-in-law.
        // But it shows that the game runs and exits without crashing.
        assertThatCode(() -> {
            DesktopLauncher.main(new String[] {});
            // Wait two minutes into the game
            Thread.sleep(2000);
            // Stop the application
            ((LwjglApplication) Gdx.app).stop();
        }).doesNotThrowAnyException();
    }
}
