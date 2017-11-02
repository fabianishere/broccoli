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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.MarbleType;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An {@link Actor} that represents an in-game marble.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public abstract class MarbleActor extends Group {
    /**
     * Get the {@link MarbleActor} for the given {@link Marble} given the {@link ActorContext}.
     *
     * @param marble The marble to get the actor for.
     * @param context The context to use when creating the actor.
     * @return The corresponding actor.
     */
    public static MarbleActor get(Marble marble, ActorContext context) {
        MarbleActor registry = (MarbleActor) context.actor(marble);

        if (registry == null) {
            if (MarbleType.JOKER.equals(marble.getType())) {
                registry = new JokerMarbleActor(marble, context);
            } else {
                registry = new RegularMarbleActor(marble, context);

            }
        }
        return registry;
    }

    /**
     * Return the {@link Marble} of this actor.
     *
     * @return The marble of this actor.
     */
    public abstract Marble getMarble();

    /**
     * Set the {@link Direction} in which the {@link Marble} is traveling.
     *
     * @param direction The direction in which the marble is traveling.
     */
    public abstract void setDirection(Direction direction);

    /**
     * Set the flag whether the {@link Marble} is moving or not.
     *
     * @param moving A flag indicate the moving of the marble.
     */
    public abstract void setMoving(boolean moving);
}
