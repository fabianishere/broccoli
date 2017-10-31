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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.FilterTrack;
import nl.tudelft.broccoli.core.track.OneWayTrack;
import nl.tudelft.broccoli.core.track.Track;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An {@link Actor} for a track on the grid.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class TrackActor extends TransportingActor<Track> implements TileableListener {
    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context  The game context of this actor.
     */
    public TrackActor(Track tileable, ActorContext context) {
        super(tileable, context);
    }

    /**
     * Initialize the modifier for this track.
     *
     * @return The modifier of this track.
     */
    protected Image getModifier() {
        if (getTileable() instanceof FilterTrack) {
            FilterTrack track = (FilterTrack) getTileable();
            Image image = new Image(getContext().getTextureAtlas()
                    .findRegion("filter/" + track.getMarbleType().toString().toLowerCase()));

            // We use a 3 pixel offset since the image is incorrectly aligned
            image.setPosition(
                    getWidth() / 2 + 3,
                    getHeight() / 2 - 3,
                    Align.center
            );
            return image;
        } else if (getTileable() instanceof OneWayTrack) {
            int index = ((OneWayTrack) getTileable()).getDirection().ordinal();
            Image image = new Image(getContext().getTextureAtlas()
                    .findRegion("direction", index));

            image.setPosition(
                    getWidth() / 2,
                    getHeight() / 2,
                    Align.center
            );
            return image;
        }

        return new Image();
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

        if (getTileable().passesMidpoint(direction, marble)) {
            travelAnimation(actor, direction, origin);
        } else {
            bounceTravelAnimation(actor, direction, origin);
        }

        addActorBefore(modifier, actor);
    }
}
