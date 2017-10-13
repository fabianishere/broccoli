/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Fabian Mastenbroek, Christian Slothouber,
 * Earth Grob, Matthijs Rijm, Bas Musters
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nl.tudelft.broccoli.core.receptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for testing the {@link Receptor} class.
 */
public class ReceptorTest {
    /**
     * The receptor under test.
     */
    private Receptor receptor;

    /**
     * The marble to use.
     */
    private Marble marble;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        receptor = new Receptor();
        marble = Marble.of(Marble.Type.GREEN);
    }

    /**
     * Test if rotating once gives rotation one.
     */
    @Test
    public void rotateOnce() {
        receptor.rotate(1);
        assertThat(receptor.getRotation()).isEqualTo(1);
    }

    /**
     * Test if not rotating gives rotation zero.
     */
    @Test
    public void rotateZeroTimes() {
        receptor.rotate(0);
        assertThat(receptor.getRotation()).isEqualTo(0);
    }

    /**
     * Test if rotating four times gives rotation zero.
     */
    @Test
    public void rotateFourTimes() {
        receptor.rotate(4);
        assertThat(receptor.getRotation()).isEqualTo(0);
    }

    /**
     * Test if the receptor starts as marked.
     */
    @Test
    public void notMarkedOnStart() {
        Receptor receptor = new Receptor();
        assertThat(receptor.isMarked()).isFalse();
    }

    /**
     * Test if the receptor can accept a ball when it is empty.
     */
    @Test
    public void acceptsWhenSlotEmpty() {
        Receptor receptor = new Receptor();
        assertThat(receptor.accepts(Direction.LEFT, marble)).isTrue();
    }

    /**
     * Test if the receptor allows a connection from the left.
     */
    @Test
    public void allowsConnectionLeft() {
        assertThat(receptor.allowsConnection(Direction.LEFT)).isTrue();
    }

    /**
     * Test if the receptor allows a connection from the right.
     */
    @Test
    public void allowsConnectionRight() {
        assertThat(receptor.allowsConnection(Direction.RIGHT)).isTrue();
    }

    /**
     * Test if the receptor allows a connection from the top.
     */
    @Test
    public void allowsConnectionTop() {
        assertThat(receptor.allowsConnection(Direction.TOP)).isTrue();
    }

    /**
     * Test if the receptor allows a connection from the top.
     */
    @Test
    public void allowsConnectionBottom() {
        assertThat(receptor.allowsConnection(Direction.BOTTOM)).isTrue();
    }

    /**
     * Test if the receptor accepts a ball in an empty slot.
     */
    @Test
    public void acceptBall() {
        Marble marble = Marble.of(Marble.Type.BLUE);
        receptor.accept(Direction.TOP, marble);
        assertThat(receptor.getSlot(Direction.TOP).getMarble()).isEqualTo(marble);
    }

    /**
     * Test if the receptor does not accept a ball in an occupied slot.
     */
    @Test
    public void notAcceptWhenSlotOccupied() {
        receptor.accept(Direction.TOP, Marble.of(Marble.Type.BLUE));
        assertThat(receptor.accepts(Direction.TOP, marble)).isFalse();
    }

    /**
     * Test if the receptor does not accept a marble when locked.
     */
    @Test
    public void notAcceptWhenLocked() {
        receptor.lock();
        assertThat(receptor.accepts(Direction.TOP, marble)).isFalse();
    }

    /**
     * Test is the locking of a receptor succeeds.
     */
    @Test
    public void lock() {
        receptor.lock();
        assertThat(receptor.isLocked()).isTrue();
    }

    /**
     * Test is the locking twice of a receptor succeeds.
     */
    @Test
    public void lockTwice() {
        receptor.lock();
        receptor.lock();
        assertThat(receptor.isLocked()).isTrue();
    }

    /**
     * Test is the unlocking of a receptor succeeds.
     */
    @Test
    public void unlock() {
        receptor.unlock();
        assertThat(receptor.isLocked()).isFalse();
    }

    /**
     * Test is the unlocking twice of a receptor succeeds.
     */
    @Test
    public void unlockTwice() {
        receptor.unlock();
        receptor.unlock();
        assertThat(receptor.isLocked()).isFalse();
    }

    /**
     * Test if a locked receptor is not releasable.
     */
    @Test
    public void lockedReceptorIsNotReleasable() {
        receptor.lock();
        Grid grid = new Grid(2, 1);
        grid.place(0, 0, new HorizontalTrack());
        grid.place(1, 0, receptor);
        assertThat(receptor.isReleasable(Direction.LEFT, marble)).isFalse();
    }

    /**
     * Test if an unlocked receptor is not releasable.
     */
    @Test
    public void unlockedReceptorIsNotReleasable() {
        Grid grid = new Grid(1, 1);
        grid.place(0, 0, receptor);
        assertThat(receptor.isReleasable(Direction.LEFT, marble)).isFalse();
    }

    /**
     * Test if a slot is releasable.
     */
    @Test
    public void isReleasable() {
        Grid grid = new Grid(2, 1);
        grid.place(0, 0, new HorizontalTrack());
        grid.place(1, 0, receptor);
        assertThat(receptor.isReleasable(Direction.LEFT, marble)).isTrue();
    }

    /**
     * Test if the receptor fails when giving a ball to an occupied slot.
     */
    @Test
    public void acceptWhenSlotOccupied() {
        receptor.accept(Direction.TOP, Marble.of(Marble.Type.BLUE));
        assertThatThrownBy(() -> receptor.accept(Direction.TOP, Marble.of(Marble.Type.BLUE)))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The slot is already occupied");
    }

    /**
     * Test if the receptor fails when giving a ball to a slot of a locked receptor.
     */
    @Test
    public void acceptWhenLocked() {
        receptor.lock();
        assertThatThrownBy(() -> receptor.accept(Direction.TOP, Marble.of(Marble.Type.BLUE)))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The receptor is locked");
    }

    /**
     * Test if the slot correctly returns its receptor.
     */
    @Test
    public void getReceptor() {
        assertThat(receptor.getSlot(Direction.LEFT).getReceptor()).isEqualTo(receptor);
    }

    /**
     * Test if releasing the marble of an unoccupied slot fails.
     */
    @Test
    public void releaseUnoccupiedSlot() {
        Slot slot = receptor.getSlot(Direction.LEFT);
        assertThatThrownBy(slot::release)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The slot is not occupied");
    }

    /**
     * Test if releasing the marble of an unoccupied slot fails.
     */
    @Test
    public void releaseUnplacedSlot() {
        Slot slot = receptor.getSlot(Direction.LEFT);
        receptor.accept(Direction.LEFT, Marble.of(Marble.Type.BLUE));
        assertThatThrownBy(slot::release)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The receptor is not placed on a grid");
    }

    /**
     * Test if releasing the marble of an unoccupied slot fails.
     */
    @Test
    public void releaseLocked() {
        Grid grid = new Grid(1, 1);
        grid.place(0, 0, receptor);
        Slot slot = receptor.getSlot(Direction.LEFT);
        receptor.accept(Direction.LEFT, Marble.of(Marble.Type.BLUE));
        receptor.lock();
        assertThatThrownBy(slot::release)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The receptor is locked");
    }

    /**
     * Test if releasing the marble of an unoccupied slot fails.
     */
    @Test
    public void releaseDisconnected() {
        Direction direction = Direction.LEFT;
        Tileable tileable = mock(Tileable.class);
        when(tileable.allowsConnection(direction.inverse())).thenReturn(false);
        when(tileable.accepts(direction.inverse(), marble)).thenReturn(true);

        Grid grid = new Grid(2, 1);
        grid.place(0, 0, tileable);
        grid.place(1, 0, receptor);

        Marble marble = Marble.of(Marble.Type.BLUE);
        Slot slot = receptor.getSlot(direction);

        receptor.accept(Direction.LEFT, marble);

        assertThatThrownBy(slot::release)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The slot cannot release the marble to its neighbor");
    }

    /**
     * Test if releasing the marble of an occupied slot fails.
     */
    @Test
    public void releaseUnreleasable() {
        Direction direction = Direction.LEFT;
        Tileable tileable = mock(Tileable.class);
        when(tileable.allowsConnection(direction.inverse())).thenReturn(true);
        when(tileable.accepts(direction.inverse(), marble)).thenReturn(false);

        Grid grid = new Grid(2, 1);
        grid.place(0, 0, tileable);
        grid.place(1, 0, receptor);

        Marble marble = Marble.of(Marble.Type.BLUE);
        Slot slot = receptor.getSlot(direction);

        receptor.accept(Direction.LEFT, marble);

        assertThatThrownBy(slot::release)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The slot cannot release the marble to its neighbor");
    }

    /**
     * Test if releasing the marble of an occupied slot fails.
     */
    @Test
    public void releaseDisconnectedAndUnreleasable() {
        Direction direction = Direction.LEFT;
        Tileable tileable = mock(Tileable.class);
        when(tileable.allowsConnection(direction.inverse())).thenReturn(false);
        when(tileable.accepts(direction.inverse(), marble)).thenReturn(false);

        Grid grid = new Grid(2, 1);
        grid.place(0, 0, tileable);
        grid.place(1, 0, receptor);

        Marble marble = Marble.of(Marble.Type.BLUE);
        Slot slot = receptor.getSlot(direction);

        receptor.accept(Direction.LEFT, marble);

        assertThatThrownBy(slot::release)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("The slot cannot release the marble to its neighbor");
    }

    /**
     * Test if releasing the marble of an unoccupied slot fails.
     */
    @Test
    public void release() {
        TileableListener listener = mock(TileableListener.class);
        Grid grid = new Grid(2, 1);
        grid.place(0, 0, new HorizontalTrack());
        grid.place(1, 0, receptor);

        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.LEFT;

        receptor.addListener(listener);
        receptor.accept(direction, marble);
        receptor.getSlot(direction).release();
        verify(listener, times(1)).ballReleased(receptor, direction, marble);
    }
}
