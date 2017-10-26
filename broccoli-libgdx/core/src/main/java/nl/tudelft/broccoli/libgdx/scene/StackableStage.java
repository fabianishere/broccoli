package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import nl.tudelft.broccoli.libgdx.scene.ui.screen.ScreenStack;

/**
 * A {@link Stage} which provides a view stack.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class StackableStage extends Stage {
    /**
     * The screen stack which manages the active screens.
     */
    private final ScreenStack stack;

    /**
     * Construct a {@link StackableStage} instance.
     *
     * @param viewport The viewport of the stage.
     * @param stack    The screen stack to use.
     */
    public StackableStage(Viewport viewport, ScreenStack stack) {
        super(viewport);
        this.stack = stack;
        this.stack.setFillParent(true);
        this.addActor(stack);
    }

    /**
     * Construct a {@link StackableStage} instance.
     *
     * @param viewport The viewport of the stage.
     */
    public StackableStage(Viewport viewport) {
        this(viewport, new ScreenStack());
    }

    /**
     * Return the {@link ScreenStack} of this game stage which manages the transitions between the
     * different screens.
     *
     * @return The screen stack.
     */
    public ScreenStack getScreenStack() {
        return stack;
    }

    /**
     * Replace the current active view in the screen stack.
     *
     * @param screen The view to replace the current active view with.
     */
    public void replace(Actor screen) {
        stack.replace(screen);
    }

    /**
     * Push a view to the screen stack.
     *
     * @param screen The view to push to the view stack.
     */
    public void push(Actor screen) {
        stack.push(screen);
    }

    /**
     * Pop the given amount of views from the screen stack.
     *
     * @param n The amount of views to pop from the stack.
     */
    public void pop(int n) {
        stack.pop(n);
    }
}
