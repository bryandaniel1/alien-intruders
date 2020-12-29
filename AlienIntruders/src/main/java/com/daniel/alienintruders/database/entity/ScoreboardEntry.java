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
 * Represents an entry to display in the scoreboard.
 *
 * @author Bryan Daniel
 */
public class ScoreboardEntry {

    /**
     * The identifier for this entry
     */
    private int id;

    /**
     * The player name
     */
    private String playerName;

    /**
     * The current score for the player
     */
    private int score;

    /**
     * The time elapsed in milliseconds for the current game
     */
    private Long gameTimeElapsed;

    /**
     * Gets the value of id.
     *
     * @return the ID of this entry
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of id.
     *
     * @param id the new id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the value of playerName
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the value of playerName
     *
     * @param playerName the new player name to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the value of score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the value of score.
     *
     * @param score the new score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the value of gameTimeElapsed.
     *
     * @return the game time elapsed
     */
    public Long getGameTimeElapsed() {
        return gameTimeElapsed;
    }

    /**
     * Sets the value of gameTimeElapsed.
     *
     * @param gameTimeElapsed the new gameTimeElapsed to set
     */
    public void setGameTimeElapsed(Long gameTimeElapsed) {
        this.gameTimeElapsed = gameTimeElapsed;
    }
}
