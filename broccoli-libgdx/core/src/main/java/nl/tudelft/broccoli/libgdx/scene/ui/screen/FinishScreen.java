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
import static nl.tudelft.broccoli.libgdx.scene.actions.ScreenActions.replace;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.Level;
import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.core.level.Progress;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * A {@link MenuScreen} representing the finish screen.
 *
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class FinishScreen extends MenuScreen {
    /**
     * Construct a {@link FinishScreen}.
     *
     * @param context The {@link ActorContext} to use.
     * @param session The session that was played.
     */
    public FinishScreen(ActorContext context, GameSession session) {
        Table table = getActor();
        Progress progress = session.getProgress();
        int score = progress.getScore();

        String feedback = "Game over! Time's up";
        if (progress.isWon()) {
            feedback = "Congratulations, you won the game";
        }
        feedback += "\nYour score was " + score;


        table.add(initLabel(feedback)).padBottom(100);
        table.row();
        table.add(initMenuButton()).width(200).height(50).padBottom(100);

        Level level = session.getLevel();
        int index = level.getIndex();
        if (index < 3) {
            table.row();
            table.add(initNextButton(context, level.getFactory(), index + 1))
                .width(200).height(50);
        }
    }

    /**
     * Return the label of the screen.
     *
     * @param text The text of the label.
     * @return The label of the screen.
     */
    private Label initLabel(String text) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();
        return new Label(text, style);
    }

    /**
     * Return the menu button of the screen.
     *
     * @return The menu button of the screen.
     */
    private Button initMenuButton() {
        Button button = createButton("Return to menu", Color.CORAL);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                addAction(pop());
            }
        });

        return button;
    }

    /**
     * Return the menu button of the screen.
     *
     * @return The menu button of the screen.
     */
    private Button initNextButton(ActorContext context, LevelFactory factory, int index) {
        Button button = createButton("Next level", Color.CORAL);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                GameSession session = factory.create(index)
                        .create(context.getConfiguration());
                addAction(replace(new GameScreen(context, session)));
            }
        });

        return button;
    }
}
