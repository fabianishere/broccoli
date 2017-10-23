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

package nl.tudelft.broccoli.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import nl.tudelft.broccoli.core.grid.Direction;
import nl.tudelft.broccoli.core.grid.Grid;
import nl.tudelft.broccoli.core.grid.TileableListener;
import nl.tudelft.broccoli.core.track.HorizontalTrack;
import nl.tudelft.broccoli.core.track.Track;
import nl.tudelft.broccoli.core.track.VerticalTrack;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class that tests the {@link Teleporter} class.
 */
public class TeleporterTest {
    /**
     * The track under test.
     */
    private Teleporter teleporter1;

    /**
     * The track under test.
     */
    private Teleporter teleporter2;

    /**
     * The inner track of teleporter1.
     */
    private Track inner = new HorizontalTrack();

    /**
     * Setup the test suite.
     */
    @Before
    public void setUp() {
        teleporter1 = new Teleporter(inner);
        teleporter2 = new Teleporter(new HorizontalTrack());
        teleporter1.setDestination(teleporter2);
        teleporter2.setDestination(teleporter1);
    }

    /**
     * Test if a horizontal track allows a connection from the left.
     */
    @Test
    public void allowsLeft() {
        assertThat(teleporter1.allowsConnection(Direction.LEFT)).isTrue();
    }

    /**
     * Test if a horizontal track allows a connection from the right.
     */
    @Test
    public void allowsRight() {
        assertThat(teleporter1.allowsConnection(Direction.RIGHT)).isTrue();
    }

    /**
     * Test if a horizontal track does not allow a connection from the top.
     */
    @Test
    public void disallowsTop() {
        assertThat(teleporter1.allowsConnection(Direction.TOP)).isFalse();
    }

    /**
     * Test if a horizontal track does not accept a ball from the bottom.
     */
    @Test
    public void disallowsBottom() {
        assertThat(teleporter1.allowsConnection(
                Direction.BOTTOM)).isFalse();
    }

    /**
     * Test if a horizontal accepts a marble from the left.
     */
    @Test
    public void acceptsLeft() {
        assertThat(teleporter1.accepts(Direction.LEFT, new Marble(
                MarbleType.GREEN))).isTrue();
    }


    /**
     * Test if giving a ball from the top gives an exception.
     */
    @Test
    public void acceptTopException() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = new Marble(MarbleType.BLUE);
        Direction direction = Direction.TOP;

        teleporter1.addListener(listener);
        assertThatThrownBy(() -> teleporter1.accept(direction, marble))
            .isInstanceOf(IllegalArgumentException.class);
        verify(listener, never()).ballAccepted(teleporter1, direction, marble);
    }

    /**
     * Test if giving a ball from the bottom gives an exception.
     */
    @Test
    public void acceptBottomException() {
        TileableListener listener = mock(TileableListener.class);
        Marble marble = new Marble(MarbleType.BLUE);
        Direction direction = Direction.BOTTOM;

        teleporter1.addListener(listener);
        assertThatThrownBy(() -> teleporter1.accept(direction, marble))
            .isInstanceOf(IllegalArgumentException.class);
        verify(listener, never()).ballAccepted(teleporter1, direction, marble);
    }

    /**
     * Test if we can get the inner track.
     */
    @Test
    public void getTrack() {
        assertThat(teleporter1.getTrack()).isEqualTo(inner);
    }

    /**
     * Test if we can get the other location of the teleporter.
     */
    @Test
    public void getDestination() {
        assertThat(teleporter1.getDestination()).isEqualTo(teleporter2);
        assertThat(teleporter2.getDestination()).isEqualTo(teleporter1);
    }

    /**
     * Test if a exception is thrown if a vertical teleporter and
     * a horizontal teleporter are connected.
     */
    @Test
    public void setDestinationFail() {
        Teleporter vertTeleporter = new Teleporter(new VerticalTrack());

        assertThatThrownBy(() -> vertTeleporter.setDestination(teleporter1))
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> teleporter1.setDestination(vertTeleporter))
                .isInstanceOf(IllegalStateException.class);
    }

    /**
     * Test if releasing a ball to the right is succesfull.
     */
    @Test
    public void informRelease() {
        Grid grid = new Grid(null, 2, 1);
        TileableListener listener = mock(TileableListener.class);
        grid.place(0, 0, inner);
        grid.place(1, 0, new HorizontalTrack());
        Direction direction = Direction.RIGHT;
        Marble marble = new Marble(MarbleType.BLUE);
        teleporter1.addListener(listener);
        inner.accept(direction, marble);
        inner.release(direction, marble);
        verify(listener, times(1)).ballReleased(
                teleporter1, direction, marble);
    }

    /**
     * Test if giving a ball from the left is successful.
     */
    @Test
    public void acceptLeft() {
        Marble marble = new Marble(MarbleType.BLUE);
        TileableListener listener = mock(TileableListener.class);
        teleporter1.addListener(listener);
        Direction direction = Direction.LEFT;
        teleporter1.accept(direction, marble);
        verify(listener, times(1)).ballAccepted(
                teleporter1, direction, marble);
    }

    /**
     * Test if disposing a ball is succesful.
     */
    @Test
    public void informDispose() {
        Direction direction = Direction.RIGHT;
        Marble marble = new Marble(MarbleType.BLUE);
        TileableListener listener = mock(TileableListener.class);
        teleporter1.addListener(listener);
        teleporter1.ballDisposed(teleporter1, direction, marble);
        verify(listener, times(1)).ballDisposed(
                teleporter1, direction, marble);
    }
}
