package nl.tudelft.broccoli.core.level;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link LevelFactory} class.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 */
public class LevelFactoryTest {
    /**
     * The object under test.
     */
    private LevelFactory factory;

    /**
     * Set up the test suite.
     */
    @Before
    public void setUp() {
        factory = new LevelFactory();
    }

    /**
     * Test whether creating a level with easy difficulty succeeds.
     */
    @Test
    public void getEasy() {
        assertThat(factory.create(Difficulty.EASY)).isInstanceOf(EasyLevel.class);
    }

    /**
     * Test whether creating a level with medium difficulty succeeds.
     */
    @Test
    public void getMedium() {
        assertThat(factory.create(Difficulty.MEDIUM)).isInstanceOf(MediumLevel.class);
    }

    /**
     * Test whether creating a level with hard difficulty succeeds.
     */
    @Test
    public void getHard() {
        assertThat(factory.create(Difficulty.HARD)).isInstanceOf(HardLevel.class);
    }
}
