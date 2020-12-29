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
package com.daniel.alienintruders.database.entity;

/**
 * Represents a coordinated alien attack.
 *
 * @author Bryan Daniel
 */
public class AlienWave {

    /**
     * The order of this wave
     */
    private int wave;

    /**
     * The description of the wave
     */
    private String description;

    /**
     * The number of alien rows
     */
    private int numberOfRows;

    /**
     * The number of alien columns
     */
    private int numberOfColumns;

    /**
     * The speed of the alien missiles
     */
    private int missileSpeed;

    /**
     * The points awarded for an alien death
     */
    private int points;

    /**
     * Gets the value of wave.
     *
     * @return the wave
     */
    public int getWave() {
        return wave;
    }

    /**
     * Sets the value of wave.
     *
     * @param wave the new wave to set
     */
    public void setWave(int wave) {
        this.wave = wave;
    }

    /**
     * Gets the value of description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of description.
     *
     * @param description the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the value of numberOfRows.
     *
     * @return the number of rows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Sets the value of numberOfRows.
     *
     * @param numberOfRows the new numberOfRows to set
     */
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    /**
     * Gets the value of numberOfColumns.
     *
     * @return the number of columns
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Sets the value of numberOfColumns.
     *
     * @param numberOfColumns the new numberOfColumns to set
     */
    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Gets the value of missileSpeed.
     *
     * @return the missile speed
     */
    public int getMissileSpeed() {
        return missileSpeed;
    }

    /**
     * Sets the value of missileSpeed.
     *
     * @param missileSpeed the new missileSpeed to set
     */
    public void setMissileSpeed(int missileSpeed) {
        this.missileSpeed = missileSpeed;
    }

    /**
     * Gets the value of points.
     *
     * @return the points awarded for death
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the value of points.
     *
     * @param points the new points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
