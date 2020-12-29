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

import com.daniel.alienintruders.image.ImageFactory;
import com.daniel.alienintruders.image.ImageType;
import java.awt.Rectangle;

/**
 * The laser beam fired by the spaceship.
 *
 * @author Bryan Daniel
 */
public class Laser extends Sprite {

    /**
     * The width of the laser
     */
    public static final int LASER_WIDTH = 7;

    /**
     * The height of the laser
     */
    public static final int LASER_HEIGHT = 20;

    /**
     * The vertical translation of the laser
     */
    public static final int LASER_VERTICAL_MOVEMENT = -10;

    /**
     * Initializes this laser.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Laser(int x, int y) {
        initialize(x, y);
    }

    /**
     * Initializes variables for this laser.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void initialize(int x, int y) {
        setImage(ImageFactory.createImage(ImageType.SPACESHIP_LASER).getImage());
        setX(x + Spaceship.SPACESHIP_WIDTH / 2);
        setY(y);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), LASER_WIDTH, LASER_HEIGHT);
    }

    @Override
    public void move() {
        setY(getY() + LASER_VERTICAL_MOVEMENT);
        if (getY() < 0) {
            die();
        }
    }
}
