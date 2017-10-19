package nl.tudelft.broccoli.core.level;

import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.receptor.Receptor;
import nl.tudelft.broccoli.core.receptor.ReceptorListener;

import java.util.HashSet;
import java.util.Set;

/**
 * This class checks the progress of the game by keeping all unmarked receptors in a set.
 *
 * @author Matthijs Rijm (m.rijm@student.tudelft.nl)
 */
public class Progress implements ReceptorListener {
    /**
     * The score of the player.
     */
    private int score = 0;

    /**
     * Set with all unmarked receptors, when it is empty the game is won.
     */
    private Set<Receptor> unmarked = new HashSet<>();

    /**
     * Construct a Progress object.
     *
     * @param grid Grid from which the receptors are taken at the start of the game.
     */
    public Progress(Grid grid) {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                Tileable t = grid.get(i, j).getTileable();
                if (t instanceof Receptor) {
                    unmarked.add((Receptor) t);
                    t.addListener(this);
                }
            }
        }
    }

    /**
     * Return if the set is empty, which means the game is won.
     *
     * @return <code>true</code> if the game is won, <code>false</code> if receptors are yet to be
     *         marked.
     */
    public boolean isWon() {
        return unmarked.isEmpty();
    }

    /**
     * Return the current score of the player.
     *
     * @return The current score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Replace the current score of the player with a given score.
     *
     * @param score The score the current score should be set to.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Add an amount of points to the current score.
     *
     * @param points The amount of points to be added.
     */
    public void score(int points) {
        score += points;
    }

    /**
     * This method is invoked when a {@link Receptor} was marked.
     *
     * @param receptor The receptor that has been marked.
     */
    @Override
    public void receptorMarked(Receptor receptor) {
        unmarked.remove(receptor);
    }
}
