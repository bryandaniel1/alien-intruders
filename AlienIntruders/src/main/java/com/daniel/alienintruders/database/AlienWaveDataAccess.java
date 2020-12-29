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
 * Connects to the database to retrieve alien wave information.
 *
 * @author Bryan Daniel
 */
public class AlienWaveDataAccess {

    /**
     * The logger for this class
     */
    public static final Logger LOGGER = LogManager.getLogger(AlienWaveDataAccess.class);

    /**
     * Private constructor - not instantiated
     */
    private AlienWaveDataAccess() {
    }

    /**
     * Retrieves the alien wave associated with the given identifier.
     *
     * @param wave the number of the wave to retrieve
     * @return the alien wave
     */
    static AlienWave findAlienWave(int wave) {
        AlienWave alienWave = null;
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * "
                        + "FROM alien_wave w "
                        + "WHERE w.wave = ?")) {
            preparedStatement.setInt(1, wave);
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                alienWave = new AlienWave();
                alienWave.setWave(wave);
                alienWave.setDescription(results.getString("description"));
                alienWave.setNumberOfRows(results.getInt("number_of_rows"));
                alienWave.setNumberOfColumns(results.getInt("number_of_columns"));
                alienWave.setMissileSpeed(results.getInt("missile_speed"));
                alienWave.setPoints(results.getInt("points"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in findAlienWave().", e);
        }
        return alienWave;
    }

    /**
     * Retrieves all alien waves from the database.
     *
     * @return the list of alien waves
     */
    static List<AlienWave> findAllAlienWaves() {
        List<AlienWave> alienWaves = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM alien_wave")) {
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                AlienWave alienWave = new AlienWave();
                alienWave.setWave(results.getInt("wave"));
                alienWave.setDescription(results.getString("description"));
                alienWave.setNumberOfRows(results.getInt("number_of_rows"));
                alienWave.setNumberOfColumns(results.getInt("number_of_columns"));
                alienWave.setMissileSpeed(results.getInt("missile_speed"));
                alienWave.setPoints(results.getInt("points"));
                alienWaves.add(alienWave);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in findAllAlienWaves().", e);
        }
        return alienWaves;
    }

    /**
     * Retrieves the count of alien waves.
     *
     * @return the number of alien waves
     */
    static int getAlienWaveCount() {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(DatabaseUtil.DB_URL);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT COUNT(*) "
                        + "FROM alien_wave w")) {
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred in getAlienWaveCount().", e);
        }
        return count;
    }
}
