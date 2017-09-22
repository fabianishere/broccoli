package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import nl.tudelft.broccoli.core.TimerTile;

public class TimerActor extends TileableActor<TimerTile> {
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

        currentTextureId = 0;
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                nextTexture();
            }
        }, getTileable().getMaxTime() / 4.f,
                getTileable().getMaxTime() / 4.f, 3);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                // We should create a game over screen instead in future releases
                Gdx.app.exit();
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
            default:
                return TX_TILE_EMPTY;
        }
    }
}
