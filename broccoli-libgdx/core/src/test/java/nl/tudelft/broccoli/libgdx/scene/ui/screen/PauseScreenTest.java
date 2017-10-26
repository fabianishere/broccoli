package nl.tudelft.broccoli.libgdx.scene.ui.screen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import nl.tudelft.broccoli.libgdx.scene.StackableStage;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link PauseScreen} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PauseScreenTest {
    /**
     * The pause actor under test.
     */
    private PauseScreen actor;

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
            private StackableStage stage;
            private ActorContext context;

            @Override
            public void create() {
                stack = spy(new ScreenStack());
                stage = new StackableStage(new ScreenViewport(), stack);
                context = new ActorContext(ConfigurationLoader.STUB, new TextureAtlas(
                    Gdx.files.classpath("atlas/sprites.atlas")));
                actor = new PauseScreen(context);
                stage.push(actor);
                latch.countDown();
            }

            @Override
            public void resize(int width, int height) {
                stage.getViewport().update(width, height, true);
            }

            @Override
            public void render() {
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                stage.act(Gdx.graphics.getDeltaTime());
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
     * Test if pressing the escape key causes the actor to resume.
     */
    @Test
    public void testEscape() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        // Fire escape button press event
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.ESCAPE);
        app.postRunnable(() -> {
            actor.fire(event);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        verify(stack).pop(eq(1));
    }

    /**
     * Test if pressing the any other key does not cause the actor to resume.
     */
    @Test
    public void testOtherKey() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        // Fire escape button press event
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.E);
        app.postRunnable(() -> {
            actor.fire(event);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        verify(stack, never()).pop(anyInt());
    }


    /**
     * Test if pressing the resume button causes the actor to resume.
     */
    @Test
    public void testResume() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        // Fire escape button press event
        app.postRunnable(() -> {
            actor.getActor().getChildren().first().fire(
                new ChangeListener.ChangeEvent());
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        verify(stack).pop(eq(1));
    }

    /**
     * Test if pressing the quit button causes the actor to resume.
     */
    @Test
    public void testQuit() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        // Fire escape button press event
        app.postRunnable(() -> {
            actor.getActor().getChildren().get(1).fire(
                new ChangeListener.ChangeEvent());
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        verify(stack).pop(eq(2));
    }
}
