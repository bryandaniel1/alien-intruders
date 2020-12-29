/*
 * Copyright 2020 Bryan Daniel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daniel.alienintruders.input;

import com.daniel.alienintruders.context.GameContext;
import com.daniel.alienintruders.sprite.Spaceship;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard inputs for the game.
 *
 * @author Bryan Daniel
 */
public class GameInputListener implements KeyListener {

    /**
     * The protagonist of the game
     */
    private final GameContext gameContext;

    /**
     * Sets the value of gameContext.
     *
     * @param gameContext the game context
     */
    public GameInputListener(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Spaceship spaceship = gameContext.getSpaceship();
        spaceship.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Spaceship spaceship = gameContext.getSpaceship();
        spaceship.keyReleased(keyEvent);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not implemented
    }
}
