package nl.tudelft.broccoli.core.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the {@link Progress} class.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class ProgressTest {
    /**
     * The {@link Progress} object under test.
     */
    private Progress progress;

    /**
     * The receptor to test with.
     */
    private Receptor receptor;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        GameSession gameSession = mock(GameSession.class);
        Grid grid = new Grid(gameSession, 1, 2);
        receptor = new Receptor();
        HorizontalTrack horTrack = new HorizontalTrack();
        grid.place(0, 0, receptor);
        grid.place(0, 1, horTrack);

        this.progress = new Progress();
        this.progress.track(grid);
        when(gameSession.getProgress()).thenReturn(progress);
    }

    @Test
    public void testWonFalse() {
        assertThat(progress.isWon()).isFalse();
    }

    @Test
    public void testWonTrue() {
        progress.receptorMarked(receptor);
        assertThat(progress.isWon()).isTrue();
    }

    @Test
    public void addPoints() {
        progress.setScore(0);
        progress.score(100);
        assertThat(progress.getScore()).isEqualTo(100);
    }

    @Test
    public void setPoints() {
        progress.setScore(1000);
        assertThat(progress.getScore()).isEqualTo(1000);
    }
}
