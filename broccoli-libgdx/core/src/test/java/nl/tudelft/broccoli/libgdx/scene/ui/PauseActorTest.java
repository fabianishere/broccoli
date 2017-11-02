package nl.tudelft.broccoli.libgdx.scene.ui;

import static org.assertj.core.api.Assertions.assertThat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link PauseActor} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PauseActorTest {
    /**
     * The pause actor under test.
     */
    private PauseActor actor;

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
        config.width = 10;
        config.height = 10;
        config.resizable = false;
        config.forceExit = false;

        latch = new CountDownLatch(1);

        Game game = new Game() {
            private Stage stage;
            private ActorContext context;

            @Override
            public void create() {
                stage = new Stage(new ScreenViewport());
                context = new ActorContext(
                    new TextureAtlas(Gdx.files.classpath("atlas/sprites.atlas")));
                actor = new PauseActor(context);
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
     * Test if pressing the resume causes the actor to resume.
     */
    @Test
    public void testResume() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        app.postRunnable(() -> {
            actor.setPaused(true);
            actor.getChildren().first().fire(new ChangeListener.ChangeEvent());
        });

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        assertThat(actor.isPaused()).isFalse();
    }


    /**
     * Test if pressing the resume causes the actor to resume.
     */
    @Test
    public void testPause() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        app.postRunnable(() -> actor.getChildren().first().fire(new ChangeListener.ChangeEvent()));

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        assertThat(actor.isPaused()).isTrue();
    }
}
