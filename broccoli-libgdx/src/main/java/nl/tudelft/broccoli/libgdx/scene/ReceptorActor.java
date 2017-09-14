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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.receptor.Receptor;

/**
 * An {@link Actor} node in the 2d scene which represents a {@link Receptor}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ReceptorActor extends TileableActor<Receptor> {
    /**
     * A marked texture of a receptor.
     */
    private static final Texture TX_RECEPTOR_MARKED =
        new Texture(Gdx.files.classpath("sprites/receptor/marked/0.png"));

    /**
     * An unmarked texture of a receptor.
     */
    private static final Texture TX_RECEPTOR_UNMARKED =
        new Texture(Gdx.files.classpath("sprites/receptor/unmarked/0.png"));

    /**
     * The texture for a tile of a marked receptor.
     */
    private static final Texture TX_TILE_MARKED =
        new Texture(Gdx.files.classpath("sprites/tiles/receptor/marked/14.png"));

    /**
     * The texture for a tile of an unmarked receptor.
     */
    private static final Texture TX_TILE_UNMARKED =
        new Texture(Gdx.files.classpath("sprites/tiles/receptor/unmarked/14.png"));


    /**
     * Construct a {@link ReceptorActor} instance.
     *
     * @param receptor The receptor this actor represents.
     */
    public ReceptorActor(Receptor receptor) {
        super(receptor);

        setOrigin(TX_RECEPTOR_MARKED.getWidth() / 2, TX_RECEPTOR_MARKED.getHeight() / 2);
        setSize(TX_RECEPTOR_MARKED.getWidth(), TX_RECEPTOR_MARKED.getHeight());
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // We need to re-create the action sequence each call since the actions are pooled
                // and automatically reset after they are done.
                addAction(Actions.sequence(
                    Actions.rotateBy(-90.f, 0.2f),
                    Actions.run(() -> receptor.rotate(1))
                ));
                return true;
            }
        });
    }

    /**
     * Return the receptor {@link Texture} for the receptor's current state.
     *
     * @return The receptor texture.
     */
    public Texture getReceptorTexture() {
        return getTileable().isMarked() ? TX_RECEPTOR_MARKED : TX_RECEPTOR_UNMARKED;
    }

    /**
     * Return the tile {@link Texture} for this {@link Tileable}.
     *
     * @return The tile texture.
     */
    @Override
    public Texture getTileTexture() {
        return getTileable().isMarked() ? TX_TILE_MARKED : TX_TILE_UNMARKED;
    }

    /**
     * This method is invoked when the actor should draw itself.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha value of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture txt = getReceptorTexture();
        batch.draw(txt, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
            getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
            txt.getWidth(), txt.getHeight(), false, false);

        super.draw(batch, parentAlpha);
    }
}
