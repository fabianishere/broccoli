package nl.tudelft.broccoli.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Deque;
import java.util.LinkedList;

/**
 * A stackable layer of sprites.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class SpritePacket {
    private Deque<Sprite> sprites;

    /**
     * Construct an empty {@link SpritePacket}.
     */
    public SpritePacket() {
        sprites = new LinkedList<Sprite>();
    }

    /**
     * Add a {@link Texture} on top of this stack.
     *
     * @param texture The texture to add.
     */
    public void addOnTop(Texture texture) {
        sprites.addLast(new Sprite(texture));
    }

    /**
     * Add a {@link Texture} on the bottom of this stack.
     *
     * @param texture The texture to add.
     */
    public void addOnBottom(Texture texture) {
        sprites.addFirst(new Sprite(texture));
    }

    /**
     * Set the position of this packet within the scene.
     *
     * @param x The x-coordinate to place the packet at.
     * @param y The y-coordinate to place the packet add.
     */
    public void setPosition(int x, int y) {
        for (Sprite sprite : sprites) {
            sprite.setPosition(x, y);
        }
    }

    /**
     * Draw the packet of sprites with the given batch.
     *
     * @param batch The batch to use.
     */
    public void draw(SpriteBatch batch) {
        for (Sprite sprite : sprites) {
            sprite.draw(batch);
        }
    }
}
