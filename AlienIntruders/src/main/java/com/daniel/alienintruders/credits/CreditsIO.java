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

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reads the credits file to produce Credit objects for display.
 *
 * @author Bryan Daniel
 */
public class CreditsIO {

    /**
     * The URL for the credits file
     */
    public static final String CREDITS_URL = "credits.csv";

    /**
     * The logger for this class
     */
    public static final Logger LOGGER = LogManager.getLogger(CreditsIO.class);

    /*
     * Private constructor - not instantiated
     */
    private CreditsIO() {
    }

    /**
     * Reads the credits file and returns the list of credits to display.
     *
     * @return the list of credits
     */
    public static List<Credit> findCredits() {
        List<Credit> credits = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(CreditsIO.class.getClassLoader()
                .getResourceAsStream(CREDITS_URL)))) {
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(Credit.class);
            credits = new CsvToBeanBuilder(reader)
                    .withType(Credit.class)
                    .withMappingStrategy(mappingStrategy)
                    .build()
                    .parse();
        } catch (IOException ex) {
            LOGGER.error("IOException occurred in findCredits().", ex);
        }
        return credits;
    }
}
