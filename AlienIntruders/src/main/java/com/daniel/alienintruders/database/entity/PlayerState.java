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
 * Represents a saved state within the a sequence of alien waves battled by the player.
 *
 * @author Bryan Daniel
 */
public class PlayerState {

    /**
     * The identifier for this player state
     */
    private Integer id;

    /**
     * The player
     */
    private Player player;

    /**
     * The alien wave
     */
    private AlienWave alienWave;

    /**
     * Indicates whether or not the player has ended the game
     */
    private boolean terminated;

    /**
     * The current score for the player
     */
    private int score;

    /**
     * Gets the value of id.
     *
     * @return the ID of this player state
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of id.
     *
     * @param id the new id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the value of player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the value of player.
     *
     * @param player the new player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the value of alienWave.
     *
     * @return the alien wave
     */
    public AlienWave getAlienWave() {
        return alienWave;
    }

    /**
     * Sets the value of alienWave.
     *
     * @param alienWave the new alienWave to set
     */
    public void setAlienWave(AlienWave alienWave) {
        this.alienWave = alienWave;
    }

    /**
     * Gets the value of terminated.
     *
     * @return the flag for terminated
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Sets the value of terminated.
     *
     * @param terminated the new terminated to set
     */
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
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
}
