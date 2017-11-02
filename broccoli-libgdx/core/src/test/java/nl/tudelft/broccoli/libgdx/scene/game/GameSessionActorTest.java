package nl.tudelft.broccoli.libgdx.scene.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import nl.tudelft.broccoli.core.powerup.bonus.BonusPowerUp;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.receptor.ReceptorListener;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import nl.tudelft.broccoli.libgdx.scene.ui.PauseActor;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test suite for the {@link GameSessionActor} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameSessionActorTest {
    /**
     * The game actor under test.
     */
    private GameSessionActor actor;

    /**
     * The application that is used for testing.
     */
    private LwjglApplication app;

    /**
     * Signal used to indicate the actor is initialised.
     */
    private CountDownLatch latch;

    /**
     * The receptor to test for the power-up.
     */
    private Receptor receptor;

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
        receptor = new Receptor();
        receptor.accept(Direction.LEFT, new Marble(MarbleType.BLUE));

        Configuration configuration = ConfigurationLoader.STUB;
        GameSession session = new AbstractGameSession(configuration, 2, 1) {
            {
                getGrid().place(0, 0, receptor);
                getGrid().place(1, 0, new HorizontalTrack());
            }

            @Override
            public Level getLevel() {
                return null;
            }
        };

        Game game = new Game() {
            private Stage stage;
            private ActorContext context;

            @Override
            public void create() {
                stage = new Stage(new ScreenViewport());
                context = new ActorContext(
                    new TextureAtlas(Gdx.files.classpath("atlas/sprites.atlas")));
                actor = new GameSessionActor(context, session);
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
     * Test if pressing the escape button causes the actor to pause.
     */
    @Test
    public void testEscapePause() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Fire escape button press event
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.ESCAPE);
        app.postRunnable(() -> actor.fire(event));

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        assertThat(actor.getChildren().pop()).isInstanceOf(PauseActor.class);
    }

    /**
     * Test if pressing the escape button causes the actor to resume.
     */
    @Test
    public void testEscapeResume() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Fire escape button press event
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.ESCAPE);
        app.postRunnable(() -> {
            actor.fire(event);
            actor.fire(event);
        });

        // Wait 20ms in order for the events to be processed
        Thread.sleep(200);

        assertThat(actor.getChildren().pop()).isNotInstanceOf(PauseActor.class);
    }


    /**
     * Test if pressing the another button while paused does not cause the game to resume.
     */
    @Test
    public void testNotResume() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        // Fire escape button press event
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.ESCAPE);
        app.postRunnable(() -> {
            actor.fire(event);
            event.setKeyCode(Input.Keys.E);
            actor.fire(event);
        });

        // Wait 200ms in order for the events to be processed
        Thread.sleep(200);

        assertThat(actor.getChildren().pop()).isInstanceOf(PauseActor.class);
    }

    /**
     * Test if the power up is shown after some time.
     */
    @Test
    public void testPowerUp() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        ReceptorListener listener = mock(ReceptorListener.class);
        receptor.addListener(listener);

        verify(listener, after(500).atLeastOnce()).receptorAssigned(receptor);
    }

    /**
     * Test if the power up is activated after some time.
     */
    @Test
    public void testPowerUpActivated() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        ReceptorListener spy = spy(new ReceptorListener() {
            @Override
            public void receptorAssigned(Receptor receptor) {
                Marble marble = new Marble(MarbleType.BLUE);
                if (receptor.getPowerUp() != null && receptor.accepts(Direction.BOTTOM, marble)) {
                    latch.countDown();
                    receptor.accept(Direction.BOTTOM, marble);
                }
            }
        });

        receptor.accept(Direction.RIGHT, new Marble(MarbleType.BLUE));
        receptor.accept(Direction.TOP, new Marble(MarbleType.BLUE));
        receptor.addListener(spy);
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        verify(spy, after(200).atLeast(2)).receptorAssigned(receptor);
        verify(spy).receptorMarked(receptor);
    }

    /**
     * Test if the power up is not activated after some time.
     */
    @Test
    public void testPowerUpNotActivated() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(2);

        ReceptorListener listener = spy(new ReceptorListener() {
            @Override
            public void receptorAssigned(Receptor receptor) {
                latch.countDown();
            }
        });

        receptor.accept(Direction.RIGHT, new Marble(MarbleType.BLUE));
        receptor.accept(Direction.TOP, new Marble(MarbleType.BLUE));
        receptor.addListener(listener);

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        verify(listener, never()).receptorMarked(receptor);
    }

    /**
     * Test if the power up is changed after some time.
     */
    @Test
    public void testPowerUpChanged() throws Exception {
        // Wait until the actor becomes available.
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        latch = new CountDownLatch(1);

        receptor.accept(Direction.RIGHT, new Marble(MarbleType.BLUE));
        receptor.accept(Direction.TOP, new Marble(MarbleType.BLUE));

        ReceptorListener spy = spy(new ReceptorListener() {
            @Override
            public void receptorAssigned(Receptor receptor) {
                if (receptor.getPowerUp() != null) {
                    latch.countDown();
                }
            }
        });

        receptor.addListener(spy);
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
        app.postRunnable(() -> receptor.setPowerUp(new BonusPowerUp()));

        verify(spy, after(200).atLeast(2)).receptorAssigned(receptor);
        verify(spy, never()).receptorMarked(receptor);
    }
}
