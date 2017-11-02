package nl.tudelft.broccoli.libgdx.scene.util;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.audio.Music;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link MusicActor} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class MusicActorTest {
    /**
     * The actor under test.
     */
    private MusicActor actor;

    /**
     * Set up the test suite.
     */
    @Before
    public void setUp() {
        actor = new MusicActor();
    }

    /**
     * Test if the actor successfully plays the given theme.
     */
    @Test
    public void testPlay() {
        Music theme = mock(Music.class);
        actor.play(theme);

        verify(theme, times(1)).play();
        verify(theme, times(1)).setLooping(true);
    }

    /**
     * Test if the actor successfully plays the second theme.
     */
    @Test
    public void testPlaySecond() {
        Music first = mock(Music.class);
        Music second = mock(Music.class);
        actor.play(first);
        reset(first);
        actor.play(second);

        verify(first, times(1)).stop();
        verify(second, times(1)).play();
        verify(second, times(1)).setLooping(true);
    }

}
