package nl.tudelft.broccoli.libgdx.strategy;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Abstract class that represents a strategy for showing that a power-up is ready.
 * For every power-up there will be a different strategy, so you know what power-up it is.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public interface PowerUpStrategy {
    /**
     * Method that will show the animation if a power-up is on the receptor.
     */
    Action animate();
}
