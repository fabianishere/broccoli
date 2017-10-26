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

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import nl.tudelft.broccoli.core.level.Progress;
import nl.tudelft.broccoli.libgdx.Context;

/**
 * This {@link Actor} represents an in-game score board.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class ScoreBoardActor extends Table {

    /**
     * The size of the font.
     */
    private static final float FONT_SIZE = 46f;

    /**
     * The space between each numeral.
     */
    private static final float OFFSET = 0f;

    /**
     * The progress of the session.
     */
    private final Progress progress;

    /**
     * The digit textures.
     */
    private final TextureRegion[] digits = new TextureRegion[10];

    /**
     * The currently represented score on screen.
     */
    private int score = -1;

    /**
     * Constructs a {@link ScoreBoardActor}.
     *
     * @param context The {@link Context} of the current game.
     */
    public ScoreBoardActor(Context context) {
        this.progress = context.getSession().getProgress();

        // Cache the digit textures
        for (int i = 0; i < digits.length; i++) {
            digits[i] = context.getTextureAtlas().findRegion("score/numeral", i);
        }
    }

    /**
     * Return the image corresponding to the given digit.
     *
     * @param digit The digit to get the image of.
     * @return The image corresponding to the given digit.
     */
    private Image getDigit(int digit) {
        Image image = new Image(digits[digit]);
        image.setScaling(Scaling.fill);
        image.setOrigin(Align.center);
        return image;
    }

    /**
     * Updates the images to the new score.
     */
    private void updateSprites() {
        clearChildren();
        score = progress.getScore();

        for (char c : Integer.toString(score).toCharArray()) {
            add(getDigit(Character.digit(c, 10))).size(FONT_SIZE).padRight(OFFSET);
        }

        score = progress.getScore();
    }

    /**
     * Act on the scene updates.
     *
     * @param deltaTime The time delta.
     */
    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);

        if (score != progress.getScore()) {
            updateSprites();
        }
    }
}
