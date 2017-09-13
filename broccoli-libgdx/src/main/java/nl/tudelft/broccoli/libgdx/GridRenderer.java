package nl.tudelft.broccoli.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.tudelft.broccoli.core.Empty;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.Track;

/**
 * A renderer for the {@link Grid} class using libgdx.
 *
 * @author Christian Slothouber (f.c.slothouber@student.tudelft.nl)
 */
public class GridRenderer {
    private Grid grid;
    private SpriteBatch spriteBatch;

    private Texture emptyTexture;
    private Texture trackTexture;
    private Texture receptorTexture;
    private Texture receptorBackgroundTexture;

    private SpritePacket[][] sprites;

    /**
     * Construct a {@link GridRenderer} instance.
     *
     * @param session The game session to render.
     */
    public GridRenderer(GameSession session) {
        this.grid = session.getGrid();
        sprites = new SpritePacket[grid.getWidth()][grid.getHeight()];
        spriteBatch = new SpriteBatch();
        loadTextures();
        loadSprites();
    }

    /**
     * Draw the grid on screen.
     */
    public void draw() {
        spriteBatch.begin();
        for (SpritePacket[] spriteArray : sprites) {
            for (SpritePacket spritePacket : spriteArray) {
                spritePacket.draw(spriteBatch);
            }
        }
        spriteBatch.end();
    }

    private void loadSprites() {
        // Hardcoded width
        // Window should be resizable so it should not be a problem
        int width = 800;
        int x = (width / 2) - 137 * grid.getWidth() / 2;
        int y = 0;

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                SpritePacket spritePacket = new SpritePacket();
                if (grid.get(i, j).getTileable() instanceof Empty) {
                    spritePacket.addOnTop(emptyTexture);
                }
                if (grid.get(i, j).getTileable() instanceof Receptor) {
                    spritePacket.addOnTop(receptorBackgroundTexture);
                    spritePacket.addOnTop(receptorTexture);
                }
                // This part will be split up in vertical track, horizontal track and so on.
                if (grid.get(i, j).getTileable() instanceof Track) {
                    spritePacket.addOnTop(trackTexture);
                }
                spritePacket.setPosition(x , y);
                y += 137;
                sprites[i][j] = spritePacket;
            }
            x += 137;
            y = 0;
        }
    }

    private void loadTextures() {
        emptyTexture = new Texture(Gdx.files.classpath("sprites/tiles/empty/0.png"));
        trackTexture = new Texture(Gdx.files.classpath("sprites/tiles/cross/0.png"));
        receptorTexture = new Texture(Gdx.files.classpath("sprites/receptor/unmarked/0.png"));
        receptorBackgroundTexture = new Texture(Gdx.files.internal("sprites/tiles/receptor/"
            + "unmarked/0.png"));
    }
}
