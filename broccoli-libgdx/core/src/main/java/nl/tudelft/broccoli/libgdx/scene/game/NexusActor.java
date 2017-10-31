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

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An {@link Actor} for a (spawning) nexus on the grid.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class NexusActor extends TransportingActor<Nexus> implements TileableListener {

    /**
     * A flag to indicate whether this tile is connected.
     */
    private boolean connected = false;

    /**
     * A flag to indicate whether this tile is a spawning tile.
     */
    private boolean spawning = false;

    /**
     * The sprites of the nexus.
     */
    private Sprite[] sprites = new Sprite[16];

    /**
     * Construct a {@link NexusActor} instance.
     *
     * @param nexus The tileable entity to create the actor for.
     * @param context The game context of this actor.
     */
    public NexusActor(Nexus nexus, ActorContext context) {
        super(nexus, context);
        nexus.addListener(this);
        connected = nexus.isConnected(Direction.BOTTOM);
        spawning = nexus instanceof SpawningNexus;

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = context.getTextureAtlas().createSprite("nexus", i);
        }

        this.setSize(sprites[0].getWidth(), sprites[0].getHeight());
    }

    /**
     * Get modifier.
     */
    @Override
    protected Image getModifier() {
        return null;
    }

    /**
     * Return the tile {@link Sprite} for this {@link Tileable}.
     *
     * @return The tile sprite.
     */
    @Override
    public Sprite getTileSprite() {
        return connected ? sprites[9] : sprites[0];
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
        addActor(actor);

        Nexus nexus = getTileable();
        Vector2 origin = getOrigin(direction);
        Vector2 center = getCenter();
        Vector2 target = getTarget(direction);
        Action moveCenter = Actions.moveToAligned(center.x, center.y, Align.center,
            origin.dst(center) * TRAVEL_TIME);
        Action moveTarget = Actions.moveToAligned(target.x, target.y, Align.center,
            target.dst(center) * TRAVEL_TIME);
        actor.setPosition(origin.x, origin.y, Align.center);
        actor.setDirection(direction.inverse());
        actor.addAction(Actions.sequence(
            moveCenter,
            Actions.run(() -> {
                Direction out = Direction.BOTTOM;
                if (nexus.isReleasable(out, marble)) {
                    actor.clearActions();
                    actor.setDirection(out);
                    nexus.release(out, marble);
                    nexus.getContext().setOccupied(false);
                }
            }),
            moveTarget,
            Actions.run(() -> {
                Direction inverse = direction.inverse();
                if (!nexus.isReleasable(inverse, marble)) {
                    ballAccepted(tileable, inverse, marble);
                    return;
                }

                nexus.release(inverse, marble);
            })
        ));
    }

    /**
     * Return the origin position for a mable.
     *
     * @param direction The direction the ball is coming from.
     * @return The origin position for a marble.
     */
    private Vector2 getOrigin(Direction direction) {
        Vector2 result;
        switch (direction) {
            case TOP:
                result = new Vector2(getWidth() / 2.f, getHeight());
                break;
            case BOTTOM:
                result = new Vector2(getWidth() / 2.f, 0.f);
                break;
            case LEFT:
                result = new Vector2(0.f, getHeight() / 2.f);
                break;
            case RIGHT:
                result = new Vector2(getWidth(), getHeight() / 2.f);
                break;
            default:
                result = new Vector2(0, 0);
        }

        return result;
    }

    /**
     * Act on the scene updates.
     *
     * @param deltaTime The time delta.
     */
    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        // Spawn a new ball if the nexus is unoccupied and we are a spawning nexus
        if (spawning && !getTileable().getContext().isOccupied()) {
            SpawningNexus nexus = (SpawningNexus) getTileable();
            nexus.spawn();
        }
    }
}
