package nl.tudelft.broccoli.libgdx.scene.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.level.AbstractGameSession;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.track.FilterTrack;
import nl.tudelft.broccoli.core.track.OneWayTrack;
import nl.tudelft.broccoli.core.track.VerticalTrack;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link TrackActor} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class TrackActorTest {
    /**
     * The grid under test.
     */
    private Grid grid;

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

        Configuration configuration = ConfigurationLoader.STUB;
        GameSession session = new AbstractGameSession(configuration, 2, 2) {
            {
                getGrid().place(0, 0, new VerticalTrack());
                getGrid().place(0, 1, new FilterTrack(new VerticalTrack(), MarbleType.BLUE));
                getGrid().place(1, 1, new VerticalTrack());
                getGrid().place(1, 0, new OneWayTrack(new VerticalTrack(), Direction.BOTTOM));
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
                grid = session.getGrid();
                GridActor gridActor = new GridActor(context, grid);
                gridActor.setFillParent(true);
                stage.addActor(gridActor);

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
     * Test whether a ball bounces from a one directional track.
     */
    @Test
    public void testOneWayBounce() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(2);

        Marble marble = new Marble(MarbleType.BLUE);
        context.register(marble, new MarbleActor(marble, context));

        OneWayTrack track = (OneWayTrack) grid.get(1, 0).getTileable();
        TrackActor actor = (TrackActor) context.actor(track);
        Direction direction = track.getDirection().inverse();
        TileableListener listener = spy(new TileableListener() {
            @Override
            public void ballReleased(Tileable tileable, Direction direction, Marble marble) {
                latch.countDown();
            }
        });
        track.addListener(listener);
        app.postRunnable(() -> {
            actor.addActor(context.actor(marble));
            track.accept(direction, marble);
            latch.countDown();
        });

        // Wait for the ball to be spawned.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait for the events to be processed
        Thread.sleep(200);

        verify(listener, atLeast(1)).ballReleased(any(), eq(direction), any());
    }

    /**
     * Test whether a ball bounces from a one directional track.
     */
    @Test
    public void testStuck() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        Marble marble = new Marble(MarbleType.GREEN);
        context.register(marble, new MarbleActor(marble, context));

        FilterTrack track = (FilterTrack) grid.get(0, 1).getTileable();
        TrackActor actor = (TrackActor) context.actor(track);
        Direction direction = Direction.TOP;
        TileableListener listener = mock(TileableListener.class);
        track.addListener(listener);
        app.postRunnable(() -> {
            actor.addActor(context.actor(marble));
            track.accept(direction, marble);
            latch.countDown();
        });

        // Wait for the ball to be spawned.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait for the events to be processed
        Thread.sleep(500);

        verify(listener, never()).ballReleased(eq(track), any(), eq(marble));
    }

    /**
     * Test whether a ball bounces from a filter track.
     */
    @Test
    public void testFilterBounce() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(2);

        Marble marble = new Marble(MarbleType.GREEN);
        context.register(marble, new MarbleActor(marble, context));

        FilterTrack track = (FilterTrack) grid.get(0, 1).getTileable();
        TrackActor actor = (TrackActor) context.actor(track);
        Direction direction = Direction.BOTTOM;
        TileableListener listener = spy(new TileableListener() {
            @Override
            public void ballReleased(Tileable tileable, Direction direction, Marble marble) {
                latch.countDown();
            }
        });
        track.addListener(listener);
        app.postRunnable(() -> {
            actor.addActor(context.actor(marble));
            track.accept(direction, marble);
            latch.countDown();
        });

        // Wait for the ball to be spawned.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait for the events to be processed
        Thread.sleep(200);

        verify(listener, atLeast(1)).ballReleased(any(), eq(direction), any());
    }
}
