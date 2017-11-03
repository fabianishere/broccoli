package nl.tudelft.broccoli.core.powerup.joker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Progress;
import nl.tudelft.broccoli.core.nexus.NexusContext;
import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.powerup.PowerUpFactory;
import nl.tudelft.broccoli.core.receptor.Receptor;
import org.junit.Before;
import org.junit.Test;

/**
 * A test suite for the {@link JokerPowerUp} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class JokerPowerUpTest {
    /**
     * The receptor under test.
     */
    private Receptor receptor;

    /**
     * The power-up to test.
     */
    private PowerUp powerUp;

    /**
     * The {@link NexusContext} mock to use.
     */
    private NexusContext context;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        context = mock(NexusContext.class);
        GameSession session = mock(GameSession.class);
        when(session.getProgress()).thenReturn(new Progress());
        when(session.getNexusContext()).thenReturn(context);
        Grid grid = new Grid(session, 1, 1);
        receptor = new Receptor();
        grid.place(0, 0, receptor);
        powerUp = new JokerPowerUp();
    }


    /**
     * Test the {@link JokerPowerUpFactory} class.
     */
    @Test
    public void factory() {
        PowerUpFactory factory = new JokerPowerUpFactory();
        assertThat(factory.create()).isInstanceOf(JokerPowerUp.class);
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
        verify(context).add(MarbleType.JOKER, MarbleType.JOKER);
    }
}
