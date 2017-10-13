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

package nl.tudelft.broccoli.core.track;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.TileableListener;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class that tests the {@link VerticalTrack} class.
 */
public class VerticalTrackTest {
    /**
     * The track under test.
     */
    private VerticalTrack track;

    /**
     * The tested marble.
     */
    private Marble marble;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        track = new VerticalTrack();
        marble = Marble.of(Marble.Type.GREEN);
    }

    /**
     * Test if an unconnected rail returns false.
     */
    @Test
    public void isNotConnected() {
        Grid grid = new Grid(1, 1);
        grid.place(0, 0, track);
        assertThat(track.isConnected()).isFalse();
    }

    /**
     * Test if an unconnected rail returns false.
     */
    @Test
    public void isNotConnectedBottom() {
        Grid grid = new Grid(1, 2);
        grid.place(0, 0, new VerticalTrack());
        grid.place(0, 1, track);
        assertThat(track.isConnected()).isFalse();
    }

    /**
     * Test if an unconnected rail returns false.
     */
    @Test
    public void isNotConnectedTop() {
        Grid grid = new Grid(1, 2);
        grid.place(0, 0, track);
        grid.place(0, 1, new HorizontalTrack());
        assertThat(track.isConnected()).isFalse();
    }

    /**
     * Test if an unplaced rail throws an exception when checking if it is connected.
     */
    @Test
    public void unplacedNotConnected() {
        assertThatThrownBy(() -> track.isConnected()).isInstanceOf(IllegalStateException.class);
    }

    /**
     * Test if a connected rail returns true.
     */
    @Test
    public void isConnected() {

        Grid grid = new Grid(1, 3);
        grid.place(0, 0, new VerticalTrack());
        grid.place(0, 1, track);
        grid.place(0, 2, new VerticalTrack());

        assertThat(track.isConnected()).isTrue();
    }

    /**
     * Test if a track allows a connection from the top.
     */
    @Test
    public void allowsTop() {
        assertThat(track.allowsConnection(Direction.TOP)).isTrue();
    }

    /**
     * Test if a track allows a connection from the bottom.
     */
    public void allowsBottom() {
        assertThat(track.allowsConnection(Direction.BOTTOM)).isTrue();
    }

    /**
     * Test if a track does not allow a connection from the left.
     */
    @Test
    public void disallowsLeft() {
        assertThat(track.allowsConnection(Direction.LEFT)).isFalse();
    }

    /**
     * Test if a track does not accept a ball from the right.
     */
    @Test
    public void disallowsBottom() {
        assertThat(track.allowsConnection(Direction.RIGHT)).isFalse();
    }

    /**
     * Test if a track does not allow a connection from direction <code>null</code>.
     */
    @Test
    public void disallowsNull() {
        assertThatThrownBy(() -> track.allowsConnection(null))
            .isInstanceOf(NullPointerException.class);
    }

    /**
     * Test if a track accepts a ball from the top.
     */
    @Test
    public void acceptsTop() {
        assertThat(track.accepts(Direction.TOP, marble)).isTrue();
    }

    /**
     * Test if a track accepts a ball from the bottom.
     */
    @Test
    public void acceptsBottom() {
        assertThat(track.accepts(Direction.BOTTOM, marble)).isTrue();
    }

    /**
     * Test if a track does not accept a ball from the left.
     */
    @Test
    public void notAcceptsLeft() {
        assertThat(track.accepts(Direction.LEFT, marble)).isFalse();
    }

    /**
     * Test if a track does not accept a ball from the right.
     */
    @Test
    public void notAcceptsRight() {
        assertThat(track.accepts(Direction.RIGHT, marble)).isFalse();
    }

    /**
     * Test if giving a ball from the top is successful.
     */
    @Test
    public void acceptTop() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.TOP;
        track.addListener(listener);
        track.accept(direction, marble);
        verify(listener, times(1)).ballAccepted(track, direction, marble);
    }

    /**
     * Test if giving a ball from the bottom is successful.
     */
    @Test
    public void acceptBottom() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.BOTTOM;
        track.addListener(listener);
        track.accept(direction, marble);
        verify(listener, times(1)).ballAccepted(track, direction, marble);
    }


    /**
     * Test if giving a ball from the left gives an exception.
     */
    @Test
    public void acceptLeftException() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.LEFT;

        track.addListener(listener);
        assertThatThrownBy(() -> track.accept(direction, marble))
            .isInstanceOf(IllegalArgumentException.class);
        verify(listener, never()).ballAccepted(track, direction, marble);
    }

    /**
     * Test if giving a ball from the right gives an exception.
     */
    @Test
    public void acceptRightException() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.RIGHT;

        track.addListener(listener);
        assertThatThrownBy(() -> track.accept(direction, marble))
            .isInstanceOf(IllegalArgumentException.class);
        verify(listener, never()).ballAccepted(track, direction, marble);
    }
}
