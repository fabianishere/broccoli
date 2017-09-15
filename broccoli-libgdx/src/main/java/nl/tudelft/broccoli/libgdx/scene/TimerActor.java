package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import nl.tudelft.broccoli.core.TimerTile;

public class TimerActor extends TileableActor<TimerTile> {
    private Timer timer;
    private int currentTextureId;

    private static final Texture TX_TILE_FULL =
            new Texture(Gdx.files.classpath("sprites/counter/4.png"));
    private static final Texture TX_TILE_THREE_QUARTER =
            new Texture(Gdx.files.classpath("sprites/counter/3.png"));
    private static final Texture TX_TILE_HALF_FULL =
            new Texture(Gdx.files.classpath("sprites/counter/2.png"));
    private static final Texture TX_TILE_QUARTER =
            new Texture(Gdx.files.classpath("sprites/counter/1.png"));
    private static final Texture TX_TILE_EMPTY =
            new Texture(Gdx.files.classpath("sprites/counter/0.png"));

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context  The game context to use.
     */
    public TimerActor(TimerTile tileable, Context context) {
        super(tileable, context);

        currentTextureId = 0;
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                nextTexture();
            }
        }, getTileable().getMaxTime() / 5,
                getTileable().getMaxTime() / 5, 4);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                System.out.println("Game Over!");
                System.exit(0);
            }
        }, getTileable().getMaxTime());
    }

    private void nextTexture() {
        currentTextureId++;
    }

    @Override
    public Texture getTileTexture() {
        switch (currentTextureId) {
            case 0:
                return TX_TILE_FULL;
            case 1:
                return TX_TILE_THREE_QUARTER;
            case 2:
                return TX_TILE_HALF_FULL;
            case 3:
                return TX_TILE_QUARTER;
        }
        return TX_TILE_EMPTY;
    }
}
