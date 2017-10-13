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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.libgdx.Context;


/**
 * An {@link Actor} that represents an in-game marble.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class MarbleActor extends Actor {
    /**
     * The marble of this actor.
     */
    private Marble marble;

    /**
     * The game context of the actor.
     */
    private Context context;

    /**
     * The sprite for this marble.
     */
    private Sprite sprite;

    /**
     * Construct a {@link MarbleActor} instance.
     *
     * @param marble The marble to create this actor for.
     * @param context The context of the actor.
     */
    public MarbleActor(Marble marble, Context context) {
        this.marble = marble;
        this.context = context;
        this.context.register(marble, this);
        this.sprite = context.getTextureAtlas().createSprite("marbles/"
            + marble.getType().name().toLowerCase());
        this.setUserObject(marble);
        this.setSize(sprite.getWidth(), sprite.getHeight());
        this.setOrigin(Align.center);
    }

    /**
     * Return the {@link Marble} of this actor.
     *
     * @return The marble of this actor.
     */
    public Marble getMarble() {
        return marble;
    }

    /**
     * Draw the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setScale(getScaleX(), getScaleY());
        sprite.setOrigin(getOriginX(), getOriginY());
        sprite.setRotation(getRotation());
        sprite.setPosition(getX(), getY());
        sprite.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }
}
