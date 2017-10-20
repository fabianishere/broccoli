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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.tudelft.broccoli.core.config.*;
import nl.tudelft.broccoli.core.level.Difficulty;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.core.level.Progress;
import nl.tudelft.broccoli.libgdx.scene.GridActor;

import javax.swing.*;

/**
 * A {@link Game} implementation which provides a 2d game view over the game logic defined in
 * the <code>broccoli-core</code> package.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class Broccoli extends Game {
    /**
     * The width of the window.
     */
    public static final Property<Integer> WINDOW_WIDTH =
        new BoundedProperty<>(new IntegerProperty("window.width", 800), 0, Integer.MAX_VALUE);

    /**
     * The height of the window.
     */
    public static final Property<Integer> WINDOW_HEIGHT =
        new BoundedProperty<>(new IntegerProperty("window.height", 480), 0, Integer.MAX_VALUE);

    /**
     * The title of the window.
     */
    public static final Property<String> WINDOW_TITLE =
        new StringProperty("window.title", "Broccoli");

    /**
     * The game {@link Configuration} to use.
     */
    private final Configuration config;

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
     * Boolean paused.
     */
    private boolean paused = false;

    /**
     * Game progress.
     */
    private Progress progress;

    /**
     * Construct a {@link Broccoli} instance.
     *
     * @param config The game configuration to use.
     */
    public Broccoli(Configuration config) {
        this.config = config;
    }

    /**
     * This method is invoked when the {@link Application} is first created.
     */
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        session = new LevelFactory().create(Difficulty.EASY).create(config);
        context = new Context(config, new TextureAtlas(Gdx.files.classpath("atlas/sprites.atlas")));
        progress = new Progress(session.getGrid());

        GridActor grid = new GridActor(session.getGrid(), context);
        grid.setFillParent(true);
        stage.addActor(grid);


        Gdx.input.setInputProcessor(stage);

        Gdx.graphics.setWindowedMode(config.get(WINDOW_WIDTH), config.get(WINDOW_HEIGHT));
        Gdx.graphics.setTitle(config.get(WINDOW_TITLE));

        new BackgroundMusic("sound/music/placeholder.mp3");
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
        if (progress.isWon()) {
            // TODO Show message on game screen instead and allow user to start a new game
            JOptionPane.showMessageDialog(new JFrame(), "Congratulations! You won the game "
                    +  "and earned " + session.getProgress().getScore() + " points.",
                "Winner!", JOptionPane.INFORMATION_MESSAGE);
            Gdx.app.exit();
            return;
        }



        if (!paused) {
            // Draw the scene stage
            stage.act(Gdx.graphics.getDeltaTime());
        }

        stage.draw();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            Gdx.input.setInputProcessor(paused ? null : stage);
        }
    }



    /**
     * This method is invoked to dispose resources allocated by the game.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
