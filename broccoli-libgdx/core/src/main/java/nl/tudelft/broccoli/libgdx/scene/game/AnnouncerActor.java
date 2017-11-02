package nl.tudelft.broccoli.libgdx.scene.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import nl.tudelft.broccoli.core.Announcer;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;

/**
 * An actor for the {@link Announcer} tile that announces the next marble to be spawned.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class AnnouncerActor extends TileableActor<Announcer> {
    /**
     * The {@link TextureRegion} array containing the announcer sprites.
     */
    private Array<? extends TextureRegion> sprites;

    /**
     * The index of in the sprite array.
     */
    private int index = 0;

    /**
     * Construct a {@link TileableActor} instance.
     *
     * @param tileable The tileable entity to create the actor for.
     * @param context  The game context to use.
     */
    public AnnouncerActor(Announcer tileable, ActorContext context) {
        super(tileable, context);
        this.sprites = context.getTextureAtlas().findRegions("announcer");
    }

    /**
     * Return the tile {@link TextureRegion} for this {@link Tileable}.
     *
     * @return The tile texture.
     */
    @Override
    public TextureRegion getTileTexture() {
        return sprites.get(index);
    }

    /**
     * Act on the scene updates.
     *
     * @param deltaTime The time delta.
     */
    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        this.index = getTileable().getTile().getGrid().getSession().getNexusContext().peek()
            .ordinal();
    }
}
