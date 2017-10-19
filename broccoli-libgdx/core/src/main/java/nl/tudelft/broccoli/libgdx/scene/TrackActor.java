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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.FilterTrack;
import nl.tudelft.broccoli.core.track.Track;
import nl.tudelft.broccoli.libgdx.Context;

/**
 * An {@link Actor} for a track on the grid.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
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
     * A possible overlay image for a special {@link Track}s if necessary.
     */
    private Image modifier;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context  The game context of this actor.
     */
    public TrackActor(Track tileable, Context context) {
        super(tileable, context);
        tileable.addListener(this);

        int tileIndex = 0;
        // Generate the index of the adaptive tile.
        // We generate this index by creating a number between 1-15 representing the directions
        // at which the track is connected in binary in counterclockwise order starting from
        // the TOP direction (e.g. 1010 means TOP and BOTTOM are connected)
        // This is done by flipping the bits on the tile index on the places it is connected.
        for (int i = 0; i < 4; i++) {
            if (tileable.isConnected(Direction.from(4 - i))) {
                tileIndex |= 1 << (3 - i);
            }
        }
        sprite = context.getTextureAtlas().createSprite("tile", tileIndex);
        modifier = getModifier();

        if (modifier != null) {
            addActor(modifier);
        }
    }

    /**
     * Initialize the modifier for this track.
     *
     * @return The modifier of this track.
     */
    private Image getModifier() {
        if (getTileable() instanceof FilterTrack) {
            FilterTrack track = (FilterTrack) getTileable();
            Image image = new Image(getContext().getTextureAtlas()
                    .findRegion("filter/" + track.getMarbleType().toString().toLowerCase()));

            // We use a 3 pixel offset since the image is incorrectly aligned
            image.setPosition(
                    sprite.getWidth() / 2 + 3,
                    sprite.getHeight() / 2 - 3,
                    Align.center
            );

            return image;
        }

        return new Image();
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
            travelAnimation(tileable, actor, direction, origin);
        } else {
            bounceTravelAnimation(tileable, actor, direction, origin);
        }

        addActorBefore(modifier, actor);
    }

    /**
     * This method will start the animation of an allowed {@link Marble} traveling over a
     * {@link Track}.
     *
     * @param tileable The {@link Tileable} of the {@link Track}.
     * @param actor The {@link MarbleActor} of the traveling {@link Marble}.
     * @param direction The {@link Direction} in which the {@link Marble} is traveling.
     * @param origin The place at which the marble started.
     */
    private void travelAnimation(Tileable tileable, MarbleActor actor, Direction direction,
                                 Vector2 origin) {
        Vector2 target = getTarget(direction);
        Marble marble = actor.getMarble();

        actor.addAction(Actions.sequence(
                Actions.moveToAligned(target.x, target.y, Align.center, origin.dst(target)
                        * TRAVEL_TIME),
                Actions.run(() -> {
                    Direction inverse = direction.inverse();
                    if (!getTileable().isReleasable(inverse, marble)) {
                        BOUNCE.play();
                        ballAccepted(tileable, inverse, marble);
                        return;
                    }

                    getTileable().release(inverse, marble);
                }))
        );
    }

    /**
     * This method will start the animation of an {@link Marble} (that is not allowed to pass)
     * traveling over a {@link Track}.
     *
     * @param tileable The {@link Tileable} of the {@link Track}.
     * @param actor The {@link MarbleActor} of the traveling {@link Marble}.
     * @param direction The {@link Direction} in which the {@link Marble} is traveling.
     * @param origin The place at which the marble started.
     */
    private void bounceTravelAnimation(Tileable tileable, MarbleActor actor, Direction direction,
                                       Vector2 origin) {
        Vector2 center = getCenter();
        Vector2 target = getTarget(direction.inverse());
        Marble marble = actor.getMarble();

        actor.addAction(Actions.sequence(
                Actions.moveToAligned(center.x, center.y, Align.center, origin.dst(center)
                    * TRAVEL_TIME),
                Actions.run(BOUNCE::play),
                Actions.moveToAligned(target.x, target.y, Align.center, center.dst(target)
                    * TRAVEL_TIME),
                Actions.run(() -> {
                    if (!getTileable().isReleasable(direction, marble)) {
                        BOUNCE.play();
                        ballAccepted(tileable, direction, marble);
                        return;
                    }

                    getTileable().release(direction, marble);
                }))
        );
    }

    /**
     * Prepares the {@link MarbleActor} for being moved.
     *
     * @param actor The soon to be prepared {@link MarbleActor}.
     * @param direction The {@link Direction} in which the {@link Marble} is traveling.
     * @param origin The start point of the {@link Marble}.
     */
    private void prepareActor(MarbleActor actor, Direction direction, Vector2 origin) {
        Vector2 center = getCenter();
        actor.setRotation(0.f);
        actor.setPosition(origin.x, origin.y);
        actor.setMoving(true);
        actor.setDirection(direction.inverse());

        switch (direction) {
            case TOP:
            case BOTTOM:
                actor.setPosition(center.x, origin.y, Align.center);
                break;
            case LEFT:
            case RIGHT:
                actor.setPosition(origin.x, center.y, Align.center);
                break;
            default:
        }
    }

    /**
     * Determines the position of the center of the current {@link Tile}.
     *
     * @return The local center position within this actor.
     */
    private Vector2 getCenter() {
        return new Vector2(getWidth() / 2, getHeight() / 2);
    }

    /**
     * Determines the target position the {@link Marble} has. It depends on you knowing if the
     * {@link Marble} is actually allowed to reach this location.
     *
     * @param direction The {@link Direction} the {@link Marble} is coming from.
     * @return The target position.
     */
    private Vector2 getTarget(Direction direction) {
        Vector2 center = getCenter();
        switch (direction) {
            case TOP:
                return new Vector2(center.x, 0);
            case BOTTOM:
                return new Vector2(center.x, getHeight());
            case LEFT:
                return new Vector2(getWidth(), center.y);
            default:
                return new Vector2(0, center.y);
        }
    }
}
