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
import com.daniel.alienintruders.database.entity.PlayerState;
import com.daniel.alienintruders.database.entity.ScoreboardEntry;
import com.daniel.alienintruders.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connects to the database to retrieve and update player scores.
 *
 * @author Bryan Daniel
 */
public class ScoreboardEntryDataAccess {

    /**
     * The logger for this class
     */
    public static final Logger LOGGER = LogManager.getLogger(ScoreboardEntryDataAccess.class);

    /**
     * Private constructor - not instantiated
     */
    private ScoreboardEntryDataAccess() {
    }

    /**
     * Fetches all player scores from the database, ordered from best to worst.
     *
     * @return the list of all scores
     */
    static List<ScoreboardEntry> findAllScores() {
        List<ScoreboardEntry> scores = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * "
                        + "FROM scoreboard_entry s "
                        + "ORDER BY s.score DESC, s.game_time_elapsed ASC")) {
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                ScoreboardEntry score = new ScoreboardEntry();
                score.setId(results.getInt("id"));
                score.setPlayerName(results.getString("player_name"));
                score.setScore(results.getInt("score"));
                score.setGameTimeElapsed(results.getLong("game_time_elapsed"));
                scores.add(score);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in findAllScores().", e);
        }
        return scores;
    }

    /**
     * Saves the score for given game context.
     *
     * @param gameContext the context containing the score data to save
     * @return true if successful, false otherwise
     */
    static boolean saveScore(GameContext gameContext) {
        PlayerState playerState = gameContext.getPlayerState();
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO scoreboard_entry (player_name, score, game_time_elapsed) "
                        + "VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, playerState.getPlayer().getName());
            preparedStatement.setInt(2, playerState.getScore());
            preparedStatement.setLong(3, gameContext.getGameTimeElapsed());
            preparedStatement.execute();
            LOGGER.info(String.format("Inserted score - name: %s score: %s game_time_elapsed: %s",
                    playerState.getPlayer().getName(), playerState.getScore(), gameContext.getGameTimeElapsed()));
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in saveScore().", e);
        }
        return false;
    }
}
