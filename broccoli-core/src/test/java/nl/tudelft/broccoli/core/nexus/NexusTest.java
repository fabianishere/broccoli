package nl.tudelft.broccoli.core.nexus;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
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
     * The marble to use.
     */
    private Marble marble;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        context = new NexusContext();
        nexus = new Nexus(context);
        marble = new Marble(MarbleType.GREEN);
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
        assertThat(nexus.accepts(Direction.LEFT, marble)).isTrue();
    }

    @Test
    public void acceptsRight() {
        assertThat(nexus.accepts(Direction.RIGHT, marble)).isTrue();
    }

    @Test
    public void notAcceptsTop() {
        assertThat(nexus.accepts(Direction.TOP, marble)).isFalse();
    }

    @Test
    public void notAcceptsBottom() {
        assertThat(nexus.accepts(Direction.BOTTOM, marble)).isFalse();
    }

    @Test
    public void getContext() {
        assertThat(nexus.getContext()).isEqualTo(context);
    }

}
