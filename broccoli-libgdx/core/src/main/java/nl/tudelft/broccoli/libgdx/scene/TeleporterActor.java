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

package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.Teleporter;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.Track;
import nl.tudelft.broccoli.libgdx.Context;

/**
 * An {@link Actor} for a track on the grid.
 *
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class TeleporterActor extends TransportingActor<Teleporter> implements TileableListener {
    /**
     * Construct a {@link TeleporterActor} instance.
     *
     * @param teleporter The teleporter entity to create the actor for.
     * @param context  The game context of this actor.
     */
    public TeleporterActor(Teleporter teleporter, Context context) {
        super(teleporter, context);
    }

    /**
     * Initialize the modifier for this track.
     *
     * @return The modifier of this track.
     */
    protected Image getModifier() {
        int index = 0;
        if (getTileable().getTrack() instanceof HorizontalTrack) {
            index = 1;
        }

        Image image = new Image(getContext().getTextureAtlas().findRegion("teleporter", index));
        image.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        return image;
    }


    /**
     * This method is invoked when a {@link Tileable} has accepted a marble.
     *
     * @param tileable  The tileable that has accepted the marble.
     * @param direction The direction from which the marble was accepted.
     * @param marble    The marble that has been accepted.
     */
    @Override
    public void ballAccepted(Tileable tileable, Direction direction, Marble marble) {
        MarbleActor actor = (MarbleActor) getContext().actor(marble);

        // This objects needs to be unaltered for the animation. Therefore it is stored here and not
        // retrieved from the actor.
        Vector2 origin = stageToLocalCoordinates(actor.localToStageCoordinates(
                new Vector2(actor.getWidth() / 2.f, actor.getHeight() / 2.f)));

        prepareActor(actor, direction, origin);
        travelToMidAnimation(actor, direction, origin);
        addActorBefore(modifier, actor);
    }

    /**
     * This method will start the animation of an allowed {@link Marble} traveling over a
     * {@link Track}.
     *
     * @param actor     The {@link MarbleActor} of the traveling {@link Marble}.
     * @param direction The {@link Direction} in which the {@link Marble} is traveling.
     * @param origin    The place at which the marble started.
     */
    private void travelToMidAnimation(MarbleActor actor, Direction direction, Vector2 origin) {
        Vector2 target = getCenter();
        actor.addAction(Actions.sequence(
                Actions.moveToAligned(target.x, target.y, Align.center, origin.dst(target)
                        * TRAVEL_TIME),
                Actions.run(() -> teleport(actor, direction))
        ));
    }

    /**
     * This method is called when a marble is teleported to this teleporter.
     *
     * @param actor the marble actor.
     * @param direction The direction the ball is moving before and after the teleportation.
     */
    public void acceptTeleportation(MarbleActor actor, Direction direction) {
        Vector2 target = getTarget(direction);
        actor.setPosition(getCenter().x, getCenter().y, Align.center);
        actor.addAction(Actions.sequence(
                Actions.moveToAligned(target.x, target.y, Align.center, getCenter().dst(target)
                        * TRAVEL_TIME),
                Actions.run(() -> {
                    Direction inverse = direction.inverse();
                    if (!getTileable().isReleasable(inverse, actor.getMarble())) {
                        BOUNCE.play();
                        ballAccepted(getTileable(), inverse, actor.getMarble());
                        return;
                    }

                    getTileable().release(inverse, actor.getMarble());
                })
        ));
        addActorBefore(modifier, actor);
    }

    /**
     * A method called to teleport the marble from this teleporter to another.
     *
     * @param actor The actor of the marble
     * @param direction The direction the ball is moving before and after the teleportation.
     */
    private void teleport(MarbleActor actor, Direction direction) {
        TeleporterActor target = (TeleporterActor) getContext().actor(
            getTileable().getDestination());
        target.acceptTeleportation(actor, direction);
        removeActor(actor);
    }
}
