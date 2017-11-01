package nl.tudelft.broccoli.core.level;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link LevelFactory} class.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 */
public abstract class LevelFactoryTest {
    /**
     * The object under test.
     */
    protected LevelFactory factory;

    /**
     * Return the {@link LevelFactory} instance to test.
     *
     * @return The level factory to test.
     */
    protected abstract LevelFactory createFactory();

    /**
     * Set up the test suite.
     */
    @Before
    public void setUp() {
        factory = createFactory();
    }

    /**
     * Test whether we can get the first level.
     */
    @Test
    public void getFirst() {
        assertThat(factory.create(1)).isNotNull();
    }


    /**
     * Test whether we can get the second level.
     */
    @Test
    public void getSecond() {
        assertThat(factory.create(2)).isNotNull();
    }

    /**
     * Test whether we can get the third level.
     */
    @Test
    public void getThird() {
        assertThat(factory.create(3)).isNotNull();
    }

    /**
     * Test whether we cannot get the fourth level.
     */
    @Test
    public void getFourth() {
        assertThat(factory.create(4)).isNull();
    }
}
