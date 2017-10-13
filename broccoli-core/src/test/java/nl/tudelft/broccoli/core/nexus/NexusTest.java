package nl.tudelft.broccoli.core.nexus;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.grid.Direction;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Nexus} class.
 */
public class NexusTest {
    /**
     * The object under test.
     */
    private Nexus nexus;

    /**
     * The context of the nexus.
     */
    private NexusContext context;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        context = new NexusContext();
        nexus = new Nexus(context);
    }

    @Test
    public void allowsConnectionLeft() {
        assertThat(nexus.allowsConnection(Direction.LEFT)).isTrue();
    }

    @Test
    public void allowsConnectionRight() {
        assertThat(nexus.allowsConnection(Direction.RIGHT)).isTrue();
    }

    @Test
    public void allowsConnectionBottom() {
        assertThat(nexus.allowsConnection(Direction.BOTTOM)).isTrue();
    }

    @Test
    public void disallowsConnectionTop() {
        assertThat(nexus.allowsConnection(Direction.TOP)).isFalse();
    }


    @Test
    public void acceptsLeft() {
        assertThat(nexus.accepts(Direction.LEFT)).isTrue();
    }

    @Test
    public void acceptsRight() {
        assertThat(nexus.accepts(Direction.RIGHT)).isTrue();
    }

    @Test
    public void notAcceptsTop() {
        assertThat(nexus.accepts(Direction.TOP)).isFalse();
    }

    @Test
    public void notAcceptsBottom() {
        assertThat(nexus.accepts(Direction.BOTTOM)).isFalse();
    }

    @Test
    public void getContext() {
        assertThat(nexus.getContext()).isEqualTo(context);
    }

}
