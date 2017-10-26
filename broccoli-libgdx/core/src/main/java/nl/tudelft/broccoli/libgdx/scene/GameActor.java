package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import nl.tudelft.broccoli.libgdx.Context;

/**
 * A {@link WidgetGroup} containing the screens drawn to the window. This actor is composed of the
 * actual game, in combination with the UI of the game.
 *
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class GameActor extends WidgetGroup {
    /**
     * The game {@link Context} to use.
     */
    private final Context context;

    /**
     * A pause actor that contains the pause screen.
     */
    private final PauseActor pause;

    /**
     * Construct a {@link GameActor}.
     *
     * @param context The game {@link Context} to use.
     */
    public GameActor(Context context) {
        super();
        this.context = context;
        this.pause = new PauseActor(context);
        this.setFillParent(true);


        Table table = new Table();
        table.setFillParent(true);
        ScoreBoardActor score = new ScoreBoardActor(context);
        table.add(score).height(80);
        table.row();
        GridActor grid = new GridActor(context);
        table.add(grid).expand();

        addActor(table);

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    pause.setPaused(!pause.isPaused());

                    if (pause.isPaused()) {
                        // Do not propagate input to the game
                        grid.setTouchable(Touchable.disabled);
                        addActor(pause);
                    } else {
                        grid.setTouchable(Touchable.enabled);
                        removeActor(pause);
                    }
                }
                return !pause.isPaused();
            }
        });

    }

    /**
     * Act on the scene updates.
     *
     * @param deltaTime The time delta.
     */
    @Override
    public void act(float deltaTime) {
        if (pause.isPaused()) {
            return;
        }
        super.act(deltaTime);
    }
}
