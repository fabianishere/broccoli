package nl.tudelft.broccoli.libgdx.scene.ui.screen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.core.level.easy.EasyLevelFactory;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import nl.tudelft.broccoli.libgdx.scene.StackableStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link FinishScreen} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class StartScreenTest {
    /**
     * The start screen to use.
     */
    private StartScreen actor;

    /**
     * The view stack.
     */
    private ScreenStack stack;

    /**
     * The application that is used for testing.
     */
    private LwjglApplication app;

    /**
     * Signal used to indicate the actor is initialised.
     */
    private CountDownLatch latch;

    /**
     * The actor context.
     */
    private ActorContext context;

    /**
     * The level factory.
     */
    private LevelFactory factory;

    /**
     * Set up the test suite.
     */
    @Before
    public void setUp() throws Exception {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 400;
        config.height = 400;
        config.resizable = false;
        config.forceExit = false;

        latch = new CountDownLatch(1);

        Game game = new Game() {
            private Stage stage;

            @Override
            public void create() {
                stack = spy(new ScreenStack());
                stage = new StackableStage(new ScreenViewport(), stack);
                context = new ActorContext(ConfigurationLoader.STUB, new TextureAtlas(
                    Gdx.files.classpath("atlas/sprites.atlas")));
                factory = spy(new EasyLevelFactory());
                actor = new StartScreen(context, factory);
                stage.addActor(actor);
                latch.countDown();
            }

            @Override
            public void resize(int width, int height) {
                stage.getViewport().update(width, height, true);
            }

            @Override
            public void render() {
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                stage.act(Gdx.graphics.getDeltaTime() * 10);
                stage.draw();
            }

            @Override
            public void dispose() {
                stage.dispose();
            }
        };
        app = new LwjglApplication(game, config);
    }

    /**
     * Tear down the test.
     */
    @After
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void tearDown() throws Exception {
        app.stop();

        Gdx.gl = null;
        Gdx.gl20 = null;
        Gdx.gl30 = null;
    }

    /**
     * Test if pressing the first button creates an easy level.
     */
    @Test
    public void testEasy() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        // Fire the event
        app.postRunnable(() -> {
            actor.getActor().getChildren().get(1)
                .fire(new ChangeListener.ChangeEvent());
            latch.countDown();
        });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 200 ms for the event to be processed
        verify(factory, after(200)).create(eq(1));
        verify(stack).push(any(GameScreen.class));
    }

    /**
     * Test if pressing the second button creates an medium level.
     */
    @Test
    public void testMedium() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        // Fire the event
        app.postRunnable(() -> {
            actor.getActor().getChildren().get(2)
                .fire(new ChangeListener.ChangeEvent());
            latch.countDown();
        });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 200 ms for the event to be processed
        verify(factory, after(200)).create(eq(2));
        verify(stack).push(any(GameScreen.class));
    }

    /**
     * Test if pressing the third button creates a hard level.
     */
    @Test
    public void testHard() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        // Fire the event
        app.postRunnable(() -> {
            actor.getActor().getChildren().get(3)
                .fire(new ChangeListener.ChangeEvent());
            latch.countDown();
        });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 200 ms for the event to be processed
        verify(factory, after(200)).create(eq(3));
        verify(stack).push(any(GameScreen.class));
    }

    /**
     * Test if pressing the last button quits the game.
     */
    @Test
    public void testQuit() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        LifecycleListener listener = mock(LifecycleListener.class);
        app.addLifecycleListener(listener);

        // Fire the event
        app.postRunnable(() -> {
            actor.getActor().getChildren().get(4)
                .fire(new ChangeListener.ChangeEvent());
            latch.countDown();
        });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Verify that the application is disposed
        verify(listener, after(200)).dispose();
    }
}
