package nl.tudelft.broccoli.libgdx.scene.ui.screen;

import static nl.tudelft.broccoli.libgdx.scene.actions.ScreenActions.push;
import static nl.tudelft.broccoli.libgdx.scene.actions.ScreenActions.replace;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
import nl.tudelft.broccoli.libgdx.scene.game.GridActor;
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
public class GameScreen extends Stack {
    /**
     * The game session that is running.
     */
    private final GameSession session;

    /**
     * The actor context that is being used.
     */
    private final ActorContext context;

    /**
     * Construct a {@link GameScreen}.
     *
     * @param context The game {@link ActorContext} to use.
     * @param session The game session that is running.
     */
    public GameScreen(ActorContext context, GameSession session) {
        super();
        this.session = session;
        this.context = context;

        Table table = new Table();
        table.setFillParent(true);
        ScoreBoardActor score = new ScoreBoardActor(context, session.getProgress());
        table.add(score).height(80);
        table.row();
        GridActor grid = new GridActor(context, session.getGrid());
        table.add(grid).expand();
        session.getProgress().track(session.getGrid());

        this.addActor(table);
        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    addAction(push(new PauseScreen(context)));
                    return true;
                }
                return false;
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
     * Method that is checked every tick.
     *
     * @param delta Time delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        if (session.getProgress().isWon()) {
            addAction(replace(new FinishScreen(context, session)));
        }
    }
}
