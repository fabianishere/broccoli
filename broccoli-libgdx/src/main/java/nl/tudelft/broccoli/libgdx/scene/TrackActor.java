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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.Track;
import nl.tudelft.broccoli.libgdx.Context;

/**
 * An {@link Actor} for a track on the grid.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class TrackActor extends TileableActor<Track> implements TileableListener {
    /**
     * The bounce sound in case a ball is denied.
     */
    private static final Sound BOUNCE =
        Gdx.audio.newSound(Gdx.files.classpath("sound/sfx/bounce.wav"));

    /**
     * The travel time multiplier for travel speed over this track.
     */
    private static final float TRAVEL_TIME = 0.008f;

    /**
     * The sprite for this track.
     */
    private Sprite sprite;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context The game context of this actor.
     */
    public TrackActor(Track tileable, Context context) {
        super(tileable, context);
        tileable.addListener(this);

        if (tileable instanceof HorizontalTrack) {
            sprite = context.getTextureAtlas().createSprite("tiles/horizontal");
        } else {
            sprite = context.getTextureAtlas().createSprite("tiles/vertical");
        }
    }

    /**
     * Return the tile {@link Sprite} for this {@link Tileable}.
     *
     * @return The tile sprite.
     */
    @Override
    public Sprite getTileSprite() {
        return sprite;
    }


    /**
     * This method is invoked when a {@link Tileable} has accepted a marble.
     *
     * @param tileable The tileable that has accepted the marble.
     * @param direction The direction from which the marble was accepted.
     * @param marble The marble that has been accepted.
     */
    @Override
    public void ballAccepted(Tileable tileable, Direction direction, Marble marble) {
        Track track = getTileable();
        Actor actor = getContext().actor(marble);
        Action move;
        actor.setRotation(0.f);
        switch (direction) {
            case TOP:
                actor.setPosition(getWidth() / 2.f, getHeight(), Align.center);
                move = Actions.moveBy(0, -getHeight(), getHeight() * TRAVEL_TIME);
                break;
            case BOTTOM:
                actor.setPosition(getWidth() / 2.f, 0.f, Align.center);
                move = Actions.moveBy(0, getHeight(), getHeight() * TRAVEL_TIME);
                break;
            case LEFT:
                actor.setPosition(0.f, getHeight() / 2.f, Align.center);
                move = Actions.moveBy(getWidth(), 0, getWidth() * TRAVEL_TIME);
                break;
            case RIGHT:
                actor.setPosition(getWidth(), getHeight() / 2.f, Align.center);
                move = Actions.moveBy(-getWidth(), 0, getWidth() * TRAVEL_TIME);
                break;
            default:
                move = Actions.sequence();
        }

        actor.addAction(Actions.sequence(
            move,
            Actions.run(() -> {
                Direction inverse = direction.inverse();
                if (!track.isReleasable(inverse)) {
                    BOUNCE.play();
                    ballAccepted(tileable, inverse, marble);
                    return;
                }

                track.release(inverse, marble);
            }))
        );
        addActor(actor);
    }
}
