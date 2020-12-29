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

import java.time.LocalDateTime;

/**
 * Represents a game player.
 *
 * @author Bryan Daniel
 */
public class Player {

    /**
     * The player's name
     */
    private String name;

    /**
     * The last time a player started a game
     */
    private LocalDateTime lastGameStarted;

    /**
     * Gets the value of name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of lastGameStarted.
     *
     * @return the time the last game was started by the player
     */
    public LocalDateTime getLastGameStarted() {
        return lastGameStarted;
    }

    /**
     * Sets the value of lastGameStarted.
     *
     * @param lastGameStarted the new lastGameStarted to set
     */
    public void setLastGameStarted(LocalDateTime lastGameStarted) {
        this.lastGameStarted = lastGameStarted;
    }
}
