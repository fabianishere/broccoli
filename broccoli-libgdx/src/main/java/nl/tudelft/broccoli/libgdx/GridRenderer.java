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
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                Sprite sprite = new Sprite(emptyTexture);
                if (grid.get(x, y).getTileable() instanceof Receptor) {
                    sprite.setTexture(receptorTecture);
                }
                if (grid.get(x, y).getTileable() instanceof Track) {
                    sprite.setTexture(trackTexture);
                }
                sprite.setPosition(x * 137, y * 137);
                sprites[x][y] = sprite;
            }
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
