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
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.receptor.ReceptorListener;
import nl.tudelft.broccoli.core.receptor.Slot;
import nl.tudelft.broccoli.libgdx.Context;

import java.util.EnumMap;

/**
 * An {@link Actor} node in the 2d scene which represents a {@link Receptor}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ReceptorActor extends TileableActor<Receptor> implements ReceptorListener {
    /**
     * The travel time multiplier for travel speed over this track.
     */
    private static final float TRAVEL_TIME = 0.008f;

    /**
     * The duration of the explosion animation frame on the marking of a receptor.
     */
    private static final float EXPLOSION_TIME = 0.12f;

    /**
     * The turn sound of a receptor.
     */
    private static final Sound ROTATE =
        Gdx.audio.newSound(Gdx.files.classpath("sound/sfx/rotate.wav"));

    /**
     * The dock sound of a receptor.
     */
    private static final Sound DOCK =
        Gdx.audio.newSound(Gdx.files.classpath("sound/sfx/dock.wav"));

    /**
     * The explosion sound of a receptor.
     */
    private static final Sound EXPLODE =
        Gdx.audio.newSound(Gdx.files.classpath("sound/sfx/explode.wav"));

    /**
     * The clank sound in case a ball cannot be released.
     */
    private static final Sound CLANK =
        Gdx.audio.newSound(Gdx.files.classpath("sound/sfx/clank.wav"));

    /**
     * The marked receptor sprite of this receptor.
     */
    private final Sprite marked;

    /**
     * The unmarked receptor sprite of this receptor.
     */
    private final Sprite unmarked;

    /**
     * The marked receptor tile sprite of this receptor.
     */
    private final Sprite markedTile;

    /**
     * The unmarked receptor tile sprite of this receptor.
     */
    private final Sprite unmarkedTile;

    /**
     * The explosion animation of the receptor.
     */
    private final Animation<TextureRegion> explosion;

    /**
     * The animation time.
     */
    private float animationTime = 0.f;

    /**
     * A map which maps the direction of slots to their positions on the receptor.
     */
    private EnumMap<Direction, Vector2> positions = new EnumMap<>(Direction.class);

    /**
     * Construct a {@link ReceptorActor} instance.
     *
     * @param receptor The receptor this actor represents.
     * @param context The context of the actor.
     */
    public ReceptorActor(Receptor receptor, Context context) {
        super(receptor, context);

        // Initialise sprites of the receptor.
        TextureAtlas atlas = context.getTextureAtlas();
        marked = atlas.createSprite("receptor/marked");
        unmarked = atlas.createSprite("receptor/unmarked");

        int tile = 0;
        // Generate the index of the adaptive tile.
        // We generate this index by creating a number between 1-15 representing the directions
        // at which the receptor is connected in binary in counterclockwise order starting from
        // the TOP direction (e.g. 1010 means TOP and BOTTOM are connected)
        // This is done by flipping the bits on the tile index on the places it is connected.
        for (int i = 0; i < 4; i++) {
            if (receptor.isConnected(Direction.from(4 - i))) {
                tile |= 1 << (3 - i);
            }
        }

        markedTile = atlas.createSprite("receptor/tile_marked", tile);
        unmarkedTile = atlas.createSprite("receptor/tile_unmarked", tile);
        explosion = new Animation<>(EXPLOSION_TIME, atlas.findRegions("explosion"),
            Animation.PlayMode.REVERSED);

        receptor.addListener(this);
        setSize(unmarked.getWidth(), unmarked.getHeight());
        setOrigin(Align.center);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // We need to re-create the action sequence each call since the actions are pooled
                // and automatically reset after they are done.
                addAction(Actions.sequence(
                    Actions.run(receptor::lock),
                    Actions.parallel(
                        Actions.run(ROTATE::play),
                        Actions.rotateBy(-90.f, 0.2f),
                        Actions.run(() -> receptor.rotate(1))

                    ),
                    Actions.run(receptor::unlock)
                ));

                for (Direction d : Direction.values()) {
                    if (receptor.getSlot(d).isOccupied()) {
                        Marble ball = receptor.getSlot(d).getMarble();
                        Actor actor = getContext().actor(ball);
                        actor.addAction(Actions.sequence(
                                Actions.rotateBy(90.f, 0.2f)
                        ));
                    }
                }
                return true;
            }
        });

        float middleX = getWidth() / 2.f;
        float middleY = getHeight() / 2.f;
        float offset = 25.f;
        positions.put(Direction.TOP, new Vector2(middleX,  getHeight() - offset));
        positions.put(Direction.BOTTOM, new Vector2(middleX, offset));
        positions.put(Direction.LEFT, new Vector2(offset, middleY));
        positions.put(Direction.RIGHT, new Vector2(getWidth() - offset, middleY));

        for (Direction direction : Direction.values()) {
            Slot slot = receptor.getSlot(direction);

            if (!slot.isOccupied()) {
                continue;
            }

            ballAccepted(getTileable(), direction, slot.getMarble());
        }
    }

    /**
     * Return the tile {@link Sprite} for this {@link Tileable}.
     *
     * @return The tile sprite.
     */
    @Override
    public Sprite getTileSprite() {
        return getTileable().isMarked() ? markedTile : unmarkedTile;
    }

    /**
     * Return the receptor {@link Sprite} for the receptor's current state.
     *
     * @return The receptor sprite.
     */
    public Sprite getReceptorSprite() {
        return getTileable().isMarked() ? marked : unmarked;
    }

    /**
     * This method is invoked when the actor should draw itself.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha value of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion region = explosion.getKeyFrame(animationTime);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
            getHeight(), getScaleX(), getScaleY(), getRotation());

        Sprite receptor = getReceptorSprite();
        receptor.setRotation(getRotation());
        receptor.draw(batch);
        super.draw(batch, parentAlpha);
    }

    /**
     * Act on the scene updates.
     *
     * @param deltaTime The time delta.
     */
    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if (getTileable().isMarked()) {
            animationTime += deltaTime;
        }
    }

    /**
     * This method is invoked when a {@link Receptor} was marked.
     *
     * @param receptor The receptor that has been marked.
     */
    @Override
    public void receptorMarked(Receptor receptor) {
        animationTime = 0.f;
        EXPLODE.play();
    }

    /**
     * This method is invoked when a {@link Tileable} has disposed a marble.
     *
     * @param tileable  The tileable that has disposed the marble.
     * @param direction The direction from which the marble was disposed.
     * @param marble      The marble that has been disposed.
     */
    @Override
    public void ballDisposed(Tileable tileable, Direction direction, Marble marble) {
        Actor actor = getContext().actor(marble);

        if (actor != null) {
            actor.remove();
        }
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
        // Get the actor for the marble or create a new one if one does not exist yet.
        MarbleActor registry = (MarbleActor) getContext().actor(marble);
        MarbleActor actor = registry != null ? registry : new MarbleActor(marble, getContext());

        Receptor receptor = getTileable();
        Slot slot = receptor.getSlot(direction);

        Vector2 target = positions.get(direction.rotate(-receptor.getRotation())).cpy();
        Vector2 origin = stageToLocalCoordinates(actor.localToStageCoordinates(
                new Vector2(actor.getWidth() / 2.f, actor.getHeight() / 2.f)));
        actor.addAction(Actions.sequence(
            Actions.moveToAligned(target.x, target.y, Align.center, target.dst(origin)
                * TRAVEL_TIME),
            Actions.run(() -> actor.setMoving(false))
        ));
        actor.setPosition(origin.x, origin.y, Align.center);
        actor.rotateBy(-getRotation());
        actor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Direction out = slot.getDirection();
                if (receptor.isReleasable(out, marble)) {
                    actor.clearActions();
                    actor.removeListener(this);
                    actor.setDirection(out);
                    slot.release();
                } else {
                    CLANK.play();
                }

                // Stop the event from propagating
                event.stop();
                return true;
            }
        });
        addActor(actor);

        DOCK.play();
    }
}