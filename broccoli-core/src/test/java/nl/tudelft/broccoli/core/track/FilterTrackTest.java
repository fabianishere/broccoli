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
import nl.tudelft.broccoli.core.grid.Tileable;
import nl.tudelft.broccoli.core.grid.TileableListener;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class that tests the {@link FilterTrack} class.
 */
public class FilterTrackTest {
    /**
     * The track under test.
     */
    private FilterTrack track;

    /**
     * The inner track.
     */
    private Track inner;

    /**
     * The type of marble that is filtered.
     */
    private Marble.Type type;

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        inner = new HorizontalTrack();
        type = Marble.Type.GREEN;
        track = new FilterTrack(inner, type);
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
     * Test if a connected rail returns true.
     */
    @Test
    public void isConnected() {
        Grid grid = new Grid(3, 1);
        grid.place(1, 0, track);

        assertThat(track.isConnected()).isTrue();
    }

    /**
     * Test if a horizontal track allows a connection from the left.
     */
    @Test
    public void allowsLeft() {
        assertThat(track.allowsConnection(Direction.LEFT)).isTrue();
    }

    /**
     * Test if a horizontal track allows a connection from the right.
     */
    @Test
    public void allowsRight() {
        assertThat(track.allowsConnection(Direction.RIGHT)).isTrue();
    }

    /**
     * Test if a horizontal track does not allow a connection from the top.
     */
    @Test
    public void disallowsTop() {
        assertThat(track.allowsConnection(Direction.TOP)).isFalse();
    }

    /**
     * Test if a horizontal track does not accept a ball from the bottom.
     */
    @Test
    public void disallowsBottom() {
        assertThat(track.allowsConnection(Direction.BOTTOM)).isFalse();
    }

    /**
     * Test if a horizontal green filter track accepts a green ball from the left.
     */
    @Test
    public void acceptsLeft() {
        assertThat(track.accepts(Direction.LEFT, Marble.of(Marble.Type.GREEN))).isTrue();
    }

    /**
     * Test if a horizontal green filter track does not accept a blue ball from the left.
     */
    @Test
    public void notAcceptsLeftBlue() {
        assertThat(track.accepts(Direction.LEFT, Marble.of(Marble.Type.BLUE))).isTrue();
        assertThat(track.passesMidpoint(Direction.LEFT, Marble.of(Marble.Type.BLUE))).isFalse();
    }

    /**
     * Test if a horizontal filter track accepts a green ball from the right.
     */
    @Test
    public void acceptsRight() {
        assertThat(track.accepts(Direction.RIGHT, Marble.of(Marble.Type.GREEN))).isTrue();
    }

    /**
     * Test if a horizontal track does not accept a ball from the top.
     */
    @Test
    public void notAcceptsTop() {
        assertThat(track.accepts(Direction.TOP, Marble.of(Marble.Type.GREEN))).isFalse();
    }

    /**
     * Test if a horizontal track does not accept a ball from the bottom.
     */
    @Test
    public void notAcceptsBottom() {
        assertThat(track.accepts(Direction.BOTTOM, Marble.of(Marble.Type.GREEN))).isFalse();
    }

    /**
     * Test if giving a ball from the left is successful.
     */
    @Test
    public void acceptLeft() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.LEFT;
        track.addListener(listener);
        track.accept(direction, marble);
        verify(listener, times(1)).ballAccepted(track, direction, marble);
    }

    /**
     * Test if giving a ball from the left is successful.
     */
    @Test
    public void acceptRight() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.RIGHT;
        track.addListener(listener);
        track.accept(direction, marble);
        verify(listener, times(1)).ballAccepted(track, direction, marble);
    }


    /**
     * Test if giving a ball from the top gives an exception.
     */
    @Test
    public void acceptTopException() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.TOP;

        track.addListener(listener);
        assertThatThrownBy(() -> track.accept(direction, marble))
            .isInstanceOf(IllegalArgumentException.class);
        verify(listener, never()).ballAccepted(track, direction, marble);
    }

    /**
     * Test if giving a ball from the bottom gives an exception.
     */
    @Test
    public void acceptBottomException() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = Marble.of(Marble.Type.BLUE);
        Direction direction = Direction.BOTTOM;

        track.addListener(listener);
        assertThatThrownBy(() -> track.accept(direction, marble))
            .isInstanceOf(IllegalArgumentException.class);
        verify(listener, never()).ballAccepted(track, direction, marble);
    }

    /**
     * Test if we can get the inner track.
     */
    @Test
    public void getTrack() {
        assertThat(track.getTrack()).isEqualTo(inner);
    }

    /**
     * Test if we can get the marble type that is filtered.
     */
    @Test
    public void getMarbleType() {
        assertThat(track.getMarbleType()).isEqualTo(type);
    }

    @Test
    public void informRelease() {
        Grid grid = new Grid(2, 1);
        TileableListener listener = mock(TileableListener.class);
        Track tileable = new HorizontalTrack();
        grid.place(0, 0, tileable);
        grid.place(1, 0, new HorizontalTrack());
        FilterTrack track = new FilterTrack(tileable, Marble.Type.GREEN);
        Direction direction = Direction.RIGHT;
        Marble marble = Marble.of(Marble.Type.BLUE);
        track.addListener(listener);
        tileable.accept(direction, marble);
        tileable.release(direction, marble);
        verify(listener, times(1)).ballReleased(track, direction, marble);
    }

    @Test
    public void informDispose() {
        Tileable tileable = new HorizontalTrack();
        Direction direction = Direction.RIGHT;
        Marble marble = Marble.of(Marble.Type.BLUE);
        track.ballDisposed(tileable, direction, marble);
    }

}
