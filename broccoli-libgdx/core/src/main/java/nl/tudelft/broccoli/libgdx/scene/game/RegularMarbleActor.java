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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An {@link Actor} that represents an in-game regular marble.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class RegularMarbleActor extends MarbleActor {
    /**
     * The travel time multiplier for travel speed over this track.
     */
    private static final float FRAME_DURATION = 0.08f;

    /**
     * The marble of this actor.
     */
    private Marble marble;

    /**
     * The horizontal animation for this marble.
     */
    private Animation<TextureRegion> horizontal;

    /**
     * The vertical animation for this marble.
     */
    private Animation<TextureRegion> vertical;

    /**
     * The animation time.
     */
    private float animationTime = 0.f;

    /**
     * The direction the marble is going.
     */
    private Direction direction;

    /**
     * A flag to indicate the marble is moving.
     */
    private boolean moving = true;

    /**
     * Construct a {@link RegularMarbleActor} instance.
     *
     * @param marble The marble to create this actor for.
     * @param context The context of the actor.
     */
    public RegularMarbleActor(Marble marble, ActorContext context) {
        this.marble = marble;
        context.register(marble, this);
        this.horizontal = new Animation<>(FRAME_DURATION,
                context.getTextureAtlas().findRegions("marbles/"
                        + marble.getType().name().toLowerCase() + "/horizontal"),
                Animation.PlayMode.LOOP);
        this.vertical = new Animation<>(FRAME_DURATION,
                context.getTextureAtlas().findRegions("marbles/"
                        + marble.getType().name().toLowerCase() + "/vertical"),
                Animation.PlayMode.LOOP);

        TextureRegion region = horizontal.getKeyFrame(0);
        this.setUserObject(marble);
        this.setSize(region.getRegionWidth(), region.getRegionHeight());
        this.setOrigin(Align.center);
        this.setDirection(Direction.LEFT);
    }

    /**
     * Draw the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        TextureRegion region = getAnimation().getKeyFrame(animationTime);
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
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

        if (!moving) {
            return;
        }
        animationTime += deltaTime;
    }

    /**
     * Return the {@link Animation} to use for the drawing.
     *
     * @return The animation to use.
     */
    private Animation<TextureRegion> getAnimation() {
        switch (direction) {
            case LEFT:
            case RIGHT:
                return horizontal;
            case TOP:
            case BOTTOM:
                return vertical;
            default:
                return horizontal;
        }
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
        this.direction = direction;

        Animation<TextureRegion> animation = getAnimation();
        switch (direction) {
            case LEFT:
            case BOTTOM:
                animation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
                break;
            case RIGHT:
            case TOP:
                animation.setPlayMode(Animation.PlayMode.LOOP);
                break;
            default:
        }
    }

    /**
     * Set the flag whether the {@link Marble} is moving or not.
     *
     * @param moving A flag indicate the moving of the marble.
     */
    @Override
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
