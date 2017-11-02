package nl.tudelft.broccoli.libgdx.scene.game.receptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.level.AbstractGameSession;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.core.powerup.bonus.BonusPowerUp;
import nl.tudelft.broccoli.core.powerup.joker.JokerPowerUp;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import nl.tudelft.broccoli.libgdx.scene.game.RegularMarbleActor;
import nl.tudelft.broccoli.libgdx.scene.ui.screen.GameScreen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link ReceptorActor} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ReceptorActorTest {
    /**
     * The receptor actor under test.
     */
    private ReceptorActor actor;

    /**
     * The receptor to test.
     */
    private Receptor receptor;

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
        receptor = new Receptor();

        Configuration configuration = ConfigurationLoader.STUB;
        GameSession session = new AbstractGameSession(configuration, 3, 2) {
            {
                NexusContext nexusContext = new NexusContext(new ArrayDeque<>(), new Random(), 0);
                getGrid().place(0, 1, new Nexus(nexusContext));
                getGrid().place(1, 1, new Nexus(nexusContext));
                getGrid().place(2, 1, new SpawningNexus(nexusContext, Direction.RIGHT));
                getGrid().place(0, 0, receptor);
                getGrid().place(1, 0, new HorizontalTrack());
                getGrid().place(2, 0, new Receptor());
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
                context = new ActorContext(ConfigurationLoader.STUB, new TextureAtlas(
                    Gdx.files.classpath("atlas/sprites.atlas")));
                stage.addActor(new GameScreen(context, session));
                actor = (ReceptorActor) context.actor(receptor);
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
     * Test that the receptor correctly rotates.
     */
    @Test
    public void rotateOnClick() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        receptor.accept(Direction.LEFT, new Marble(MarbleType.BLUE));

        // Fire escape button press event
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.touchDown);

        app.postRunnable(() -> actor.fire(event));
        app.postRunnable(() -> latch.countDown());

        // Wait until the event is fired
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait until the animation is finished
        Thread.sleep(500);

        assertThat(receptor.getRotation()).isEqualTo(1);
    }

    /**
     * Test that the receptor fails to release a ball.
     */
    @Test
    public void releaseFailure() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(2);

        Marble marble = new Marble(MarbleType.BLUE);

        // Place marble in receptor
        app.postRunnable(() -> {
            new RegularMarbleActor(marble, context);
            receptor.accept(Direction.LEFT, marble);
            latch.countDown();
        });

        // Try to release marble
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.touchDown);
        app.postRunnable(() -> {
            Actor marbleActor = context.actor(marble);
            marbleActor.fire(event);
            latch.countDown();
        });

        // Wait until the event is fired
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait until the event is processed
        Thread.sleep(200);

        assertThat(receptor.getSlot(Direction.LEFT).isOccupied()).isTrue();
    }

    /**
     * Test that the receptor succeeds to release a ball.
     */
    @Test
    public void releaseSuccess() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(2);

        Marble marble = new Marble(MarbleType.BLUE);

        // Place marble in receptor
        app.postRunnable(() -> {
            new RegularMarbleActor(marble, context);
            receptor.accept(Direction.RIGHT, marble);
            latch.countDown();
        });

        // Try to release marble
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.touchDown);
        app.postRunnable(() -> {
            Actor marbleActor = context.actor(marble);
            marbleActor.fire(event);
            latch.countDown();
        });

        // Wait until the event is fired
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Wait until the event is processed
        Thread.sleep(200);

        assertThat(receptor.getSlot(Direction.RIGHT).isOccupied()).isFalse();
    }

    /**
     * Test that disposing an unknown marble does not cause a crash.
     */
    @Test
    public void disposeUnknown() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        assertThatCode(() ->
            actor.ballDisposed(receptor, Direction.LEFT, new Marble(MarbleType.BLUE))
        ).doesNotThrowAnyException();
    }

    /**
     * Test that joker power-up.
     */
    @Test
    public void testJokerPowerUp() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        assertThatCode(() ->
            receptor.setPowerUp(new JokerPowerUp())
        ).doesNotThrowAnyException();

        Thread.sleep(500);
    }


    /**
     * Test that bonus power-up.
     */
    @Test
    public void testBonusPowerUp() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        assertThatCode(() ->
            receptor.setPowerUp(new BonusPowerUp())
        ).doesNotThrowAnyException();

        Thread.sleep(500);
    }
}
