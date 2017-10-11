package nl.tudelft.broccoli.core.nexus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import org.junit.Before;
import org.junit.Test;

import java.util.Queue;
import java.util.Random;

/**
 * Test suite for the {@link SpawningNexus} class.
 */
public class SpawningNexusTest {
    /**
     * The nexus under test.
     */
    private SpawningNexus nexus;

    /**
     * The nexus context.
     */
    private NexusContext context;

    /**
     * The direction at which the balls are spawned.
     */
    private Direction direction;

    /**
     * The random instance.
     */
    private Random random;

    /**
     * The probability of the joker occurring.
     */
    private double jokerProbability;

    /**
     * The queue of initial balls.
     */
    private Queue<Marble.Type> initial;

    /**
     * Setup the test suite.
     */
    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        context = new NexusContext();
        direction = Direction.RIGHT;
        random = mock(Random.class);
        jokerProbability = 0.5;
        initial = (Queue<Marble.Type>) mock(Queue.class);
        nexus = new SpawningNexus(context, random, direction, jokerProbability, initial);

        when(initial.isEmpty()).thenReturn(true);
        when(initial.poll()).thenReturn(null);
    }

    /**
     * Test if the spawn method fails if the nexus is occupied.
     */
    @Test
    public void spawnFailsOnOccupation() {
        context.setOccupied(true);
        assertThatThrownBy(() -> nexus.spawn()).isInstanceOf(IllegalStateException.class);
    }

    /**
     * Test if the spawn method initially returns the initial sequence.
     */
    @Test
    public void spawnInitialSequence() {
        when(initial.isEmpty()).thenReturn(false);
        when(initial.poll()).thenReturn(Marble.Type.JOKER);
        assertThat(nexus.spawn().getType()).isEqualTo(Marble.Type.JOKER);
    }

    /**
     * Test if the spawn method successfully spawns a joker.
     */
    @Test
    public void spawnJoker() {
        when(random.nextDouble()).thenReturn(0.4);
        assertThat(nexus.spawn().getType()).isEqualTo(Marble.Type.JOKER);
    }

    /**
     * Test if the spawn method successfully spawns a pink marble.
     */
    @Test
    public void spawnPink() {
        when(random.nextInt(anyInt())).thenReturn(0);
        when(random.nextDouble()).thenReturn(0.6);
        assertThat(nexus.spawn().getType()).isEqualTo(Marble.Type.PINK);
    }

    /**
     * Test if the spawn method successfully spawns a green marble.
     */
    @Test
    public void spawnGreen() {
        when(random.nextDouble()).thenReturn(0.6);
        when(random.nextInt(anyInt())).thenReturn(1);
        assertThat(nexus.spawn().getType()).isEqualTo(Marble.Type.GREEN);
    }

    /**
     * Test if the spawn method successfully spawns a blue marble.
     */
    @Test
    public void spawnBlue() {
        when(random.nextDouble()).thenReturn(0.6);
        when(random.nextInt(anyInt())).thenReturn(2);
        assertThat(nexus.spawn().getType()).isEqualTo(Marble.Type.BLUE);
    }

    /**
     * Test if the spawn method successfully spawns a blue marble.
     */
    @Test
    public void spawnYellow() {
        when(random.nextDouble()).thenReturn(0.6);
        when(random.nextInt(anyInt())).thenReturn(3);
        assertThat(nexus.spawn().getType()).isEqualTo(Marble.Type.YELLOW);
    }

    /**
     * Test if the class correctly returns the random instance.
     */
    @Test
    public void getRandom() {
        assertThat(nexus.getRandom()).isEqualTo(random);
    }

    /**
     * Test if the class correctly returns the direction at which the balls are spawned.
     */
    @Test
    public void getDirection() {
        assertThat(nexus.getDirection()).isEqualTo(direction);
    }
}
