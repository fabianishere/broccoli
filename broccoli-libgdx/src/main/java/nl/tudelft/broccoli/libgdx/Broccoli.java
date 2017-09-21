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

package nl.tudelft.broccoli.libgdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.SimpleLevel;
import nl.tudelft.broccoli.libgdx.scene.Context;
import nl.tudelft.broccoli.libgdx.scene.GridActor;
import nl.tudelft.broccoli.defpro.MyConfig;

import javax.swing.*;

/**
 * A {@link Game} implementation which provides a 2d game view over the game logic defined in
 * the <code>broccoli-core</code> package.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Broccoli extends Game {
    /**
     * A {@link Stage} for the scene we want to render.
     */
    private Stage stage;

    /**
     * The {@link GameSession} to render.
     */
    private GameSession session;

    /**
     * The game {@link Context} to use.
     */
    private Context context;

    /**
     * This method is invoked when the {@link Application} is first created.
     */
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        session = new SimpleLevel().create();
        context = new Context();

        GridActor grid = new GridActor(session.getGrid(), context);
        grid.setFillParent(true);
        stage.addActor(grid);

        Gdx.input.setInputProcessor(stage);

        MyConfig config = new MyConfig();
    }

    /**
     * This method is invoked when the window is resized.
     *
     * @param width The new width of the window.
     * @param height The new height of the window.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * This method is invoked when the {@link Application} should (re-)render.
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Check if the game has been won
        if (session.isWon()) {
            // TODO Show message on game screen instead and allow user to start a new game
            JOptionPane.showMessageDialog(new JFrame(), "Congratulations! You won the game",
                "Winner!", JOptionPane.INFORMATION_MESSAGE);
            Gdx.app.exit();
            return;
        }

        // Draw the scene stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * This method is invoked to dispose resources allocated by the game.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
