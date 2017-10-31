package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.level.Difficulty;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.libgdx.scene.game.GameActor;
import nl.tudelft.broccoli.libgdx.scene.util.MusicActor;

/**
 * The scene graph of the Broccoli game. This {@link Stage} sets up the root actors of the game,
 * which includes the actor that handles the screens and the actor that handles the music theme
 * of the game.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class GameStage extends Stage {
    /**
     * The game configuration.
     */
    private final Configuration configuration;

    /**
     * The actor context shared between actors.
     */
    private final ActorContext context;

    /**
     * Construct a {@link GameStage} instance.
     *
     * @param configuration The configuration of the stage.
     * @param viewport The viewport of the stage.
     */
    public GameStage(Configuration configuration, Viewport viewport) {
        super(viewport);
        this.configuration = configuration;
        this.context = new ActorContext(
            new TextureAtlas(Gdx.files.classpath("atlas/sprites.atlas")));
        this.addActor(initGame());
        this.addActor(initMusic());
    }

    /**
     * Return the {@link ActorContext} of this stage.
     *
     * @return The context shared between actors.
     */
    public ActorContext getContext() {
        return context;
    }

    /**
     * Initialise the {@link GameActor} that displays the actual game.
     */
    private Actor initGame() {
        GameSession session = new LevelFactory().create(Difficulty.EASY).create(configuration);
        GameActor game = new GameActor(context, session);
        setKeyboardFocus(game);
        return game;
    }

    /**
     * Initialise the {@link MusicActor} that takes care of the background music.
     */
    private Actor initMusic() {
        MusicActor music = new MusicActor();
        music.play(Gdx.audio.newMusic(Gdx.files.classpath("sound/music/placeholder.mp3")));
        return music;
    }
}
