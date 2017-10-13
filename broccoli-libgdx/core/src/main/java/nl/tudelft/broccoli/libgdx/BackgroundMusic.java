package nl.tudelft.broccoli.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * This class represents the music on the background. by default it will loop and play
 * immediately when constructed.
 *
 * @author Christian Slothouber {f.c.slothouber@student.tudelft.nl}
 */
public class BackgroundMusic {

    /**
     * The constructor of the {@link BackgroundMusic}.
     *
     * @param path The url to the music file to be played.
     */
    public BackgroundMusic(String path) {
        Music music = Gdx.audio.newMusic(Gdx.files.classpath(path));
        music.setLooping(true);
        music.play();
    }
}
