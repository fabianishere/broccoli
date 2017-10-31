package nl.tudelft.broccoli.libgdx.scene.ui;

import static org.assertj.core.api.Assertions.assertThat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import nl.tudelft.broccoli.libgdx.scene.GameStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link PauseActor} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
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
     * Set up the test suite.
     */
    @Before
    public void setUp() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 100;
        config.height = 100;
        config.resizable = false;
        config.forceExit = false;

        Game game = new Game() {
            private GameStage stage;

            @Override
            public void create() {
                Configuration configuration = ConfigurationLoader.STUB;
                stage = new GameStage(configuration, new ScreenViewport());
                actor = new PauseActor(stage.getContext());
                stage.addActor(actor);
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
    public void tearDown() {
        app.stop();
    }

    /**
     * Test if pressing the resume causes the actor to resume.
     */
    @Test
    public void testEscape() throws Exception {
        app.postRunnable(() -> {
            actor.setPaused(true);
            actor.getChildren().first().fire(new ChangeListener.ChangeEvent());
        });
        Thread.sleep(2000);
        assertThat(actor.isPaused()).isFalse();
    }
}
