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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An {@link Actor} that represents a {@link Tile} on a {@link Grid}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class TileActor extends Actor {
    /**
     * The {@link TileableActor} of the tile.
     */
    private final TileableActor<?> tileableActor;

    /**
     * Construct a {@link TileActor}.
     *
     * @param tile The {@link Tile} to create the actor for.
     * @param context The game context to use.
     */
    public TileActor(Tile tile, ActorContext context) {
        context.register(tile, this);
        this.setUserObject(tile);
        this.tileableActor = (TileableActor<?>) context.actor(tile.getTileable());

        TextureRegion region = tileableActor.getTileTexture();
        this.setSize(region.getRegionWidth(), region.getRegionHeight());
    }

    /**
     * Draw only the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion region = tileableActor.getTileTexture();
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
            getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
