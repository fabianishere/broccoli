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

package nl.tudelft.broccoli.libgdx.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An actor in the scene for a pause overlay.
 *
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class PauseActor extends Table {
    /**
     * A flag to indicate the game is paused.
     */
    private boolean paused = false;

    /**
     * Construct a {@link PauseActor} instance.
     *
     * @param context The game context.
     */
    public PauseActor(ActorContext context) {
        this.setFillParent(true);
        this.setBackground(new TextureRegionDrawable(context.getTextureAtlas()
            .findRegion("pauseOverlay")));
        this.add(createResume()).width(200).height(50);
    }

    /**
     * Construct a resume {@link Button}.
     *
     * @return The resume button.
     */
    private Button createResume() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();

        Pixmap labelColor = new Pixmap(1, 1, Pixmap.Format.RGB888);
        labelColor.setColor(Color.GRAY);
        labelColor.fill();
        TextButton button = new TextButton("Resume", style);

        button.getLabel().getStyle().background = new Image(new Texture(labelColor))
            .getDrawable();
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                paused = !paused;
                remove();
            }
        });
        return button;
    }

    /**
     * Set whether the game is paused or not.
     *
     * @param paused The paused flag to set.
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Determine whether the game is currently paused.
     *
     * @return <code>true</code> if the game is paused, <code>false</code> otherwise.
     */
    public boolean isPaused() {
        return paused;
    }
}
