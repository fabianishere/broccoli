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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.Track;

/**
 * An {@link Actor} for a track on the grid.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class TrackActor extends TileableActor<Track> implements TileableListener {
    /**
     * The texture for a horizontal track.
     */
    private static final Texture TX_TILE_HORIZONTAL =
        new Texture(Gdx.files.classpath("sprites/tiles/horizontal/0.png"));

    /**
     * The texture for a vertical track.
     */
    private static final Texture TX_TILE_VERTICAL =
        new Texture(Gdx.files.classpath("sprites/tiles/vertical/0.png"));

    /**
     * A flag to indicate whether this track is horizontal or vertical.
     */
    private boolean horizontal = false;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context The game context of this actor.
     */
    public TrackActor(Track tileable, Context context) {
        super(tileable, context);
        tileable.addListener(this);
        horizontal = tileable instanceof HorizontalTrack;
    }

    /**
     * Return the tile {@link Texture} for this {@link Tileable}.
     *
     * @return The tile texture.
     */
    @Override
    public Texture getTileTexture() {
        return horizontal ? TX_TILE_HORIZONTAL : TX_TILE_VERTICAL;
    }

    /**
     * This method is invoked when a {@link Tileable} has accepted a ball.
     *
     * @param tileable The tileable that has accepted the ball.
     * @param direction The direction from which the ball was accepted.
     * @param ball The ball that has been accepted.
     */
    @Override
    public void ballAccepted(Tileable tileable, Direction direction, Ball ball) {
        Track track = getTileable();
        Actor actor = getContext().actor(ball);
        Action move;

        switch (direction) {
            case TOP:
                actor.setPosition(getWidth() / 2.f, getHeight(), Align.center);
                move = Actions.moveBy(0, -getHeight(), getHeight() * 0.005f);
                break;
            case BOTTOM:
                actor.setPosition(getWidth() / 2.f, 0.f, Align.center);
                move = Actions.moveBy(0, getHeight(), getHeight() * 0.005f);
                break;
            case LEFT:
                actor.setPosition(0.f, getHeight() / 2.f, Align.center);
                move = Actions.moveBy(getWidth(), 0, getWidth() * 0.005f);
                break;
            case RIGHT:
                actor.setPosition(getWidth(), getHeight() / 2.f, Align.center);
                move = Actions.moveBy(-getWidth(), 0, getWidth() * 0.005f);
                break;
            default:
                move = Actions.sequence();
        }

        actor.addAction(Actions.sequence(
            move,
            Actions.run(() -> {
                Direction inverse = direction.inverse();
                if (!track.isReleasable(inverse)) {
                    ballAccepted(tileable, inverse, ball);
                    return;
                }

                track.release(inverse, ball);
            }))
        );
        addActor(actor);
    }
}
