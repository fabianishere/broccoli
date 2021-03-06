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

package nl.tudelft.broccoli.libgdx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.tudelft.broccoli.core.config.Configuration;
import nl.tudelft.broccoli.core.config.ConfigurationLoader;
import nl.tudelft.broccoli.lightbend.LightbendConfigurationLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * A launcher for the Gudeballs game implementation using libgdx as front-end,
 * with a LWJGL backend for libgdx.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public final class DesktopLauncher implements Runnable {
    /**
     * The singleton instance of this class.
     */
    private static final DesktopLauncher INSTANCE = new DesktopLauncher();

    /**
     * Disallow instantiation of the {@link DesktopLauncher} class.
     */
    private DesktopLauncher() {}

    /**
     * Return the singleton instance of the {@link DesktopLauncher} class.
     *
     * @return An {@link DesktopLauncher} instance.
     */
    public static DesktopLauncher getInstance() {
        return INSTANCE;
    }

    /**
     * The main entry point of the program.
     *
     * @param args The command line arguments passed to this program.
     */
    public static void main(String[] args) {
        getInstance().run();
    }

    /**
     * Launch the desktop frontend of the Gudeballs game.
     */
    @Override
    public void run() {
        try (InputStream input = DesktopLauncher.class.getResourceAsStream("/reference.conf")) {
            ConfigurationLoader loader = new LightbendConfigurationLoader();
            Configuration configuration = loader.tryLoad(input);

            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.title = configuration.get(Broccoli.WINDOW_TITLE);
            config.width = configuration.get(Broccoli.WINDOW_WIDTH);
            config.height = configuration.get(Broccoli.WINDOW_HEIGHT);
            config.resizable = false;
            config.forceExit = false;

            new LwjglApplication(new Broccoli(configuration), config);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
