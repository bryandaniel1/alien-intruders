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
import com.daniel.alienintruders.database.entity.AlienWave;
import com.daniel.alienintruders.image.ImageFactory;
import com.daniel.alienintruders.image.ImageType;
import com.daniel.alienintruders.view.GamePanel;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An alien intruder determined to crush humanity.
 *
 * @author Bryan Daniel
 */
public class Alien extends Sprite {

    /**
     * The width of the alien
     */
    public static final int ALIEN_WIDTH = 34;

    /**
     * The height of the alien
     */
    public static final int ALIEN_HEIGHT = 34;

    /**
     * The beginning x coordinate of the alien
     */
    public static final int ALIEN_INIT_X = 450;

    /**
     * The beginning y coordinate of the alien
     */
    public static final int ALIEN_INIT_Y = 40;

    /**
     * Normal speed for the alien
     */
    public static final int NORMAL_ALIEN_MOVEMENT = 2;

    /**
     * Fast speed for the alien
     */
    public static final int FAST_ALIEN_MOVEMENT = 6;

    /**
     * The game context
     */
    private final GameContext gameContext;

    /**
     * The maximum activation time in milliseconds
     */
    private int activationTimeLimit = 22000;

    /**
     * The translation value of the alien
     */
    private int alienMovement = NORMAL_ALIEN_MOVEMENT;

    /**
     * Used to measure approach movement
     */
    private int approach = 0;

    /**
     * Indicates whether or not the aliens are moving to the left
     */
    private boolean movingLeft = true;

    /**
     * The missile fired by the alien
     */
    private AlienMissile firedMissile;

    /**
     * The missiles loaded for attack
     */
    private Queue<AlienMissile> loadedMissiles;

    /**
     * The time in milliseconds to prepare the next missile launch
     */
    private int missileActivationTime;

    /**
     * The translation of the fired missiles
     */
    private int missileSpeed;

    /**
     * The points awarded for this alien's death
     */
    private int points;

    /**
     * Indicates whether or not this alien is in kamikazi mode
     */
    private boolean kamikaziMode = false;

    /**
     * Creates this alien.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param gameContext the game context
     */
    public Alien(int x, int y, GameContext gameContext) {
        this.gameContext = gameContext;
        initialize(x, y);
    }

    /**
     * Initializes variables for this alien.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void initialize(int x, int y) {
        setX(x);
        setY(y);
        loadedMissiles = new LinkedList<>();
        loadedMissiles.add(new AlienMissile());
        loadedMissiles.add(new AlienMissile());
        loadedMissiles.add(new AlienMissile());
        loadedMissiles.add(new AlienMissile());
        loadedMissiles.add(new AlienMissile());
        setImage(ImageFactory.createImage(ImageType.ALIEN).getImage());
        missileActivationTime = (int) (Math.random() * activationTimeLimit);
        AlienWave alienWave = gameContext.getPlayerState().getAlienWave();
        missileSpeed = alienWave.getMissileSpeed();
        points = alienWave.getPoints();
    }

    /**
     * Launches a missile attack. If all missiles are spent, alien speed increases.
     */
    public void fireMissile() {
        if (gameContext.isGameRunning() && (firedMissile == null || firedMissile.
                isDead())) {
            if (!loadedMissiles.isEmpty()) {
                if (missileActivationTime < 0) {
                    firedMissile = loadedMissiles.remove();
                    firedMissile.initialize(getX() + ALIEN_WIDTH / 2, getY() + ALIEN_HEIGHT / 2,
                            determineMissileRoute(this, missileSpeed));
                    missileActivationTime = (int) (Math.random() * activationTimeLimit);
                } else {
                    missileActivationTime -= GamePanel.LOOP_DELAY;
                }
            }
        }
    }

    @Override
    public void move() {

        // finding the left and right edges of the alien swarm
        int leftmostAlienLocation = gameContext.getAlienList().get(0).getX() - alienMovement;
        int rightmostAlienLocation = gameContext.getAlienList().get(
                gameContext.getAlienColumns() - 1).getX() + ALIEN_WIDTH + alienMovement;
        if (kamikaziMode) {
            int newLeftLocation = -1;
            int newRightLocation = -1;
            for (Alien alien : gameContext.getAlienList()) {
                if (!alien.isDead() && (newLeftLocation < 0 || newLeftLocation > alien.getX())) {
                    newLeftLocation = alien.getX();
                }
                if (!alien.isDead() && (newRightLocation < 0 || newRightLocation < alien.getX() + ALIEN_WIDTH)) {
                    newRightLocation = alien.getX() + ALIEN_WIDTH;
                }
            }
            leftmostAlienLocation = newLeftLocation;
            rightmostAlienLocation = newRightLocation;
        }

        // setting coordinates
        if (movingLeft && leftmostAlienLocation < 0 && approach < ALIEN_HEIGHT) {
            setY(getY() + alienMovement);
            approach += alienMovement;
        } else if (movingLeft && leftmostAlienLocation < 0 && approach >= ALIEN_HEIGHT) {
            approach = 0;
            setX(getX() + alienMovement);
            movingLeft = false;
        } else if (!movingLeft && rightmostAlienLocation > GamePanel.PANEL_WIDTH && approach < ALIEN_HEIGHT) {
            setY(getY() + alienMovement);
            approach += alienMovement;
        } else if (!movingLeft && rightmostAlienLocation > GamePanel.PANEL_WIDTH && approach >= ALIEN_HEIGHT) {
            approach = 0;
            setX(getX() - alienMovement);
            movingLeft = true;
        } else if (movingLeft) {
            setX(getX() - alienMovement);
        } else {
            setX(getX() + alienMovement);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), ALIEN_WIDTH, ALIEN_HEIGHT);
    }

    /**
     * Determines the missile route from the specified alien to the spaceship.
     *
     * @param alien the attacking alien
     * @param missileSpeed the missile speed
     * @return the missile route data
     */
    private MissileRoute determineMissileRoute(Alien alien, int missileSpeed) {

        // get the alien coordinates
        MissileRoute missileRoute = new MissileRoute();
        int alienX = alien.getX() + Alien.ALIEN_WIDTH / 2;
        int alienY = alien.getY() + Alien.ALIEN_HEIGHT / 2;

        // get the spaceship coordinates
        Spaceship spaceship = gameContext.getSpaceship();
        int spaceshipX = spaceship.getX() + Spaceship.SPACESHIP_WIDTH / 2;
        int spaceshipY = spaceship.getY() + Spaceship.SPACESHIP_HEIGHT / 2;

        // find the x and y distances
        int distanceX = spaceshipX - alienX;
        int distanceY = spaceshipY - alienY;

        // calculate the x and y translations
        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            if (distanceX < 0) {
                missileRoute.setDeltaX(-missileSpeed);
            } else {
                missileRoute.setDeltaX(missileSpeed);
            }
            missileRoute.
                    setDeltaY((distanceY * missileSpeed) / Math.abs(distanceX));
        } else {
            if (distanceX < 0) {
                missileRoute.setDeltaX(-missileSpeed);
            } else {
                missileRoute.setDeltaX(missileSpeed);
            }
            missileRoute.setDeltaX((distanceX * missileSpeed) / distanceY);
            missileRoute.setDeltaY(missileSpeed);
        }

        return missileRoute;
    }

    /**
     * Indicates whether or not this alien has spent all missiles.
     *
     * @return true if this alien has missiles, false otherwise
     */
    public boolean isArmed() {
        return !isDead() && !loadedMissiles.isEmpty();
    }

    /**
     * Gets the value of activationTimeLimit.
     *
     * @return the maximum time limit for missile activation
     */
    public int getActivationTimeLimit() {
        return activationTimeLimit;
    }

    /**
     * Sets the value of activationTimeLimit.
     *
     * @param activationTimeLimit the new activationTimeLimit to set
     */
    public void setActivationTimeLimit(int activationTimeLimit) {
        this.activationTimeLimit = activationTimeLimit;
    }

    /**
     * Gets the value of firedMissile.
     *
     * @return the fired missile
     */
    public AlienMissile getFiredMissile() {
        return firedMissile;
    }

    /**
     * Gets the value of points.
     *
     * @return the points this alien's death is worth
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the value for kamikaziMode.
     *
     * @return the flag for kamikazi mode
     */
    public boolean isKamikaziMode() {
        return kamikaziMode;
    }

    /**
     * Sets the value for kamikazi mode and increases the alien movement speed.
     *
     * @param kamikaziMode the new kamikaziMode to set
     */
    public void setKamikaziMode(boolean kamikaziMode) {
        this.kamikaziMode = kamikaziMode;
        alienMovement = FAST_ALIEN_MOVEMENT;
    }
}
