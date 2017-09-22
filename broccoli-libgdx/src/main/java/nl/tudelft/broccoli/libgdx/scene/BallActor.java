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
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Ball;

/**
 * An {@link Actor} that represents an in-game ball.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class BallActor extends Actor {
    /**
     * The texture for a blue ball.
     */
    private static final Texture TX_BALL_BLUE =
        new Texture(Gdx.files.classpath("sprites/marbles/blue/0.png"));

    /**
     * The texture for a green ball.
     */
    private static final Texture TX_BALL_GREEN =
        new Texture(Gdx.files.classpath("sprites/marbles/green/0.png"));

    /**
     * The texture for a pink ball.
     */
    private static final Texture TX_BALL_PINK =
        new Texture(Gdx.files.classpath("sprites/marbles/pink/0.png"));

    /**
     * The texture for a yellow ball.
     */
    private static final Texture TX_BALL_YELLOW =
        new Texture(Gdx.files.classpath("sprites/marbles/yellow/0.png"));

    /**
     * The texture for a joker ball.
     */
    private static final Texture TX_BALL_JOKER =
            new Texture(Gdx.files.classpath("sprites/marbles/joker/0.png"));

    /**
     * The ball of this actor.
     */
    private Ball ball;

    /**
     * The game context of the actor.
     */
    private Context context;

    /**
     * The {@link Texture} of the ball.
     */
    private Texture texture;

    /**
     * Construct a {@link BallActor} instance.
     *
     * @param ball The ball to create this actor for.
     * @param context The context of the actor.
     */
    public BallActor(Ball ball, Context context) {
        this.ball = ball;
        this.context = context;
        this.context.register(ball, this);
        this.texture = findTexture();
        this.setUserObject(ball);
        this.setSize(texture.getWidth(), texture.getHeight());
        this.setOrigin(Align.center);
    }

    /**
     * Return the {@link Ball} of this actor.
     *
     * @return The ball of this actor.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Find the correct {@link Texture} for the ball.
     *
     * @return A texture for the ball.
     */
    private Texture findTexture() {
        switch (ball.getType()) {
            case BLUE:
                return TX_BALL_BLUE;
            case GREEN:
                return TX_BALL_GREEN;
            case PINK:
                return TX_BALL_PINK;
            case YELLOW:
                return TX_BALL_YELLOW;
            case JOKER:
                return TX_BALL_JOKER;
            default:
                // XXX Fix this case
                return TX_BALL_BLUE;
        }
    }

    /**
     * Draw the tile onto the screen.
     *
     * @param batch The batch to use.
     * @param parentAlpha The alpha of the parent.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
            getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
            texture.getWidth(), texture.getHeight(), false, false);

        super.draw(batch, parentAlpha);
    }
}
