package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.level.LevelFactory;
import nl.tudelft.broccoli.libgdx.scene.ui.screen.StartScreen;
import nl.tudelft.broccoli.libgdx.scene.util.MusicActor;

/**
 * The scene graph of the Broccoli game. This {@link Stage} sets up the root actors of the game,
 * which includes the actor that handles the screens and the actor that handles the music theme
 * of the game.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class GameStage extends StackableStage {
    /**
     * The actor context shared between actors.
     */
    private final ActorContext context;

    /**
     * The actor which manages the music.
     */
    private final MusicActor music;

    /**
     * Construct a {@link GameStage} instance.
     *
     * @param configuration The configuration of the stage.
     * @param viewport      The viewport of the stage.
     * @param factory       The level factory to construct the levels with.
     */
    public GameStage(Viewport viewport, Configuration configuration, LevelFactory factory) {
        super(viewport);
        this.context = new ActorContext(configuration, new TextureAtlas(
            Gdx.files.classpath("atlas/sprites.atlas")));

        this.music = initMusic();
        this.addActor(music);
        this.getScreenStack().replace(new StartScreen(context, factory));
    }

    /**
     * Dispose this stage.
     */
    @Override
    public void dispose() {
        super.dispose();

        // Dispose the texture atlas
        context.getTextureAtlas().dispose();

        // Dispose the music theme
        music.dispose();
    }

    /**
     * Initialise the {@link MusicActor} that takes care of the background music.
     */
    private MusicActor initMusic() {
        MusicActor music = new MusicActor();
        music.play(Gdx.audio.newMusic(Gdx.files.classpath("sound/music/placeholder.mp3")));
        return music;
    }
}
