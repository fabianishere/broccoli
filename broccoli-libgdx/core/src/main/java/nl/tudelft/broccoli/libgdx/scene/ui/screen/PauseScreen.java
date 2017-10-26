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

package nl.tudelft.broccoli.libgdx.scene.ui.screen;

import static nl.tudelft.broccoli.libgdx.scene.actions.ScreenActions.pop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An actor in the scene for a pause overlay.
 *
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class PauseScreen extends MenuScreen {
    /**
     * Construct a {@link PauseScreen} instance.
     *
     * @param context The game context.
     */
    public PauseScreen(ActorContext context) {
        this.setBackground(new TextureRegionDrawable(context.getTextureAtlas()
            .findRegion("pauseOverlay")));
        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    addAction(pop());
                    return true;
                }
                return false;
            }
        });

        Table table = getActor();
        table.setFillParent(true);

        Button resume = createButton("Resume", Color.GRAY);
        resume.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                addAction(pop());
            }
        });
        table.add(resume).width(200).height(50).padBottom(100);
        table.row();

        Button toMenu = createButton("Quit and return to start menu", Color.GRAY);
        toMenu.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                addAction(pop(2));
            }
        });

        table.add(toMenu).width(200).height(50);
    }
}
