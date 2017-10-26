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

package nl.tudelft.broccoli.libgdx.scene;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nl.tudelft.broccoli.core.Entity;
import nl.tudelft.broccoli.core.config.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * A context object that is shared between the actors of a scene.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
public class ActorContext {
    /**
     * The game configuration.
     */
    private final Configuration configuration;

    /**
     * The {@link TextureAtlas} we get the sprites from.
     */
    private final TextureAtlas atlas;

    /**
     * The actor registry which maps entities to their respective actor in the scene.
     */
    private final Map<Entity, Actor> registry = new HashMap<>();

    /**
     * Construct an {@link ActorContext} instance.
     *
     * @param configuration The game configuration.
     * @param atlas The texture atlas to use.
     */
    public ActorContext(Configuration configuration, TextureAtlas atlas) {
        this.configuration = configuration;
        this.atlas = atlas;
    }

    /**
     * Look up an {@link Entity} in the registry.
     *
     * @param entity The entity to look up in the registry.
     * @return The actor that is bound to this entity, or <code>null</code> if the entity has not
     *         been registered yet.
     */
    public Actor actor(Entity entity) {
        return registry.get(entity);
    }

    /**
     * Register an {@link Entity} and its respective {@link Actor} in the registry of this context.
     *
     * @param entity The entity to register.
     * @param actor The actor of this entity.
     */
    public void register(Entity entity, Actor actor) {
        registry.put(entity, actor);
    }

    /**
     * Return the game {@link Configuration} object.
     *
     * @return The game configuration.
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Return the {@link TextureAtlas} containing the sprites of the game.
     *
     * @return The texture atlas of the game sprites.
     */
    public TextureAtlas getTextureAtlas() {
        return atlas;
    }
}
