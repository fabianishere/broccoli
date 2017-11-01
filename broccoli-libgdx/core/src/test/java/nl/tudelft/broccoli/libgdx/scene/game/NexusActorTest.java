package nl.tudelft.broccoli.libgdx.scene.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.level.AbstractGameSession;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link NexusActor} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class NexusActorTest {
    /**
     * The nexus actor under test.
     */
    private NexusActor actor;

    /**
     * The nexus to test.
     */
    private SpawningNexus nexus;

    /**
     * The application that is used for testing.
     */
    private LwjglApplication app;

    /**
     * Signal used to indicate the actor is initialised.
     */
    private CountDownLatch latch;

    /**
     * The actor context to use.
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
        NexusContext nexusContext = new NexusContext(new ArrayDeque<>(), new Random(), 0);
        nexus = new SpawningNexus(nexusContext, Direction.LEFT);

        Configuration configuration = ConfigurationLoader.STUB;
        GameSession session = new AbstractGameSession(configuration, 3, 2) {
            {
                getGrid().place(0, 1, new Nexus(nexusContext));
                getGrid().place(1, 1, nexus);
                getGrid().place(2, 1, new Nexus(nexusContext));
                getGrid().place(0, 0, new Tileable() {
                    @Override
                    public boolean allowsConnection(Direction direction) {
                        return false;
                    }

                    @Override
                    public boolean accepts(Direction direction, Marble marble) {
                        return false;
                    }

                    @Override
                    public void accept(Direction direction, Marble marble) {}
                });
            }

            @Override
            public Level getLevel() {
                return null;
            }
        };

        Game game = new Game() {
            private Stage stage;

            @Override
            public void create() {
                stage = new Stage(new ScreenViewport());
                context = new ActorContext(
                    new TextureAtlas(Gdx.files.classpath("atlas/sprites.atlas")));
                GridActor grid = new GridActor(context, session.getGrid());
                grid.setFillParent(true);
                stage.addActor(grid);
                actor = (NexusActor) context.actor(nexus);
                latch.countDown();
            }

            @Override
            public void resize(int width, int height) {
                stage.getViewport().update(width, height, true);
            }

            @Override
            public void render() {
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                stage.act(Gdx.graphics.getDeltaTime() * 100);
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
     * Test whether a ball is spawned by the nexus.
     */
    @Test
    public void testSpawn() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(2);

        TileableListener listener = spy(new TileableListener() {
            @Override
            public void ballAccepted(Tileable tileable, Direction direction, Marble marble) {
                latch.countDown();
            }
        });
        nexus.addListener(listener);

        // Wait for the nexus to spawn the ball.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        verify(listener, atLeast(2)).ballAccepted(any(), any(), any());
    }


    /**
     * Test whether a ball is spawned from an invalid direction crashes.
     */
    @Test
    public void testInvalidDirection() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        assertThatThrownBy(() -> actor.ballAccepted(nexus, Direction.TOP,
            new Marble(MarbleType.BLUE))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
