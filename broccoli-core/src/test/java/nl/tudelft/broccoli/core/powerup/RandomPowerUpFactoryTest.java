package nl.tudelft.broccoli.core.powerup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.broccoli.core.powerup.bonus.BonusPowerUp;
import nl.tudelft.broccoli.core.powerup.bonus.BonusPowerUpFactory;
import nl.tudelft.broccoli.core.powerup.joker.JokerPowerUp;
import nl.tudelft.broccoli.core.powerup.joker.JokerPowerUpFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Test suite for the {@link RandomPowerUpFactory} class.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class RandomPowerUpFactoryTest {
    /**
     * The random instance.
     */
    private Random random;

    /**
     * Set-up the test suite.
     */
    @Before
    public void setUp() {
        random = mock(Random.class);
    }

    /**
     * Test the creation of a random {@link PowerUp}.
     */
    @Test
    public void createA() {
        NavigableMap<Double, PowerUpFactory> cdf = new TreeMap<>();
        cdf.put(0.0, new BonusPowerUpFactory());
        cdf.put(0.6, new JokerPowerUpFactory());
        PowerUpFactory factory = new RandomPowerUpFactory(random, cdf);
        when(random.nextDouble()).thenReturn(0.4);
        assertThat(factory.create()).isInstanceOf(BonusPowerUp.class);
    }

    /**
     * Test the creation of a random {@link PowerUp}.
     */
    @Test
    public void createB() {
        NavigableMap<Double, PowerUpFactory> cdf = new TreeMap<>();
        cdf.put(0.0, new BonusPowerUpFactory());
        cdf.put(0.6, new JokerPowerUpFactory());
        PowerUpFactory factory = new RandomPowerUpFactory(random, cdf);
        when(random.nextDouble()).thenReturn(0.7);
        assertThat(factory.create()).isInstanceOf(JokerPowerUp.class);
    }

}
