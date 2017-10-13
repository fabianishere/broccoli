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

package nl.tudelft.broccoli.core.grid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import nl.tudelft.broccoli.core.Empty;
import nl.tudelft.broccoli.core.Marble;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.Track;
import org.junit.Before;
import org.junit.Test;

/**
 * A class to test the abstract {@link Tileable} class.
 */
public class TileableTest {
    private Grid grid;
    private Marble marble;

    /**
     * Sets up a grid an onGrid tile and an off the grid tile ☺.
     */
    @Before
    public void setUp() {
        grid = new Grid(2, 2);
        grid.place(0,0, new HorizontalTrack());
        grid.place(1, 0, new HorizontalTrack());
        grid.place(0, 1, new HorizontalTrack());
        grid.place(1, 1, new Empty());
        marble = Marble.of(Marble.Type.GREEN);
    }

    @Test
    public void isConnected() {
        Tileable tileable = grid.get(0, 0).getTileable();
        assertThat(tileable.isConnected(Direction.RIGHT)).isTrue();
    }

    @Test
    public void isConnectedInverse() {
        Tileable tileable = grid.get(1, 0).getTileable();
        assertThat(tileable.isConnected(Direction.LEFT)).isTrue();
    }

    @Test
    public void isNotConnected() {
        Tileable tileable = grid.get(0, 0).getTileable();
        assertThat(tileable.isConnected(Direction.LEFT)).isFalse();
    }

    @Test
    public void doesNotAllowConnections() {
        Tileable tileable = grid.get(1, 1).getTileable();
        assertThat(tileable.isConnected(Direction.TOP)).isFalse();
    }

    @Test
    public void neighbourDoesNotAllowConnections() {
        Tileable tileable = grid.get(0, 1).getTileable();
        assertThat(tileable.isConnected(Direction.RIGHT)).isFalse();
    }

    @Test
    public void unplacedNotConnected() {
        Track track = new HorizontalTrack();
        assertThatThrownBy(() -> track.isConnected(Direction.RIGHT))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void isReleasable() {
        Tileable tileable = grid.get(0, 0).getTileable();
        assertThat(tileable.isReleasable(Direction.RIGHT, marble)).isTrue();
    }

    @Test
    public void isNotReleasable() {
        Tileable tileable = grid.get(0, 0).getTileable();
        assertThat(tileable.isReleasable(Direction.LEFT, marble)).isFalse();
    }

    @Test
    public void neighbourDoesNotAllowReleasing() {
        Tileable tileable = grid.get(0, 1).getTileable();
        assertThat(tileable.isReleasable(Direction.RIGHT, marble)).isFalse();
    }

    @Test
    public void unplacedNotReleasable() {
        Track track = new HorizontalTrack();
        assertThatThrownBy(() -> track.isReleasable(Direction.RIGHT, marble))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void releaseNeighbour() {
        TileableListener listener = mock(TileableListener.class);
        Tile tile = grid.get(0, 0);
        Tileable tileable = tile.getTileable();
        tileable.addListener(listener);
        Marble marble = Marble.of(Marble.Type.BLUE);
        tileable.release(Direction.RIGHT, marble);
        verify(listener, times(1)).ballReleased(tileable, Direction.RIGHT, marble);
    }

    @Test
    public void releaseNeighbourNull() {
        TileableListener listener = mock(TileableListener.class);
        Tile tile = grid.get(0, 0);
        Tileable tileable = tile.getTileable();
        tileable.addListener(listener);
        Marble marble = Marble.of(Marble.Type.BLUE);
        tileable.release(Direction.LEFT, marble);
        verify(listener, never()).ballReleased(tileable, Direction.LEFT, marble);
        tileable.removeListener(listener);
    }

    @Test
    public void unplacedRelease() {
        Track track = new HorizontalTrack();
        assertThatThrownBy(() -> track.release(Direction.RIGHT, Marble.of(Marble.Type.BLUE)))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void onGrid() {
        Tileable tileable = grid.get(0, 0).getTileable();
        assertThat(tileable.onGrid()).isTrue();
    }

    @Test
    public void notOnGrid() {
        Tileable tileable = new HorizontalTrack();
        assertThat(tileable.onGrid()).isFalse();
    }

    @Test
    public void getTile() {
        Tile tile = grid.get(0, 0);
        Tileable tileable = tile.getTileable();
        assertThat(tileable.getTile()).isEqualTo(tile);
    }

    @Test
    public void getListeners() {
        Tileable tileable = new HorizontalTrack();
        assertThat(tileable.getListeners()).isEmpty();
    }

    @Test
    public void addListener() {
        TileableListener listener = mock(TileableListener.class);
        Tileable tileable = new HorizontalTrack();
        tileable.addListener(listener);
        assertThat(tileable.getListeners()).containsExactly(listener);
    }

    @Test
    public void removeListener() {
        TileableListener listener = mock(TileableListener.class);
        Tileable tileable = new HorizontalTrack();
        tileable.addListener(listener);
        tileable.removeListener(listener);
        assertThat(tileable.getListeners()).isEmpty();
    }

    @Test
    public void informAcceptation() {
        TileableListener listener = mock(TileableListener.class);
        Tileable tileable = new HorizontalTrack();
        Direction direction = Direction.RIGHT;
        Marble marble = Marble.of(Marble.Type.BLUE);
        tileable.addListener(listener);
        tileable.informAcceptation(direction, marble);
        verify(listener, times(1)).ballAccepted(tileable, direction, marble);
    }

    @Test
    public void informRelease() {
        TileableListener listener = mock(TileableListener.class);
        Tileable tileable = new HorizontalTrack();
        Direction direction = Direction.RIGHT;
        Marble marble = Marble.of(Marble.Type.BLUE);
        tileable.addListener(listener);
        tileable.informRelease(direction, marble);
        verify(listener, times(1)).ballReleased(tileable, direction, marble);
    }

    @Test
    public void informDispose() {
        TileableListener listener = mock(TileableListener.class);
        Tileable tileable = new HorizontalTrack();
        Direction direction = Direction.RIGHT;
        Marble marble = Marble.of(Marble.Type.BLUE);
        tileable.addListener(listener);
        tileable.informDispose(direction, marble);
        verify(listener, times(1)).ballDisposed(tileable, direction, marble);
    }

    /**
     * Test default implementations of the {@link TileableListener} interface.
     */
    @Test
    public void informAll() {
        TileableListener listener = new TileableListener() {};
        Tileable tileable = new HorizontalTrack();
        Direction direction = Direction.RIGHT;
        Marble marble = Marble.of(Marble.Type.BLUE);
        tileable.addListener(listener);
        tileable.informAcceptation(direction, marble);
        tileable.informRelease(direction, marble);
        tileable.informDispose(direction, marble);
    }
}
