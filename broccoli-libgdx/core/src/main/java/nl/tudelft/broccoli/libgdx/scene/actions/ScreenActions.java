package nl.tudelft.broccoli.libgdx.scene.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.broccoli.libgdx.scene.StackableStage;

import java.util.function.Consumer;

/**
 * This class provides several action builders for replace between screens.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public final class ScreenActions {
    /**
     * Prevent the class from being constructed.
     */
    private ScreenActions() {}

    /**
     * Replace the current active view in the screen stack.
     *
     * @param screen The view to replace the current active view with.
     */
    public static Action replace(Actor screen) {
        return run(stage -> ((StackableStage) stage).replace(screen));
    }

    /**
     * Push a view to the screen stack.
     *
     * @param screen The view to push to the view stack.
     */
    public static Action push(Actor screen) {
        return run(stage -> ((StackableStage) stage).push(screen));
    }

    /**
     * Pop a view from the screen stack.
     */
    public static Action pop() {
        return pop(1);
    }

    /**
     * Pop the given amount of views from the screen stack.
     *
     * @param n The amount of views to pop from the stack.
     */
    public static Action pop(int n) {
        return run(stage -> ((StackableStage) stage).pop(n));
    }

    /**
     * Run the given function of the game stage.
     *
     * @param f The logic to run as a function of the game stage.
     */
    private static Action run(Consumer<Stage> f) {
        return new Action() {
            @Override
            public boolean act(float delta) {
                f.accept(getActor().getStage());
                return true;
            }
        };
    }

}
