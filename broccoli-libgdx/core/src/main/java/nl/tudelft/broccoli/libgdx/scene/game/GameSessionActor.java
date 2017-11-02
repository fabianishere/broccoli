package nl.tudelft.broccoli.libgdx.scene.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.level.GameSession;
import nl.tudelft.broccoli.core.powerup.PowerUp;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.libgdx.scene.ActorContext;
import nl.tudelft.broccoli.libgdx.scene.ui.PauseActor;
import nl.tudelft.broccoli.libgdx.scene.ui.ScoreBoardActor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A {@link WidgetGroup} containing the screens drawn to the window. This actor is composed of the
 * actual game, in combination with the UI of the game.
 *
 * @author Bas Musters (m.b.musters@student.tudelft.nl)
 */
public class GameSessionActor extends Stack {
    /**
     * The game session that is running.
     */
    private final GameSession session;

    /**
     * A pause actor that contains the pause screen.
     */
    private final PauseActor pause;

    /**
     * Construct a {@link GameSessionActor}.
     *
     * @param context The game {@link ActorContext} to use.
     * @param session The game session that is running.
     */
    public GameSessionActor(ActorContext context, GameSession session) {
        super();
        this.session = session;
        this.pause = new PauseActor(context);

        Table table = new Table();
        table.setFillParent(true);
        ScoreBoardActor score = new ScoreBoardActor(context, session.getProgress());
        table.add(score).height(80);
        table.row();
        GridActor grid = new GridActor(context, session.getGrid());
        table.add(grid).expand();

        this.addActor(table);
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

        initPowerUps();
    }

    /**
     * Initialise the {@link PowerUp} assignment in the game.
     */
    private void initPowerUps() {
        List<Receptor> receptors = getReceptors();
        int n = receptors.size();
        int stdev = 10;
        int mu = 30;
        int duration = 20;

        final Random random = new Random();
        final Runnable assign = new Runnable() {
            @Override
            public void run() {
                // Create the power-up to assign
                PowerUp powerUp = session.getPowerUpFactory().create();

                // Find receptor to assign to
                Receptor receptor = receptors.get(random.nextInt(n));
                receptor.setPowerUp(powerUp);

                // Schedule de-assignment of power-up
                addAction(Actions.delay(duration, Actions.run(() -> {
                    if (receptor.getPowerUp() != null && receptor.getPowerUp().equals(powerUp)) {
                        receptor.setPowerUp(null);
                    }
                })));

                // Schedule next assignment of power-up
                int next = (int) random.nextGaussian() * stdev + mu;
                addAction(Actions.delay(next, Actions.run(this)));
            }
        };

        int next = (int) random.nextGaussian() * stdev + mu;
        addAction(Actions.delay(next, Actions.run(assign)));
    }

    /**
     * Get the receptors on the grid.
     *
     * @return The receptors on the grid.
     */
    private List<Receptor> getReceptors() {
        Grid grid = session.getGrid();
        final List<Receptor> receptors = new ArrayList<>();
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                Tileable t = grid.get(i, j).getTileable();
                if (t instanceof Receptor) {
                    receptors.add((Receptor) t);
                }
            }
        }

        return receptors;
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
