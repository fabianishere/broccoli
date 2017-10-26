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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
 * An {@link Actor} node in the 2d scene which represents an in-game grid.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class GridActor extends Stack {
    /**
     * The {@link Table} we use to draw the grid.
     */
    private final Table tiles;

    /**
     * The {@link Table} we use to draw the tileables.
     */
    private final Table tileables;

    /**
     * Construct a {@link GridActor} instance.
     *
     * @param context The actor context to use.
     * @param grid The grid to display.
     */
    public GridActor(ActorContext context, Grid grid) {
        this.setUserObject(grid);
        context.register(grid, this);

        this.tiles = new Table();
        this.tiles.setFillParent(true);
        this.add(tiles);

        this.tileables = new Table();
        this.tileables.setFillParent(true);
        this.add(tileables);

        for (int j = grid.getHeight() - 1; j >= 0; j--) {
            for (int i = 0; i < grid.getWidth(); i++) {
                Tile tile = grid.get(i, j);
                TileableActor<?> tileableActor = createTileable(tile, context);
                TileActor tileActor = new TileActor(tile, context);
                tiles.add(tileActor).fill();
                tileables.add(tileableActor)
                    .width(tileActor.getWidth())
                    .height(tileActor.getHeight());
            }
            tiles.row();
            tileables.row();
        }
    }

    /**
     * Convert a {@link Tile} instance to a {@link TileableActor}.
     *
     * @param tile The tile to convert.
     * @return The {@link TileableActor} for the tile.
     */
    private TileableActor<?> createTileable(Tile tile, ActorContext context) {
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
