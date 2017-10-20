package nl.tudelft.broccoli.core.nexus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.grid.Direction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
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
     * The queue of initial balls.
     */
    private Queue<MarbleType> initial;

    /**
     * Setup the test suite.
     */
    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        direction = Direction.RIGHT;
        random = mock(Random.class);

        initial = new ArrayDeque<>();
        initial.add(MarbleType.BLUE);
        context = new NexusContext(initial, random, 0.5);
        nexus = new SpawningNexus(context, direction);
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
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.BLUE);
    }

    /**
     * Test if we can peek into the initial sequence.
     */
    @Test
    public void peekInitialSequence() {
        assertThat(context.peek()).isEqualTo(MarbleType.BLUE);
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.BLUE);
    }

    /**
     * Test if the spawn method successfully spawns a joker.
     */
    @Test
    public void spawnJoker() {
        when(random.nextDouble()).thenReturn(0.4);
        context.poll();
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.JOKER);
    }

    /**
     * Test if the spawn method successfully spawns a pink marble.
     */
    @Test
    public void spawnPink() {
        when(random.nextInt(anyInt())).thenReturn(0);
        when(random.nextDouble()).thenReturn(0.6);
        context.poll();
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.PINK);
    }

    /**
     * Test if the spawn method successfully spawns a green marble.
     */
    @Test
    public void spawnGreen() {
        when(random.nextDouble()).thenReturn(0.6);
        when(random.nextInt(anyInt())).thenReturn(1);
        context.poll();
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.GREEN);
    }

    /**
     * Test if the spawn method successfully spawns a blue marble.
     */
    @Test
    public void spawnBlue() {
        when(random.nextDouble()).thenReturn(0.6);
        when(random.nextInt(anyInt())).thenReturn(2);
        context.poll();
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.BLUE);
    }

    /**
     * Test if the spawn method successfully spawns a blue marble.
     */
    @Test
    public void spawnYellow() {
        when(random.nextDouble()).thenReturn(0.6);
        when(random.nextInt(anyInt())).thenReturn(3);
        context.poll();
        assertThat(nexus.spawn().getType()).isEqualTo(MarbleType.YELLOW);
    }

    /**
     * Test if the class correctly returns the direction at which the balls are spawned.
     */
    @Test
    public void getDirection() {
        assertThat(nexus.getDirection()).isEqualTo(direction);
    }
}
