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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tile;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An {@link Actor} node in the 2d scene which represents an in-game grid.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class GridActor extends WidgetGroup {
    /**
     * The grid to draw.
     */
    private final Grid grid;

    /**
     * The {@link Table} we use to draw the grid.
     */
    private final Table table;

    /**
     * Construct a {@link GridActor} instance.
     *
     * @param context The actor context to use.
     * @param grid The grid to display.
     */
    public GridActor(ActorContext context, Grid grid) {
        this.grid = grid;
        this.setUserObject(grid);
        context.register(grid, this);

        this.table = new Table();
        this.table.setFillParent(true);
        this.addActor(table);

        for (int j = grid.getHeight() - 1; j >= 0; j--) {
            for (int i = 0; i < grid.getWidth(); i++) {
                Tile tile = grid.get(i, j);
                table.add(new TileActor(tile, context));
            }
            table.row();
        }
    }

    /**
     * Return the {@link Grid} of the actor.
     *
     * @return The grid of the actor.
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Draw the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // Hack to draw contents of tiles after the tiles themselves
        // This allows the content of tiles (including the marbles) to overflow into new tiles.
        for (Actor actor : table.getChildren()) {
            TileActor tile = (TileActor) actor;
            tile.drawTileable(batch, parentAlpha);
        }
    }
}
