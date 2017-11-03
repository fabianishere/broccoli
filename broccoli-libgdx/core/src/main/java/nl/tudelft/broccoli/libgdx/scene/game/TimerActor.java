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

import static nl.tudelft.broccoli.libgdx.scene.actions.ScreenActions.replace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import nl.tudelft.broccoli.libgdx.scene.ui.screen.FinishScreen;

/**
 * An {@link Actor} for a timer on the grid.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class TimerActor extends TileableActor<TimerTile> {
    /**
     * The possible textures of the timer.
     */
    private final Array<? extends TextureRegion> textures;

    /**
     * The index of the sprite currently shown.
     */
    private int currentTextureId;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context  The game context to use.
     */
    public TimerActor(TimerTile tileable, ActorContext context) {
        super(tileable, context);

        float delay = getTileable().getMaxTime() / 5.f;
        addAction(Actions.sequence(
            Actions.repeat(4, Actions.sequence(
                Actions.delay(delay),
                Actions.run(() -> currentTextureId++)
            )),
            replace(new FinishScreen(context, getTileable().getTile().getGrid().getSession()))
        ));

        textures = context.getTextureAtlas().findRegions("counter");
    }

    /**
     * Return the tile {@link TextureRegion} for this {@link Tileable}.
     *
     * @return The tile texture.
     */
    @Override
    public TextureRegion getTileTexture() {
        int index = textures.size - currentTextureId - 1;
        return textures.get(Math.max(0, index));
    }
}
