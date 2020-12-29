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

import com.daniel.alienintruders.database.entity.AlienWave;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * Displays game instructions.
 *
 * @author Bryan Daniel
 */
public class InstructionsPanel extends JPanel implements ActionListener {

    /**
     * The command to close the scoreboard
     */
    public static final String CLOSE_COMMAND = "Close";

    /**
     * The main frame
     */
    private final GameMainFrame gameMainFrame;

    /**
     * The list of alien waves
     */
    private final List<AlienWave> alienWaves;

    /**
     * Sets the value for the main frame and initializes the instructions panel.
     *
     * @param gameMainFrame the main frame
     * @param alienWaves the alien waves
     */
    public InstructionsPanel(GameMainFrame gameMainFrame, List<AlienWave> alienWaves) {
        this.gameMainFrame = gameMainFrame;
        this.alienWaves = alienWaves;
        initialize();
    }

    /**
     * Initializes variables and layout for the panel.
     */
    private void initialize() {
        setBounds(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        setBackground(GameMainFrame.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        add(new TitlePanel(), BorderLayout.NORTH);
        add(buildInstructionsPanel(), BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonString = e.getActionCommand();
        switch (buttonString) {
            case CLOSE_COMMAND:
                gameMainFrame.closeInstructions();
                break;
            default:
                break;
        }
    }

    /**
     * Creates and returns the panel containing the game instructions.
     *
     * @return the instructions panel
     */
    private JPanel buildInstructionsPanel() {
        JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        /*
         * Inputs
         */
        JPanel instructionsPanel = new JPanel(new GridBagLayout());
        instructionsPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        JLabel description = new JLabel("Protect humanity from " + alienWaves.size()
                + " waves of hostile alien intruders!");
        description.setBackground(GameMainFrame.BACKGROUND_COLOR);
        description.setForeground(Color.BLACK);
        description.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(5, 5, 5, 5);
        instructionsPanel.add(description, constraints);

        JLabel keyboardLabel = new JLabel("Keyboard Inputs:");
        keyboardLabel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        keyboardLabel.setForeground(Color.BLACK);
        keyboardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        instructionsPanel.add(keyboardLabel, constraints);

        JLabel leftInput = new JLabel("Left arrow");
        leftInput.setBackground(GameMainFrame.BACKGROUND_COLOR);
        leftInput.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        instructionsPanel.add(leftInput, constraints);

        JLabel leftEquals = new JLabel("=");
        leftEquals.setBackground(GameMainFrame.BACKGROUND_COLOR);
        leftEquals.setForeground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 2;
        instructionsPanel.add(leftEquals, constraints);

        JLabel leftValue = new JLabel("Move left");
        leftValue.setBackground(GameMainFrame.BACKGROUND_COLOR);
        leftValue.setForeground(Color.BLACK);
        constraints.gridx = 2;
        constraints.gridy = 2;
        instructionsPanel.add(leftValue, constraints);

        JLabel rightInput = new JLabel("Right arrow");
        rightInput.setBackground(GameMainFrame.BACKGROUND_COLOR);
        rightInput.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        instructionsPanel.add(rightInput, constraints);

        JLabel rightEquals = new JLabel("=");
        rightEquals.setBackground(GameMainFrame.BACKGROUND_COLOR);
        rightEquals.setForeground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 3;
        instructionsPanel.add(rightEquals, constraints);

        JLabel rightValue = new JLabel("Move right");
        rightValue.setBackground(GameMainFrame.BACKGROUND_COLOR);
        rightValue.setForeground(Color.BLACK);
        constraints.gridx = 2;
        constraints.gridy = 3;
        instructionsPanel.add(rightValue, constraints);

        JLabel spaceInput = new JLabel("Spacebar");
        spaceInput.setBackground(GameMainFrame.BACKGROUND_COLOR);
        spaceInput.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        instructionsPanel.add(spaceInput, constraints);

        JLabel spaceEquals = new JLabel("=");
        spaceEquals.setBackground(GameMainFrame.BACKGROUND_COLOR);
        spaceEquals.setForeground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 4;
        instructionsPanel.add(spaceEquals, constraints);

        JLabel spaceValue = new JLabel("Fire laser beam");
        spaceValue.setBackground(GameMainFrame.BACKGROUND_COLOR);
        spaceValue.setForeground(Color.BLACK);
        constraints.gridx = 2;
        constraints.gridy = 4;
        instructionsPanel.add(spaceValue, constraints);

        JLabel pointsLabel = new JLabel("Points:");
        pointsLabel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        pointsLabel.setForeground(Color.BLACK);
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 3;
        instructionsPanel.add(pointsLabel, constraints);

        /*
         * Points
         */
        for (AlienWave wave : alienWaves) {

            JLabel alienKilled = new JLabel("Wave " + wave.getWave() + " alien");
            alienKilled.setBackground(GameMainFrame.BACKGROUND_COLOR);
            alienKilled.setForeground(Color.BLACK);
            constraints.gridx = 0;
            ++constraints.gridy;
            constraints.gridwidth = 1;
            instructionsPanel.add(alienKilled, constraints);

            JLabel alienEquals = new JLabel("=");
            alienEquals.setBackground(GameMainFrame.BACKGROUND_COLOR);
            alienEquals.setForeground(Color.BLACK);
            constraints.gridx = 1;
            instructionsPanel.add(alienEquals, constraints);

            JLabel alienValue = new JLabel(wave.getPoints() + " points");
            alienValue.setBackground(GameMainFrame.BACKGROUND_COLOR);
            alienValue.setForeground(Color.BLACK);
            constraints.gridx = 2;
            instructionsPanel.add(alienValue, constraints);
        }

        /*
         * Instructions panel added to base
         */
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(10, 20, 10, 20);  //top, left, bottom, right
        basePanel.add(instructionsPanel, constraints);

        /*
         * Close button added to base
         */
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
        basePanel.add(closeButtonPanel, constraints);

        /*
         * Border added
         */
        TitledBorder instructionsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Instructions", TitledBorder.CENTER, TitledBorder.TOP);
        instructionsBorder.setTitleFont(new Font("Arial", Font.BOLD, 20));
        instructionsBorder.setTitleColor(Color.BLACK);
        basePanel.setBorder(instructionsBorder);

        return basePanel;
    }
}
