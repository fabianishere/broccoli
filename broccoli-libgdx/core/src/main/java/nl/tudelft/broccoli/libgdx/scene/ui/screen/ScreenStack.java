package nl.tudelft.broccoli.libgdx.scene.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * A stack of screens that simplifies the transitions between these screens.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ScreenStack extends Stack {
    /**
     * Replace the current active view in the screen stack.
     *
     * @param screen The view to replace the current active view with.
     */
    public void replace(Actor screen) {
        Action runnable = Actions.run(() -> {
            if (hasChildren()) {
                getChildren().pop();
            }
            add(screen);
            getStage().setKeyboardFocus(screen);
        });

        addAction(runnable);
    }

    /**
     * Push a view to the screen stack.
     *
     * @param screen The view to push to the view stack.
     */
    public void push(Actor screen) {
        Action runnable = Actions.run(() -> {
            add(screen);
            getStage().setKeyboardFocus(screen);
        });
        addAction(runnable);
    }

    /**
     * Pop the given amount of views from the screen stack.
     *
     * @param n The amount of views to pop from the stack.
     */
    public void pop(int n) {
        addAction(Actions.run(() -> {
            if (!hasChildren() || n == 0) {
                return;
            }

            SnapshotArray<Actor> children = getChildren();
            int size = children.size;
            children.removeRange(Math.max(0, size - n), size - 1);

            if (size - n > 0) {
                getStage().setKeyboardFocus(children.peek());
            }
        }));
    }

    /**
     * Act on the scene updates.
     *
     * @param deltaTime The time delta.
     */
    @Override
    public void act(float deltaTime) {
        Array<Action> actions = this.getActions();
        Stage stage = getStage();
        if (actions.size > 0) {
            if (stage != null) {
                if (stage.getActionsRequestRendering()) {
                    Gdx.graphics.requestRendering();
                }
            }

            for (int i = 0; i < actions.size; i++) {
                Action action = actions.get(i);
                if (action.act(deltaTime)) {
                    actions.removeIndex(i);
                    action.setActor(null);
                    i--;
                }
            }
        }

        if (hasChildren()) {
            // Only act on the top of the stack.
            getChildren().peek().act(deltaTime);
        }
    }
}
