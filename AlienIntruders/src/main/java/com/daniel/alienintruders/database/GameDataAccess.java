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
package com.daniel.alienintruders.database;

import com.daniel.alienintruders.context.GameContext;
import com.daniel.alienintruders.database.entity.AlienWave;
import com.daniel.alienintruders.database.entity.Player;
import com.daniel.alienintruders.database.entity.PlayerState;
import com.daniel.alienintruders.database.entity.ScoreboardEntry;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides a facade to the database access classes.
 *
 * @author Bryan Daniel
 */
public class GameDataAccess {

    /**
     * Private constructor.
     */
    private GameDataAccess() {
    }

    /**
     * Retrieves game data for a player and populates the provided GameContext object.
     *
     * @param playerName the player name
     * @param gameContext the game context to populate
     */
    public static void loadGameContext(String playerName,
            GameContext gameContext) {
        PlayerState playerState = PlayerStateDataAccess.findPlayerState(playerName);
        if (playerState == null) {
            Player player = new Player();
            player.setName(playerName);
            player.setLastGameStarted(LocalDateTime.now());

            playerState = new PlayerState();
            playerState.setPlayer(player);
            playerState.setTerminated(false);
            playerState.setScore(0);
            playerState.setAlienWave(findAlienWave(1));

            gameContext.setPlayerState(playerState);
            gameContext.setGameTimeElapsed(0L);
        } else {
            if (playerState.isTerminated()) {
                playerState.getPlayer().setLastGameStarted(LocalDateTime.now());

                playerState.setTerminated(false);
                playerState.setScore(0);
                playerState.setAlienWave(findAlienWave(1));

                gameContext.setPlayerState(playerState);
                gameContext.setGameTimeElapsed(0L);
            } else {
                gameContext.setPlayerState(playerState);
            }
        }
    }

    /**
     * Saved the specified game context.
     *
     * @param gameContext the game context to save
     * @return true if successful, false otherwise
     */
    public static boolean saveGameContext(GameContext gameContext) {
        if (PlayerDataAccess.savePlayer(gameContext.getPlayerState().getPlayer())) {
            return PlayerStateDataAccess.savePlayerState(gameContext.getPlayerState());
        }
        return false;
    }

    /**
     * Saves the player's score.
     *
     * @param gameContext the game context
     * @return true if successful, false otherwise
     */
    public static boolean saveScore(GameContext gameContext) {
        return ScoreboardEntryDataAccess.saveScore(gameContext);
    }

    /**
     * Fetches all saved player scores.
     *
     * @return the list of all scores
     */
    public static List<ScoreboardEntry> findAllScores() {
        return ScoreboardEntryDataAccess.findAllScores();
    }

    /**
     * Retrieves the alien wave associated with the given identifier.
     *
     * @param wave the number of the wave to retrieve
     * @return the alien wave
     */
    public static AlienWave findAlienWave(int wave) {
        return AlienWaveDataAccess.findAlienWave(wave);
    }

    /**
     * Retrieves all alien waves from the database.
     *
     * @return the list of alien waves
     */
    public static List<AlienWave> findAllAlienWaves() {
        return AlienWaveDataAccess.findAllAlienWaves();
    }

    /**
     * Retrieves the count of alien waves.
     *
     * @return the number of alien waves
     */
    public static int getAlienWaveCount() {
        return AlienWaveDataAccess.getAlienWaveCount();
    }
}
