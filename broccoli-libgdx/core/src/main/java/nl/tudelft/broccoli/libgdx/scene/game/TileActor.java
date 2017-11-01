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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.tudelft.broccoli.core.Announcer;
import nl.tudelft.broccoli.core.Empty;
import nl.tudelft.broccoli.core.Teleporter;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.nexus.Nexus;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.Track;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import nl.tudelft.broccoli.libgdx.scene.game.receptor.ReceptorActor;

/**
 * An {@link Actor} that represents a {@link Tile} on a {@link Grid}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class TileActor extends Group {
    /**
     * The game context of this actor.
     */
    private final ActorContext context;

    /**
     * The {@link TileableActor} for the {@link Tileable} of this tile.
     */
    private TileableActor<?> tileableActor;

    /**
     * Construct a {@link TileActor}.
     *
     * @param tile The {@link Tile} to create the actor for.
     * @param context The game context to use.
     */
    public TileActor(Tile tile, ActorContext context) {
        this.context = context;
        this.context.register(tile, this);
        this.tileableActor = createActor(tile);
        this.addActor(tileableActor);
        this.setUserObject(tile);

        Sprite sprite = tileableActor.getTileSprite();
        setSize(sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Draw the contents of the tile onto the screen.
     *
     * @param batch the batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    public void drawTileable(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    /**
     * Draw only the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Sprite tile = tileableActor.getTileSprite();
        tile.setScale(getScaleX(), getScaleY());
        tile.setOrigin(getOriginX(), getOriginY());
        tile.setRotation(getRotation());
        tile.setPosition(getX(), getY());
        tile.draw(batch);
    }

    /**
     * Convert a {@link Tile} instance to a {@link TileableActor}.
     *
     * @param tile The tile to convert.
     * @return The {@link TileableActor} for the tile.
     */
    private TileableActor<?> createActor(Tile tile) {
        Tileable tileable = tile.getTileable();
        final TileableActor<?> result;

        if (tileable instanceof Teleporter) {
            result = new TeleporterActor((Teleporter) tileable, context);
        } else if (tileable instanceof Receptor) {
            result = new ReceptorActor((Receptor) tileable, context);
        } else if (tileable instanceof Nexus) {
            result = new NexusActor((Nexus) tileable, context);
        } else if (tileable instanceof Track) {
            result = new TrackActor((Track) tileable, context);
        } else if (tileable instanceof TimerTile) {
            result = new TimerActor((TimerTile) tileable, context);
        } else if (tileable instanceof Announcer) {
            result = new AnnouncerActor((Announcer) tileable, context);
        } else if (tileable instanceof Empty) {
            result = new EmptyActor((Empty) tileable, context);
        } else {
            result = new UnsupportedTileableActor(tileable, context);
        }

        return result;
    }
}
