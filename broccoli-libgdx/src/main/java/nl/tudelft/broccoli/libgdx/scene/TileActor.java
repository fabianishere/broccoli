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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.tudelft.broccoli.core.Empty;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.Track;

/**
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class TileActor extends Group {
    /**
     * The {@link Tile} of this actor.
     */
    private Tile tile;

    /**
     * The {@link TileableActor} for the {@link Tileable} of this tile.
     */
    private TileableActor<?> tileableActor;

    /**
     * Construct a {@link TileActor}.
     *
     * @param tile The {@link Tile} to create the actor for.
     */
    public TileActor(Tile tile) {
        this.tile = tile;

        tileableActor = createActor(tile);
        addActor(tileableActor);

        Texture texture = tileableActor.getTileTexture();
        setSize(texture.getWidth(), texture.getHeight());
    }

    /**
     * Draw the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture txt = tileableActor.getTileTexture();
        batch.draw(txt, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
            getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
            txt.getWidth(), txt.getHeight(), false, false);

        super.draw(batch, parentAlpha);
    }

    /**
     * Return the {@link Tile} of this actor.
     *
     * @return The tile of this actor.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Convert a {@link Tile} instance to a {@link TileableActor}.
     *
     * @param tile The tile to convert.
     * @return The {@link TileableActor} for the tile.
     */
    private TileableActor<?> createActor(Tile tile) {
        Tileable tileable = tile.getTileable();

        if (tileable instanceof Receptor) {
            return new ReceptorActor((Receptor) tileable);
        } else if (tileable instanceof Track) {
            return new TrackActor((Track) tileable);
        } else if (tileable instanceof Empty) {
            return new EmptyActor((Empty) tileable);
        }

        return new UnsupportedTileableActor(tileable);
    }
}
