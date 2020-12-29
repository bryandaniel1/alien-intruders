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

/**
 * Contains data to set the route for an alien missile.
 *
 * @author Bryan Daniel
 */
public class MissileRoute {

    /**
     * The change in x to travel to the target
     */
    private double deltaX;

    /**
     * The change in y to travel to the target
     */
    private double deltaY;

    /**
     * Gets the value of deltaX;
     *
     * @return the change in x
     */
    public double getDeltaX() {
        return deltaX;
    }

    /**
     * Sets the value of deltaX.
     *
     * @param deltaX the change in x to set
     */
    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    /**
     * Gets the value of deltaY;
     *
     * @return the change in y
     */
    public double getDeltaY() {
        return deltaY;
    }

    /**
     * Sets the value of deltaY.
     *
     * @param deltaY the change in y to set
     */
    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }
}
