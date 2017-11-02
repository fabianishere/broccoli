/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Fabian Mastenbroek, Christian Slothouber,
 * Earth Grob, Matthijs Rijm, Bas Musters
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nl.tudelft.broccoli.libgdx.scene.game;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;


/**
 * An {@link Actor} that represents an in-game joker marble.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class JokerMarbleActor extends MarbleActor {
    /**
     * The duration of each marble animation.
     */
    private static final float ANIMATION_DURATION = 0.5f;

    /**
     * The marble of this actor.
     */
    private final Marble marble;

    /**
     * Construct a {@link JokerMarbleActor} instance.
     *
     * @param marble The marble to create this actor for.
     * @param context The context of the actor.
     */
    public JokerMarbleActor(Marble marble, ActorContext context) {
        this.marble = marble;
        context.register(marble, this);

        MarbleActor blue = new RegularMarbleActor(new Marble(MarbleType.BLUE), context);
        this.addActor(blue);

        MarbleActor green = new RegularMarbleActor(new Marble(MarbleType.GREEN), context);
        green.addAction(Actions.alpha(0));
        this.addActor(green);

        MarbleActor pink = new RegularMarbleActor(new Marble(MarbleType.PINK), context);
        pink.addAction(Actions.alpha(0));
        this.addActor(pink);


        MarbleActor yellow = new RegularMarbleActor(new Marble(MarbleType.YELLOW), context);
        yellow.addAction(Actions.alpha(0));
        this.addActor(yellow);

        float duration = ANIMATION_DURATION;
        this.addAction(Actions.forever(Actions.sequence(
            Actions.parallel(
                fadeOut(duration, blue),
                fadeIn(duration, green)
            ),
            Actions.parallel(
                fadeOut(duration, green),
                fadeIn(duration, pink)
            ),
            Actions.parallel(
                fadeOut(duration, pink),
                fadeIn(duration, yellow)
            ),
            Actions.parallel(
                fadeOut(duration, yellow),
                fadeIn(duration, blue)
            )
        )));

        this.setSize(blue.getWidth(), blue.getHeight());
        this.setUserObject(marble);
        this.setOrigin(Align.center);
        this.setDirection(Direction.LEFT);
    }

    /**
     * Generate a fading out action with the given duration for the given marble actor.
     *
     * @param duration The duration of the fade out.
     * @param marble The marble to fade.
     * @return The fading action.
     */
    private static Action fadeOut(float duration, Actor marble) {
        return alpha(0, duration, marble);
    }

    /**
     * Generate a fading in action with the given duration for the given marble actor.
     *
     * @param duration The duration of the fade in.
     * @param marble The marble to fade.
     * @return The fading action.
     */
    private static Action fadeIn(float duration, Actor marble) {
        return alpha(1, duration, marble);
    }

    /**
     * Generate an alpha fading animation action with the given alpha value target, a duration
     * and the target actor.
     *
     * @param alpha The alpha to animate to.
     * @param duration The duration of the animation.
     * @return The alpha animation action.
     */
    private static Action alpha(float alpha, float duration, Actor marble) {
        return new AlphaAction() {
            @Override
            protected void begin() {
                this.setTarget(marble);
                this.setAlpha(alpha);
                this.setDuration(duration);

                super.begin();
            }
        };
    }

    /**
     * Return the {@link Marble} of this actor.
     *
     * @return The marble of this actor.
     */
    @Override
    public Marble getMarble() {
        return marble;
    }

    /**
     * Set the {@link Direction} in which the {@link Marble} is traveling.
     *
     * @param direction The direction in which the marble is traveling.
     */
    @Override
    public void setDirection(Direction direction) {
        for (Actor actor : getChildren()) {
            if (actor instanceof MarbleActor) {
                ((MarbleActor) actor).setDirection(direction);
            }
        }
    }

    /**
     * Set the flag whether the {@link Marble} is moving or not.
     *
     * @param moving A flag indicate the moving of the marble.
     */
    @Override
    public void setMoving(boolean moving) {
        for (Actor actor : getChildren()) {
            if (actor instanceof MarbleActor) {
                ((MarbleActor) actor).setMoving(moving);
            }
        }
    }
}
