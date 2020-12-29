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

import com.daniel.alienintruders.database.entity.Player;
import com.daniel.alienintruders.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connects to the database to retrieve and update player information.
 *
 * @author Bryan Daniel
 */
public class PlayerDataAccess {

    /**
     * The logger for this class
     */
    public static final Logger LOGGER = LogManager.getLogger(PlayerDataAccess.class);

    /**
     * Private constructor - not instantiated
     */
    private PlayerDataAccess() {
    }

    /**
     * Saves the given player information in the database.
     *
     * @param player the player to save
     * @return true if successful, false otherwise
     */
    static boolean savePlayer(Player player) {
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * "
                        + "FROM player p "
                        + "WHERE p.name = ?")) {
            preparedStatement.setString(1, player.getName());
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                results.close();
                return updatePlayer(player);
            } else {
                results.close();
                return insertPlayer(player);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in savePlayer().", e);
        }
        return false;
    }

    /**
     * Performs an insert to save player information.
     *
     * @param player the player to save
     * @return true if successful, false otherwise
     */
    private static boolean insertPlayer(Player player) {
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO player (name, last_game_started) "
                        + "VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(player
                    .getLastGameStarted()));
            preparedStatement.execute();
            LOGGER.info(String.format("Inserted player - name: %s last_game_started: %s",
                    player.getName(), player.getLastGameStarted()));
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in insertPlayer().", e);
        }
        return false;
    }

    /**
     * Performs an update to save player information.
     *
     * @param player the player to save
     * @return true if successful, false otherwise
     */
    private static boolean updatePlayer(Player player) {
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE player "
                        + "SET last_game_started = ? "
                        + "WHERE name = ?")) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(player
                    .getLastGameStarted()));
            preparedStatement.setString(2, player.getName());
            preparedStatement.execute();
            LOGGER.info(String.format("Updated player, %s - last_game_started: %s",
                    player.getName(), player.getLastGameStarted()));
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in updatePlayer().", e);
        }
        return false;
    }
}
