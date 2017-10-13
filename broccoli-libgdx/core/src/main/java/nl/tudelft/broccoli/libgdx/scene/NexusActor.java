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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.nexus.SpawningNexus;
import nl.tudelft.broccoli.libgdx.Context;

/**
 * An {@link Actor} for a (spawning) nexus on the grid.
 *
 * @author Earth Grob (w.lauwapong@student.tudelft.nl)
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class NexusActor extends TileableActor<Nexus> implements TileableListener {
    /**
     * The travel time multiplier for travel speed over this track.
     */
    private static final float TRAVEL_TIME = 0.008f;

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
    public NexusActor(Nexus nexus, Context context) {
        super(nexus, context);
        nexus.addListener(this);
        connected = nexus.isConnected(Direction.BOTTOM);
        spawning = nexus instanceof SpawningNexus;

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = context.getTextureAtlas().createSprite("nexus", i);
        }
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
        Actor registry = getContext().actor(marble);
        Actor actor = registry != null ? registry : new MarbleActor(marble, getContext());
        addActor(actor);

        Nexus nexus = getTileable();
        Action move;

        switch (direction) {
            case TOP:
                actor.setPosition(getWidth() / 2.f, getHeight(), Align.center);
                move = Actions.moveBy(0, -getHeight() / 2.f, (getHeight() / 2.f) * TRAVEL_TIME);
                break;
            case BOTTOM:
                actor.setPosition(getWidth() / 2.f, 0.f, Align.center);
                move = Actions.moveBy(0, getHeight() / 2.f, (getHeight() / 2.f) * TRAVEL_TIME);
                break;
            case LEFT:
                actor.setPosition(0.f, getHeight() / 2.f, Align.center);
                move = Actions.moveBy(getWidth() / 2.f, 0, (getWidth() / 2.f) * TRAVEL_TIME);
                break;
            case RIGHT:
                actor.setPosition(getWidth(), getHeight() / 2.f, Align.center);
                move = Actions.moveBy(-getWidth() / 2.f, 0, (getWidth() / 2.f) * TRAVEL_TIME);
                break;
            default:
                move = Actions.sequence();
        }

        actor.addAction(Actions.sequence(
            move,
            Actions.run(() -> {
                if (nexus.isReleasable(Direction.BOTTOM, marble)) {
                    actor.clearActions();
                    nexus.release(Direction.BOTTOM, marble);
                    nexus.getContext().setOccupied(false);
                    return;
                }
                move.restart();
            }),
            move,
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
     * Draw only the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Spawn a new ball if the nexus is unoccupied and we are a spawning nexus
        if (spawning && !getTileable().getContext().isOccupied()) {
            SpawningNexus nexus = (SpawningNexus) getTileable();
            nexus.spawn();
        }
        super.draw(batch, parentAlpha);
    }
}
