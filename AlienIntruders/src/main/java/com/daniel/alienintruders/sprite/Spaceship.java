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
package com.daniel.alienintruders.sprite;

import com.daniel.alienintruders.context.GameContext;
import com.daniel.alienintruders.image.ImageFactory;
import com.daniel.alienintruders.image.ImageType;
import com.daniel.alienintruders.view.GamePanel;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * Represents the hero of Alien Intruders, defending humanity from merciless alien beings.
 *
 * @author Bryan Daniel
 */
public class Spaceship extends Sprite {

    /**
     * The width of the spaceship
     */
    public static final int SPACESHIP_WIDTH = 40;

    /**
     * The height of the spaceship
     */
    public static final int SPACESHIP_HEIGHT = 40;

    /**
     * The translation of the spaceship
     */
    public static final int SPACESHIP_MOVEMENT = 4;

    /**
     * The game context
     */
    private final GameContext gameContext;

    /**
     * The laser fired by the spaceship
     */
    private Laser laser;

    /**
     * The flag for a death explosion
     */
    private boolean explode = false;

    /**
     * Sets the value for the game context and initializes the spaceship.
     *
     * @param gameContext the game context
     */
    public Spaceship(GameContext gameContext) {
        this.gameContext = gameContext;
        initialize();
    }

    /**
     * Initializes variables for the spaceship.
     */
    private void initialize() {
        setImage(ImageFactory.createImage(ImageType.SPACESHIP).getImage());
        int startX = GamePanel.PANEL_WIDTH / 2 - SPACESHIP_WIDTH / 2;
        int startY = GamePanel.PANEL_HEIGHT - 100;
        setX(startX);
        setY(startY);
    }

    @Override
    public void move() {
        setX(getX() + getDeltaX());
        if (getX() < 0) {
            setX(0);
        }
        if ((getX() + SPACESHIP_WIDTH) > GamePanel.PANEL_WIDTH) {
            setX(GamePanel.PANEL_WIDTH - SPACESHIP_WIDTH);
        }
    }

    /**
     * Handles key presses to affect actions of the ship.
     *
     * @param keyEvent the key event
     */
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            setDeltaX(-SPACESHIP_MOVEMENT);
        }
        if (key == KeyEvent.VK_RIGHT) {
            setDeltaX(SPACESHIP_MOVEMENT);
        }
        if (key == KeyEvent.VK_SPACE) {
            if (gameContext.isGameRunning() && !isDead() && (laser == null || laser.
                    isDead())) {
                laser = new Laser(getX(), getY());
                new Thread(() -> {
                    gameContext.getLaserAudioPlayer().playSound();
                }).start();
            }
        }
    }

    /**
     * Handles key releases of the left and right arrows to stop the ship.
     *
     * @param keyEvent the key event
     */
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            setDeltaX(0);
        }
        if (key == KeyEvent.VK_RIGHT) {
            setDeltaX(0);
        }
        if (key == KeyEvent.VK_SPACE) {
        }
    }

    /**
     * Gets the value of laser.
     *
     * @return the laser
     */
    public Laser getLaser() {
        return laser;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), SPACESHIP_WIDTH, SPACESHIP_HEIGHT);
    }

    /**
     * Gets the value of explode.
     *
     * @return the flag for a death explosion
     */
    public boolean isExplode() {
        return explode;
    }

    /**
     * Sets the value of explode.
     *
     * @param explode the new value of explode to set
     */
    public void setExplode(boolean explode) {
        this.explode = explode;
    }
}
