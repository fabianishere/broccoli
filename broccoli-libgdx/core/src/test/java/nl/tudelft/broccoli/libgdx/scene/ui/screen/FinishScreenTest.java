package nl.tudelft.broccoli.libgdx.scene.ui.screen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Progress;
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
public class FinishScreenTest {
    /**
     * The stage to use.
     */
    private StackableStage stage;

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
            @Override
            public void create() {
                stage = new StackableStage(new ScreenViewport(), spy(new ScreenStack()));
                context = new ActorContext(ConfigurationLoader.STUB, new TextureAtlas(
                    Gdx.files.classpath("atlas/sprites.atlas")));
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
     * Test if pressing the return button causes the game go to the start screen.
     */
    @Test
    public void testReturn() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        Progress progress = mock(Progress.class);
        GameSession session = mock(GameSession.class);
        when(session.getProgress()).thenReturn(progress);
        when(progress.isWon()).thenReturn(false);

        latch = new CountDownLatch(1);

        // Create finish screen
        app.postRunnable(() -> {
            FinishScreen screen = new FinishScreen(context, session);
            stage.addActor(screen);
            screen.getActor().getChildren().get(1).fire(new ChangeListener.ChangeEvent());
            latch.countDown();
        });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait 400ms in order for the events to be processed
        verify(stage.getScreenStack(), after(400)).pop(eq(1));
    }

    /**
     * Test if the won text is shown.
     */
    @Test
    public void testWon() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);
        // Add finish screen to scene.
        Progress progress = mock(Progress.class);
        GameSession session = mock(GameSession.class);
        when(session.getProgress()).thenReturn(progress);
        when(progress.isWon()).thenReturn(true);

        final FinishScreen[] screen = new FinishScreen[1];

        // Create finish screen
        app.postRunnable(() -> {
            screen[0] = new FinishScreen(context, session);
            stage.addActor(screen[0]);
            latch.countDown();
        });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(((Label) screen[0].getActor().getChildren().first()).getText())
            .contains("Congratulations, you won the game\n")
            .contains("Your score was 0");
    }
}
