package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.libgdx.Context;

public class TimerActor extends TileableActor<TimerTile> {
    private Sprite[] sprites = new Sprite[5];
    private int currentTextureId;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context  The game context to use.
     */
    public TimerActor(TimerTile tileable, Context context) {
        super(tileable, context);

        float delay = getTileable().getMaxTime() / 5.f;
        addAction(Actions.sequence(
            Actions.repeat(4, Actions.sequence(
                Actions.delay(delay),
                Actions.run(() -> currentTextureId++)
            )),
            Actions.run(() -> Gdx.app.exit())
        ));

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = context.getTextureAtlas().createSprite("counter", i);
        }
    }

    /**
     * Return the tile {@link Sprite} for this {@link Tileable}.
     *
     * @return The tile sprite.
     */
    @Override
    public Sprite getTileSprite() {
        int index = sprites.length - currentTextureId - 1;
        if (index < 0) {
            return sprites[0];
        }
        return sprites[index];
    }
}
