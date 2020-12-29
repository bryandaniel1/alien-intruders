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

import java.awt.Image;
import java.awt.Rectangle;

/**
 * The abstract class for game objects which appear on screen.
 *
 * @author Bryan Daniel
 */
public abstract class Sprite {

    /**
     * The image for the sprite
     */
    private Image image;

    /**
     * Indicates whether or not the sprite has died
     */
    private boolean dead;

    /**
     * The x coordinate of the sprite location
     */
    protected int x;

    /**
     * The y coordinate of the sprite location
     */
    protected int y;

    /**
     * The change in the x coordinate
     */
    protected int deltaX;

    /**
     * Creates a new Sprite with dead set to false.
     */
    public Sprite() {
        this.dead = false;
    }

    /**
     * Sets dead to true.
     */
    public void die() {
        setDead(true);
    }

    /**
     * Determines if this sprite is colliding with the given sprite.
     *
     * @param otherSprite the given sprite
     * @return true if colliding, false otherwise
     */
    public boolean isColliding(Sprite otherSprite) {
        return getBounds().intersects(otherSprite.getBounds());
    }

    /**
     * Returns the bounds for this sprite.
     *
     * @return the Rectangle object
     */
    public abstract Rectangle getBounds();

    /**
     * Defines movement for the sprite.
     */
    public abstract void move();

    /**
     * Gets the value of image.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the value of image.
     *
     * @param image the new image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the value of dead.
     *
     * @return the flag for dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Sets the value of dead.
     *
     * @param dead the new value of dead to set
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Gets the value of the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the value of the x coordinate.
     *
     * @param x the new value of x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the value of the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the value of the y coordinate.
     *
     * @param y the new value of y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the value of deltaX.
     *
     * @return the change in x
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Sets the value of deltaX.
     *
     * @param deltaX the new value of deltaX to set
     */
    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }
}
