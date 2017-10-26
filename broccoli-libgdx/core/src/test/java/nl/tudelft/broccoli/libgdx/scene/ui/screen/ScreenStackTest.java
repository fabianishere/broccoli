package nl.tudelft.broccoli.libgdx.scene.ui.screen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link ScreenStack} actor.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ScreenStackTest {
    /**
     * The application that is used for testing.
     */
    private static LwjglApplication app;

    /**
     * The stage we use for testing.
     */
    private static Stage stage;

    /**
     * The actor under test.
     */
    private ScreenStack actor;

    /**
     * Signal used to indicate the actor is initialised.
     */
    private CountDownLatch latch;

    /**
     * Set up the test suite.
     */
    @BeforeClass
    public static void setUpSuite() throws Exception {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1;
        config.height = 1;
        config.resizable = false;
        config.forceExit = false;

        Game game = new Game() {
            @Override
            public void create() {
                stage = new Stage(new ScreenViewport());
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
     * Set up the test.
     */
    @Before
    public void setUp() throws Exception {
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor = new ScreenStack();
            stage.addActor(actor);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
    }

    /**
     * Tear down the test suite.
     */
    @AfterClass
    public static void tearDownSuite() throws Exception {
        app.stop();

        Gdx.gl = null;
        Gdx.gl20 = null;
        Gdx.gl30 = null;
    }

    /**
     * Tear down the test.
     */
    @After
    public void tearDown() throws Exception {
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.remove();
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
    }

    /**
     * Test if pushing a screen works.
     */
    @Test
    public void testPush() throws Exception {
        Actor screen = spy(new Actor());
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.push(screen);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 500ms in order for the events to be processed
        Thread.sleep(500);

        verify(screen, atLeastOnce()).draw(any(), anyFloat());
        verify(screen, atLeastOnce()).act(anyFloat());
    }

    /**
     * Test if replace a screen works.
     */
    @Test
    public void testReplace() throws Exception {
        Actor screenA = spy(new Actor());
        Actor screenB = spy(new Actor());
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.push(screenA);
            actor.replace(screenB);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        reset(screenA);

        // Wait 500ms in order for the events to be processed
        Thread.sleep(500);

        verify(screenA, never()).draw(any(), anyFloat());
        verify(screenA, never()).act(anyFloat());

        verify(screenB, atLeastOnce()).draw(any(), anyFloat());
        verify(screenB, atLeastOnce()).act(anyFloat());
        assertThat(stage.getKeyboardFocus()).isEqualTo(screenB);
    }

    /**
     * Test if replace a non existent screen works.
     */
    @Test
    public void testReplaceNonExistent() throws Exception {
        Actor screen = spy(new Actor());
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.replace(screen);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 500ms in order for the events to be processed
        Thread.sleep(500);

        verify(screen, atLeastOnce()).draw(any(), anyFloat());
        verify(screen, atLeastOnce()).act(anyFloat());
    }

    /**
     * Test if pop(0) does not pop any screens.
     */
    @Test
    public void testPopNone() throws Exception {
        Actor screen = spy(new Actor());
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.push(screen);
            actor.pop(0);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 500ms in order for the events to be processed
        Thread.sleep(500);

        verify(screen, atLeastOnce()).draw(any(), anyFloat());
        verify(screen, atLeastOnce()).act(anyFloat());
        assertThat(stage.getKeyboardFocus()).isEqualTo(screen);
    }

    /**
     * Test if pop(1) does not pop a non existent screen.
     */
    @Test
    public void testPopNoChildren() throws Exception {
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.pop(1);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 500ms in order for the events to be processed
        Thread.sleep(500);

        assertThat(actor.hasChildren()).isFalse();
    }

    /**
     * Test if pop(1) works correctly.
     */
    @Test
    public void testPopRemaining() throws Exception {
        Actor screenA = spy(new Actor());
        Actor screenB = spy(new Actor());

        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.push(screenA);
            actor.push(screenB);
            actor.pop(1);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        reset(screenB);

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        verify(screenA, atLeastOnce()).draw(any(), anyFloat());
        verify(screenA, atLeastOnce()).act(anyFloat());
        verify(screenB, never()).draw(any(), anyFloat());
        verify(screenB, never()).act(anyFloat());
        assertThat(stage.getKeyboardFocus()).isEqualTo(screenA);
    }

    /**
     * Test if actions are executed without stage.
     */
    @Test
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void testActionsNoStage() throws Exception {
        Graphics real = Gdx.graphics;
        Gdx.graphics = spy(real);
        latch = new CountDownLatch(1);
        app.postRunnable(() -> {
            actor.remove();
            actor.addAction(Actions.delay(1));
            actor.act(1);
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();


        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        verify(Gdx.graphics, times(1)).requestRendering();
        Gdx.graphics = real;
    }


    /**
     * Test if actions are executed without stage.
     */
    @Test
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void testActionsStage() throws Exception {
        Graphics real = Gdx.graphics;
        Gdx.graphics = spy(real);
        latch = new CountDownLatch(2);
        app.postRunnable(() -> {
            stage.setActionsRequestRendering(false);
            actor.addAction(Actions.delay(200));
            actor.addAction(Actions.run(() -> latch.countDown()));
            latch.countDown();
        });
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();


        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        verify(Gdx.graphics, atLeast(1)).requestRendering();
        Gdx.graphics = real;
    }
}
