package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import nl.tudelft.broccoli.core.TimerTile;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.libgdx.Context;

public class TimerActor extends TileableActor<TimerTile> {
    private Sprite[] sprites = new Sprite[5];
    private Timer timer;
    private int currentTextureId;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context  The game context to use.
     */
    public TimerActor(TimerTile tileable, Context context) {
        super(tileable, context);

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = context.getTextureAtlas().createSprite("counter", i);
        }

        currentTextureId = 0;
        timer = new Timer();
        timer.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                nextTexture();
            }
        }, getTileable().getMaxTime() / 5.f,
                getTileable().getMaxTime() / 5.f, 4);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                // We should create a game over screen instead in future releases
                Gdx.app.exit();
            }
        }, getTileable().getMaxTime());
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

    private void nextTexture() {
        currentTextureId++;
    }

}
