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
package com.daniel.alienintruders.credits;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Represents a credit to display on the game credits screen.
 *
 * @author Bryan Daniel
 */
public class Credit {

    /**
     * The type of work contributed
     */
    @CsvBindByPosition(position = 0)
    private String type;

    /**
     * The name of the work contributed
     */
    @CsvBindByPosition(position = 1)
    private String work;

    /**
     * The person who created the contribution
     */
    @CsvBindByPosition(position = 2)
    private String by;

    /**
     * Where the work can be found
     */
    @CsvBindByPosition(position = 3)
    private String where;

    /**
     * The license information
     */
    @CsvBindByPosition(position = 4)
    private String licenseInfo;

    /**
     * Gets the value of type.
     *
     * @return the type of work
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of type.
     *
     * @param type the new type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the value of work.
     *
     * @return the name of work
     */
    public String getWork() {
        return work;
    }

    /**
     * Sets the value of work.
     *
     * @param work the new work to set
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * Gets the value of by.
     *
     * @return the person who created the work
     */
    public String getBy() {
        return by;
    }

    /**
     * Sets the value of by.
     *
     * @param by the new by to set
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * Gets the value of where.
     *
     * @return where the work can be found
     */
    public String getWhere() {
        return where;
    }

    /**
     * Sets the value of where.
     *
     * @param where the new where to set
     */
    public void setWhere(String where) {
        this.where = where;
    }

    /**
     * Gets the value of licenseInfo.
     *
     * @return the license information
     */
    public String getLicenseInfo() {
        return licenseInfo;
    }

    /**
     * Sets the value of licenseInfo.
     *
     * @param licenseInfo the new licenseInfo to set
     */
    public void setLicenseInfo(String licenseInfo) {
        this.licenseInfo = licenseInfo;
    }
}
