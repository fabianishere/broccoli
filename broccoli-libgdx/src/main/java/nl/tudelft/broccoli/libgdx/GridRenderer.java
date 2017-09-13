package nl.tudelft.broccoli.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.track.Track;

public class GridRenderer {
    private Grid grid;
    private SpriteBatch spriteBatch;
    private Texture emptyTexture;
    private Texture trackTexture;
    private Texture receptorTecture;
    private Sprite[][] sprites;

    public GridRenderer(GameSession session) {
        this.grid = session.getGrid();
        sprites = new Sprite[grid.getWidth()][grid.getHeight()];
        spriteBatch = new SpriteBatch();
        loadTextures();
        loadSprites();
    }

    private void loadSprites() {
        // Hardcoded width
        // Window should be resizable so it should not be a problem
        int width = 800;
        int x = (width / 2) - 137 * grid.getWidth() / 2;
        int y = 0;

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                Sprite sprite = new Sprite(emptyTexture);

                if (grid.get(i, j).getTileable() instanceof Receptor) {
                    sprite.setTexture(receptorTecture);
                } else if (grid.get(i, j).getTileable() instanceof Track) {
                    sprite.setTexture(trackTexture);
                }

                sprite.setPosition(x , y);
                y += 137;
                sprites[i][j] = sprite;
            }
            x += 137;
            y = 0;
        }
    }

    private void loadTextures() {
        emptyTexture = new Texture(Gdx.files.classpath("sprites/tiles/empty/0.png"));
        trackTexture = new Texture(Gdx.files.classpath("sprites/tiles/cross/0.png"));
        receptorTecture = new Texture(Gdx.files.classpath("sprites/receptor/unmarked/0.png"));
    }

    public void draw() {
        spriteBatch.begin();

        for (Sprite[] tempSprites : sprites) {
            for (Sprite sprite : tempSprites) {
                sprite.draw(spriteBatch);
            }
        }

        spriteBatch.end();
    }
}
