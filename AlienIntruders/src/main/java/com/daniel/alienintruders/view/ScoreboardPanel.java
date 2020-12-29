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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

/**
 * Displays the list of scores ordered from greatest to lowest.
 *
 * @author Bryan Daniel
 */
public class ScoreboardPanel extends JPanel implements ActionListener {

    /**
     * The command to close the scoreboard
     */
    public static final String CLOSE_COMMAND = "Close";

    /**
     * The main frame
     */
    private final GameMainFrame gameMainFrame;

    /**
     * The game scores to display
     */
    private final List<ScoreboardEntry> scores;

    /**
     * Sets the values for the main frame and the scores.
     *
     * @param gameMainFrame the main frame
     * @param scores the list of scores
     */
    public ScoreboardPanel(GameMainFrame gameMainFrame,
            List<ScoreboardEntry> scores) {
        this.gameMainFrame = gameMainFrame;
        this.scores = scores;
        initialize();
    }

    /**
     * Initializes this scoreboard.
     */
    private void initialize() {
        setBounds(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        setBackground(GameMainFrame.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        ScoreboardTableModel model = new ScoreboardTableModel(scores);
        JTable table = new JTable(model);
        ScoreboardCellRenderer cellRenderer = new ScoreboardCellRenderer();
        table.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(10, 20, 10, 20);  //top, left, bottom, right
        tablePanel.add(scrollPane, constraints);

        GameButton closeButton = new GameButton(CLOSE_COMMAND);
        closeButton.addActionListener(this);
        JPanel closeButtonPanel = new JPanel();
        closeButtonPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        closeButtonPanel.add(closeButton);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(10, 20, 65, 20);  //top, left, bottom, right
        constraints.anchor = GridBagConstraints.SOUTH;
        tablePanel.add(closeButtonPanel, constraints);

        TitledBorder scoreboardBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Scoreboard",
                TitledBorder.CENTER, TitledBorder.TOP);
        scoreboardBorder.setTitleFont(new Font("Arial", Font.BOLD, 20));
        scoreboardBorder.setTitleColor(Color.BLACK);
        tablePanel.setBorder(scoreboardBorder);

        add(new TitlePanel(), BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonString = e.getActionCommand();
        switch (buttonString) {
            case CLOSE_COMMAND:
                gameMainFrame.closeScoreboard();
                break;
            default:
                break;
        }
    }
}
