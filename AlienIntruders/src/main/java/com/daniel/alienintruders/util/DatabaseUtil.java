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
package com.daniel.alienintruders.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains utility database functions.
 *
 * @author Bryan Daniel
 */
public class DatabaseUtil {

    /**
     * The URL for the database
     */
    public static final String DB_URL = "jdbc:derby:alien_intruders;create=true;user=alien_intruders";

    /**
     * The logger for this class
     */
    public static final Logger LOGGER = LogManager.getLogger(DatabaseUtil.class);

    /**
     * Verifies that the schema and tables for Alien Intruders have been created. If not, this method creates them.
     */
    public static void verifyDatabase() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e1) {
            LOGGER.error("ClassNotFoundException for the Derby embedded driver.", e1);
        }
        try (Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet results = metaData.getTables(null, "ALIEN_INTRUDERS", "PLAYER", null);
            if (!results.next()) {
                LOGGER.info("Creating tables in alienintruders schema.");
                createTables(statement);

                results.close();
                metaData = connection.getMetaData();
                results = metaData.getTables(null, "ALIENINTRUDERS", "PLAYER", null);
                if (results.next()) {
                    LOGGER.info("Database created successfully.");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in verifyDatabase().", e);
        }
    }

    /**
     * Creates the tables for the game and inserts reference data.
     *
     * @param statement the Statement object to use.
     * @throws SQLException
     */
    private static void createTables(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE player ("
                + "name VARCHAR(255), "
                + "last_game_started TIMESTAMP, "
                + "CONSTRAINT player_pk PRIMARY KEY (name)"
                + ")");

        statement.executeUpdate("CREATE TABLE alien_wave ("
                + "wave INTEGER, "
                + "description VARCHAR(255), "
                + "number_of_rows INTEGER, "
                + "number_of_columns INTEGER, "
                + "missile_speed INTEGER, "
                + "points INTEGER, "
                + "CONSTRAINT alien_wave_pk PRIMARY KEY (wave)"
                + ")");

        statement.executeUpdate("CREATE TABLE player_state ("
                + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "player_name VARCHAR(255) NOT NULL UNIQUE REFERENCES player (name), "
                + "alien_wave INTEGER REFERENCES alien_wave (wave), "
                + "terminated BOOLEAN, "
                + "score INTEGER, "
                + "CONSTRAINT player_state_pk PRIMARY KEY (id)"
                + ")");

        statement.executeUpdate("CREATE TABLE scoreboard_entry ("
                + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "player_name VARCHAR(255) REFERENCES player (name), "
                + "score INTEGER, "
                + "game_time_elapsed BIGINT, "
                + "CONSTRAINT scoreboard_entry_pk PRIMARY KEY (id)"
                + ")");

        statement.executeUpdate(
                "INSERT INTO alien_wave "
                + "VALUES (1, 'The first wave. Easy difficulty.', 5, 7, 4, 100)");
        statement.executeUpdate(
                "INSERT INTO alien_wave "
                + "VALUES (2, 'The second wave. Medium difficulty.', 5, 8, 6, 200)");
        statement.executeUpdate(
                "INSERT INTO alien_wave "
                + "VALUES (3, 'The third wave. Hard difficulty.', 5, 9, 8, 300)");
        statement.executeUpdate(
                "INSERT INTO alien_wave "
                + "VALUES (4, 'The fourth wave. Extreme difficulty.', 5, 10, 10, 400)");
    }
}
