package nl.tudelft.broccoli.libgdx.scene.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * This {@link Actor} manages the music of the game. By default it will loop and play immediately
 * when constructed.
 *
 * @author Christian Slothouber {f.c.slothouber@student.tudelft.nl}
 */
public class MusicActor extends Actor {
    /**
     * The current music theme that is playing.
     */
    private Music theme;

    /**
     * Play the given {@link Music} theme. If there is already a theme playing, it will stop and be
     * replaced by the new music theme,
     *
     * @param theme The music theme to play.
     */
    public void play(Music theme) {
        if (this.theme != null) {
            this.theme.stop();
        }

        this.theme = theme;
        this.theme.setLooping(true);
        this.theme.play();
    }
}
