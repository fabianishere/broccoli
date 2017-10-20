package nl.tudelft.broccoli.core.powerup.bonus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Progress;
import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.powerup.PowerUpFactory;
import nl.tudelft.broccoli.core.receptor.Receptor;
import org.junit.Before;
import org.junit.Test;

/**
 * A test suite for the {@link BonusPowerUp} class.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class BonusPowerUpTest {
    /**
     * The receptor under test.
     */
    private Receptor receptor;

    /**
     * The power-up to test.
     */
    private PowerUp powerUp;

    /**
     * The {@link GameSession} mock to use.
     */
    private GameSession session;

    /**
     * The progress to keep the score.
     */
    private Progress progress;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        session = mock(GameSession.class);
        Grid grid = new Grid(session, 1, 1);
        progress = new Progress(grid);
        when(session.getProgress()).thenReturn(progress);
        receptor = new Receptor();
        grid.place(0, 0, receptor);
        powerUp = new BonusPowerUp();
    }

    /**
     * Test the {@link BonusPowerUpFactory} class.
     */
    @Test
    public void factory() {
        PowerUpFactory factory = new BonusPowerUpFactory();
        assertThat(factory.create()).isInstanceOf(BonusPowerUp.class);
    }

    /**
     * Test whether the power-up correctly activates.
     */
    @Test
    public void activate() {
        receptor.setPowerUp(powerUp);
        for (Direction direction : Direction.values()) {
            receptor.accept(direction, new Marble(MarbleType.BLUE));
        }
        assertThat(session.getProgress().getScore()).isEqualTo(200);
    }
}
