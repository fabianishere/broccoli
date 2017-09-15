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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import nl.tudelft.broccoli.core.Ball;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.receptor.Slot;

import java.util.EnumMap;

/**
 * An {@link Actor} node in the 2d scene which represents a {@link Receptor}.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ReceptorActor extends TileableActor<Receptor> implements TileableListener {
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
     * A map which maps the direction of slots to their positions on the receptor.
     */
    private EnumMap<Direction, Vector2> positions = new EnumMap<>(Direction.class);


    /**
     * Construct a {@link ReceptorActor} instance.
     *
     * @param receptor The receptor this actor represents.
     * @param context The context of the actor.
     */
    public ReceptorActor(Receptor receptor, Context context) {
        super(receptor, context);
        receptor.addListener(this);
        setSize(TX_RECEPTOR_MARKED.getWidth(), TX_RECEPTOR_MARKED.getHeight());
        setOrigin(Align.center);
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

        float middleX = getWidth() / 2.f;
        float middleY = getHeight() / 2.f;
        float offset = 25.f;
        positions.put(Direction.TOP, new Vector2(middleX,  getHeight() - offset));
        positions.put(Direction.BOTTOM, new Vector2(middleX, offset));
        positions.put(Direction.LEFT, new Vector2(offset, middleY));
        positions.put(Direction.RIGHT, new Vector2(getWidth() - offset, middleY));

        for (Direction direction : Direction.values()) {
            Slot slot = receptor.getSlot(direction);

            if (!slot.isOccupied()) {
                continue;
            }

            ballAccepted(getTileable(), direction, slot.getBall());
        }
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

    /**
     * This method is invoked when a {@link Tileable} has disposed a ball.
     *
     * @param tileable  The tileable that has disposed the ball.
     * @param direction The direction from which the ball was disposed.
     * @param ball      The ball that has been disposed.
     */
    @Override
    public void ballDisposed(Tileable tileable, Direction direction, Ball ball) {
        Actor actor = getContext().actor(ball);

        if (actor != null) {
            actor.remove();
        }
    }

    /**
     * This method is invoked when a {@link Tileable} has accepted a ball.
     *
     * @param tileable The tileable that has accepted the ball.
     * @param direction The direction from which the ball was accepted.
     * @param ball The ball that has been accepted.
     */
    @Override
    public void ballAccepted(Tileable tileable, Direction direction, Ball ball) {
        Receptor receptor = getTileable();
        Vector2 position = positions.get(direction.rotate(-receptor.getRotation()));
        Slot slot = receptor.getSlot(direction);

        // Get the actor for the ball or create a new one if one does not exist yet.
        Actor registry = getContext().actor(ball);
        Actor actor = registry != null ? registry : new BallActor(ball, getContext());

        actor.setPosition(position.x, position.y, Align.center);
        actor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (receptor.isReleasable(slot.getDirection())) {
                    actor.addAction(Actions.run(() -> {
                        actor.removeListener(this);
                        slot.release();
                    }));
                }

                // Stop the event from propagating
                event.stop();
                return true;
            }
        });
        addActor(actor);
    }
}
