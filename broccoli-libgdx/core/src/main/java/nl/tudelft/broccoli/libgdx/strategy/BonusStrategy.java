package nl.tudelft.broccoli.libgdx.strategy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Strategy that will make the receptor red if the BonusPowerUp is assigned to the receptor.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class BonusStrategy implements PowerUpStrategy {

    /**
     * Method that will show the animation if a power-up is on the receptor.
     */
    @Override
    public Action animate() {
        return Actions.forever(Actions.sequence(
                Actions.color(Color.RED, 0.5f, Interpolation.fade),
                Actions.color(Color.WHITE, 0.5f, Interpolation.fade)
        ));
    }
}
