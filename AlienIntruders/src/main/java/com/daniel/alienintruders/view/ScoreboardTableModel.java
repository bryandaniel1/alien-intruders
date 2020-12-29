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
package com.daniel.alienintruders.view;

import com.daniel.alienintruders.database.entity.ScoreboardEntry;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * The table model to manage scoreboard data.
 *
 * @author Bryan Daniel
 */
public class ScoreboardTableModel extends AbstractTableModel {

    /**
     * The column headers
     */
    private final String[] columnNames = {"Rank", "Player", "Score", "Time (seconds)"};

    /**
     * The list of scores
     */
    private final List<ScoreboardEntry> scores;

    /**
     * Sets the value for scores.
     *
     * @param scores the scores to display
     */
    public ScoreboardTableModel(List<ScoreboardEntry> scores) {
        this.scores = scores;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return scores.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String value = "";
        String column = columnNames[columnIndex];
        switch (column) {
            case "Rank":
                value = String.valueOf(rowIndex + 1);
                break;
            case "Player":
                value = scores.get(rowIndex).getPlayerName();
                break;
            case "Score":
                value = String.valueOf(scores.get(rowIndex).getScore());
                break;
            case "Time (seconds)":
                value = String.valueOf(scores.get(rowIndex).getGameTimeElapsed());
                break;
            default:
                break;
        }
        return value;
    }
}
