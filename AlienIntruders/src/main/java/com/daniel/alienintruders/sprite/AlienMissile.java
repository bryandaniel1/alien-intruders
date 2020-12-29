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
import com.daniel.alienintruders.view.GamePanel;
import java.awt.Rectangle;

/**
 * The weapon fired by the alien.
 *
 * @author Bryan Daniel
 */
public class AlienMissile extends Sprite {

    /**
     * The width of the laser
     */
    public static final int MISSILE_WIDTH = 11;

    /**
     * The height of the laser
     */
    public static final int MISSILE_HEIGHT = 22;

    /**
     * The route for this missile
     */
    private MissileRoute missileRoute;

    /**
     * Initializes variables for this alien missile.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param missileRoute the missile route
     */
    public void initialize(int x, int y, MissileRoute missileRoute) {
        setX(x);
        setY(y);
        this.missileRoute = missileRoute;
        setImage(ImageFactory.createImage(ImageType.ALIEN_MISSILE).getImage());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), MISSILE_WIDTH, MISSILE_HEIGHT);
    }

    @Override
    public void move() {
        setY((int) (getY() + missileRoute.getDeltaY()));
        setX((int) (getX() + missileRoute.getDeltaX()));
        if (getY() > GamePanel.PANEL_HEIGHT || getX() > GamePanel.PANEL_WIDTH || getX() < 0) {
            die();
        }
    }

    /**
     * Gets the value of missileRoute.
     *
     * @return the missile route
     */
    public MissileRoute getMissileRoute() {
        return missileRoute;
    }
}
