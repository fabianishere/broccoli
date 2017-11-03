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

import static nl.tudelft.broccoli.libgdx.scene.actions.ScreenActions.push;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.easy.EasyLevelFactory;
import nl.tudelft.broccoli.core.level.hard.HardLevelFactory;
import nl.tudelft.broccoli.core.level.medium.MediumLevelFactory;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * A {@link MenuScreen} representing a start screen.
 *
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class StartScreen extends MenuScreen {
    /**
     * Construct a {@link StartScreen}.
     *
     * @param context The {@link ActorContext} to use.
     */
    public StartScreen(ActorContext context) {
        Table table = getActor();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        Label selectLevel = new Label("Select a level:", labelStyle);
        table.add(selectLevel).padBottom(20);
        table.row();

        Button easy = createButton("Easy", Color.CORAL);
        easy.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                GameSession session = new EasyLevelFactory().create(1)
                    .create(context.getConfiguration());
                addAction(push(new GameScreen(context, session)));
            }
        });
        table.add(easy).width(200).height(50).padBottom(20);
        table.row();

        Button medium = createButton("Medium", Color.CORAL);
        medium.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                GameSession session = new MediumLevelFactory().create(1)
                    .create(context.getConfiguration());
                addAction(push(new GameScreen(context, session)));
            }
        });
        table.add(medium).width(200).height(50).padBottom(20);
        table.row();

        Button hard = createButton("Hard", Color.CORAL);
        hard.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                GameSession session = new HardLevelFactory().create(1)
                    .create(context.getConfiguration());
                addAction(push(new GameScreen(context, session)));
            }
        });
        table.add(hard).width(200).height(50).padBottom(60);
        table.row();

        Button exit = createButton("Quit", Color.GRAY);
        exit.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        table.add(exit).width(200).height(50).padBottom(20);

    }
}
