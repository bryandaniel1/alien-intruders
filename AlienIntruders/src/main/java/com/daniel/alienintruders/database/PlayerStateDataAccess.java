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

import com.daniel.alienintruders.database.entity.AlienWave;
import com.daniel.alienintruders.database.entity.Player;
import com.daniel.alienintruders.database.entity.PlayerState;
import com.daniel.alienintruders.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connects to the database to retrieve and update player state information.
 *
 * @author Bryan Daniel
 */
public class PlayerStateDataAccess {

    /**
     * The logger for this class
     */
    public static final Logger LOGGER = LogManager.getLogger(PlayerStateDataAccess.class);

    /**
     * Private constructor - not instantiated
     */
    private PlayerStateDataAccess() {
    }

    /**
     * Retrieves player state for the player identified by the specified player name.
     *
     * @param playerName the player name
     * @return the player state or null if none is found
     */
    static PlayerState findPlayerState(String playerName) {
        PlayerState playerState = null;

        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * "
                        + "FROM player_state s, player p, alien_wave w "
                        + "WHERE s.player_name = p.name "
                        + "AND s.alien_wave = w.wave "
                        + "AND s.player_name = ?")) {
            preparedStatement.setString(1, playerName);
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                if (StringUtils.isBlank(results.getString("name"))) {
                    return null;
                }
                Player player = new Player();
                player.setName(results.getString("name"));
                player.setLastGameStarted(results.getTimestamp(
                        "last_game_started").toLocalDateTime());
                AlienWave alienWave = new AlienWave();
                alienWave.setWave(results.getInt("alien_wave"));
                alienWave.setDescription(results.getString("description"));
                alienWave.setNumberOfRows(results.getInt("number_of_rows"));
                alienWave.setNumberOfColumns(results.getInt("number_of_columns"));
                alienWave.setMissileSpeed(results.getInt("missile_speed"));
                alienWave.setPoints(results.getInt("points"));
                playerState = new PlayerState();
                playerState.setId(results.getInt("id"));
                playerState.setTerminated(results.getBoolean("terminated"));
                playerState.setScore(results.getInt("score"));
                playerState.setPlayer(player);
                playerState.setAlienWave(alienWave);
                LOGGER.info(String.format("Retrieved player, %s, playing in wave: %d with player "
                        + "state id: %d terminated %s wave: %d alien points: %d.",
                        player.getName(), playerState.getAlienWave().getWave(), playerState.getId(), playerState
                        .isTerminated(), playerState.getAlienWave().getWave(), playerState.getAlienWave().getPoints()));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in findPlayerState().", e);
        }

        return playerState;
    }

    /**
     * Saves the given player state in the database. If the state of the game for the player is non-existent, this
     * results in an insert. If the state does exist, this will be an update operation.
     *
     * @param playerState the player state
     * @return true if successful, false otherwise
     */
    static boolean savePlayerState(PlayerState playerState) {
        if (playerState.getId() == null) {
            return insertPlayerState(playerState);
        } else {
            return updatePlayerState(playerState);
        }
    }

    /**
     * Performs an insert to save player state information.
     *
     * @param playerState the player state to save
     * @return true if successful, false otherwise
     */
    private static boolean insertPlayerState(PlayerState playerState) {
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO player_state (player_name, alien_wave, terminated, score) "
                        + "VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, playerState.getPlayer().getName());
            preparedStatement.setInt(2, playerState.getAlienWave().getWave());
            preparedStatement.setBoolean(3, playerState.isTerminated());
            preparedStatement.setInt(4, playerState.getScore());
            preparedStatement.execute();
            LOGGER.info(String.format("Inserted player state - name: %s wave: %d terminated: %s score: %s",
                    playerState.getPlayer().getName(), playerState.getAlienWave().getWave(), playerState.isTerminated(),
                    playerState.getScore()));
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in insertPlayerState().", e);
        }
        return false;
    }

    /**
     * Performs an update to save player state information.
     *
     * @param playerState the player state to save
     * @return true if successful, false otherwise
     */
    private static boolean updatePlayerState(PlayerState playerState) {
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE player_state "
                        + "SET player_name = ?, alien_wave = ?, terminated = ?, score = ? "
                        + "WHERE id = ?")) {
            preparedStatement.setString(1, playerState.getPlayer().getName());
            preparedStatement.setInt(2, playerState.getAlienWave().getWave());
            preparedStatement.setBoolean(3, playerState.isTerminated());
            preparedStatement.setInt(4, playerState.getScore());
            preparedStatement.setInt(5, playerState.getId());
            preparedStatement.execute();
            LOGGER.info(String.format("Updated player state - id: %d name: %s wave: %d terminated: %s score: %s",
                    playerState.getId(), playerState.getPlayer().getName(), playerState.getAlienWave().getWave(),
                    playerState.isTerminated(), playerState.getScore()));
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in updatePlayerState().", e);
        }
        return false;
    }
}
