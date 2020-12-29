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
package com.daniel.alienintruders.image;

import java.awt.Image;

/**
 * Represents a single image inside an animated GIF file.
 *
 * @author Bryan Daniel
 */
public class GifFrame {

    /**
     * The image for the animation
     */
    private Image image;

    /**
     * The x coordinate of the location
     */
    private int x;

    /**
     * The y coordinate of the location
     */
    private int y;

    /**
     * The width of the animation
     */
    private int width;

    /**
     * The height of the animation
     */
    private int height;

    /**
     * Sets the values for the instance variables.
     *
     * @param image the image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width
     * @param height the height
     */
    public GifFrame(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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
     * Gets the value of the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the value of the width.
     *
     * @param width the new value of width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the value of the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the value of the height.
     *
     * @param height the new value of height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
