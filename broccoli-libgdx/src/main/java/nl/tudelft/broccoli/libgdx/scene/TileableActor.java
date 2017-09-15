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
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import nl.tudelft.broccoli.core.Entity;
import nl.tudelft.broccoli.core.grid.Tileable;

/**
 * An actor in the scene for a {@link nl.tudelft.broccoli.core.grid.Tileable} entity.
 *
 * @param <T> The shape of the tileable entity.
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public abstract class TileableActor<T extends Tileable> extends WidgetGroup {
    /**
     * The tileable {@link Entity}.
     */
    private final T tileable;

    /**
     * The game context to use.
     */
    private final Context context;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context The game context to use.
     */
    public TileableActor(T tileable, Context context) {
        this.tileable = tileable;
        this.context = context;
        this.context.register(tileable, this);
        this.setUserObject(tileable);
        this.setFillParent(true);
    }

    /**
     * Return the tile {@link Texture} for this {@link Tileable}.
     *
     * @return The tile texture.
     */
    public abstract Texture getTileTexture();

    /**
     * Return the {@link Tileable} of this actor.
     *
     * @return The tileable entity of this actor.
     */
    public T getTileable() {
        return tileable;
    }

    /**
     * Return the game {@link Context} of this actor.
     *
     * @return The game context of the actor.
     */
    public Context getContext() {
        return context;
    }
}