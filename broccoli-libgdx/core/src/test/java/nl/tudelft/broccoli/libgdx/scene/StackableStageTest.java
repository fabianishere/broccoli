package nl.tudelft.broccoli.libgdx.scene;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.tudelft.broccoli.libgdx.scene.ui.screen.ScreenStack;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link StackableStage} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class StackableStageTest {
    /**
     * The application that runs.
     */
    private static LwjglApplication app;

    /**
     * The stage to test.
     */
    private StackableStage stage;

    /**
     * The underlying stack to use.
     */
    private ScreenStack stack;

    /**
     * Signal used to indicate the actor is initialised.
     */
    private CountDownLatch latch;


    /**
     * Set up the test suite.
     */
    @BeforeClass
    public static void setUpSuite() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 1;
        config.width = 1;
        config.resizable = false;
        config.forceExit = false;
        Game game = mock(Game.class);
        app = new LwjglApplication(game, config);
    }

    /**
     * Tear down the test suite.
     */
    @AfterClass
    public static void tearDownSuite() {
        app.stop();

        Gdx.gl = null;
        Gdx.gl20 = null;
        Gdx.gl30 = null;
    }

    /**
     * Set up before a test.
     */
    @Before
    public void setUp() throws Exception {
        stack = mock(ScreenStack.class);
        latch = new CountDownLatch(1);

        app.postRunnable(() -> {
            stage = new StackableStage(new ScreenViewport(), stack);
            latch.countDown();
        });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
    }

    /**
     * Push a new screen to the stack.
     */
    @Test
    public void push() {
        Actor screen = mock(Actor.class);
        stage.push(screen);
        verify(stack).push(eq(screen));
    }

    /**
     * Replace a new screen in the stack.
     */
    @Test
    public void replace() {
        Actor screen = mock(Actor.class);
        stage.replace(screen);
        verify(stack).replace(eq(screen));
    }

    /**
     * Pop screens from the stack.
     */
    @Test
    public void pop() {
        stage.pop(2);
        verify(stack).pop(eq(2));
    }
}
