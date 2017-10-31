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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.powerup.bonus.BonusPowerUp;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.receptor.ReceptorListener;
import nl.tudelft.broccoli.libgdx.Context;
import nl.tudelft.broccoli.libgdx.strategy.BonusStrategy;
import nl.tudelft.broccoli.libgdx.strategy.JokerStrategy;
import nl.tudelft.broccoli.libgdx.strategy.PowerUpStrategy;

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
     * The receptor image of this receptor.
     */
    private final Image image;

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
     * The power-up strategy.
     */
    private PowerUpStrategy powerUpStrategy;

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
        image = new Image(atlas.findRegion("receptor/unmarked"));
        addActor(image);

        markedTile = atlas.createSprite("receptor/tile_marked", getTileIndex());
        unmarkedTile = atlas.createSprite("receptor/tile_unmarked", getTileIndex());
        explosion = new Animation<>(EXPLOSION_TIME, atlas.findRegions("explosion"),
            Animation.PlayMode.REVERSED);

        receptor.addListener(this);
        setSize(image.getWidth(), image.getHeight());
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


                for (Direction direction : Direction.values()) {
                    if (receptor.getSlot(direction).isOccupied()) {
                        Marble ball = receptor.getSlot(direction).getMarble();
                        Actor actor = getContext().actor(ball);
                        actor.addAction(Actions.rotateBy(90.f, 0.2f));
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
            Receptor.Slot slot = receptor.getSlot(direction);

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
        if (getTileable().isMarked() || getTileable().getPowerUp() != null) {
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

        // Draw a marked receptor
        image.setDrawable(new TextureRegionDrawable(getContext().getTextureAtlas()
            .findRegion("receptor/marked")));
    }

    /**
     * This method is invoked when a {@link Receptor} has been assigned a {@link PowerUp} it can
     * activate.
     *
     * @param receptor The receptor to which the {@link PowerUp} is assigned.
     */
    @Override
    public void receptorAssigned(Receptor receptor) {
        if (receptor.getPowerUp() != null) {
            if (receptor.getPowerUp() instanceof BonusPowerUp) {
                powerUpStrategy = new BonusStrategy();
            } else {
                powerUpStrategy = new JokerStrategy();
            }
            animationTime = 0.f;
            EXPLODE.play();

            image.addAction(powerUpStrategy.animate());
            return;
        }

        image.setColor(Color.WHITE);
        image.clearActions();
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

        Vector2 target = getTarget(direction);
        Vector2 origin = getOrigin(actor);

        actor.addAction(Actions.sequence(
            Actions.moveToAligned(target.x, target.y, Align.center, target.dst(origin)
                * TRAVEL_TIME),
            Actions.run(() -> actor.setMoving(false))
        ));
        actor.setPosition(origin.x, origin.y, Align.center);
        actor.rotateBy(-getRotation());
        actor.addListener(getInputHandler(actor, direction));

        addActor(actor);
        DOCK.play();
    }

    /**
     * Return the target of a marble.
     *
     * @param direction The direction the ball is coming from.
     * @return The target of a marble.
     */
    private Vector2 getTarget(Direction direction) {
        return positions.get(direction.rotate(-getTileable().getRotation())).cpy();
    }

    /**
     * Return the origin of a marble.
     *
     * @param actor The actor of the marble.
     * @return The origin of the marble.
     */
    private Vector2 getOrigin(Actor actor) {
        return stageToLocalCoordinates(actor.localToStageCoordinates(
            new Vector2(actor.getWidth() / 2.f, actor.getHeight() / 2.f)));
    }

    /**
     * Return the input handler for the given marble actor.
     *
     * @param actor The marble actor to create the input handler for.
     * @param direction The direction the marble is coming from.
     * @return The input handler of the marble actor.
     */
    private InputListener getInputHandler(MarbleActor actor, Direction direction) {
        Receptor receptor = getTileable();
        Receptor.Slot slot = receptor.getSlot(direction);
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Direction out = slot.getDirection();
                Vector2 pos = positions.get(out.rotate(-receptor.getRotation())).cpy();

                if (receptor.isReleasable(out, actor.getMarble())) {
                    actor.clearActions();
                    actor.removeListener(this);
                    actor.setDirection(out);
                    actor.setPosition(pos.x, pos.y, Align.center);
                    slot.release();
                } else {
                    CLANK.play();
                }

                // Stop the event from propagating
                event.stop();
                return true;
            }
        };
    }
}
