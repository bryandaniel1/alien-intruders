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

import com.daniel.alienintruders.context.GameContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.StringUtils;

/**
 * The panel displaying options to read instructions and start the game.
 *
 * @author Bryan Daniel
 */
public class MainMenuPanel extends JPanel implements ActionListener {

    /**
     * The command to display instructions
     */
    public static final String INSTRUCTIONS_COMMAND = "Instructions";

    /**
     * The command to start the game
     */
    public static final String START_COMMAND = "Start Game";

    /**
     * The command to show the scoreboard
     */
    public static final String SCORES_COMMAND = "Scoreboard";

    /**
     * The command to show the credits
     */
    public static final String CREDITS_COMMAND = "Credits";

    /**
     * The command to exit the game
     */
    public static final String EXIT_COMMAND = "Exit";

    /**
     * The game context
     */
    private final GameContext gameContext;

    /**
     * The main frame
     */
    private final GameMainFrame gameMainFrame;

    /**
     * The button to display game instructions
     */
    private GameButton instructionsButton;

    /**
     * The button starting the game
     */
    private GameButton startButton;

    /**
     * The button to display scores
     */
    private GameButton scoresButton;

    /**
     * The button to display credits
     */
    private GameButton creditsButton;

    /**
     * The button to exit the game
     */
    private GameButton exitButton;

    /**
     * Sets the value for the game main frame and initializes itself.
     *
     * @param gameMainFrame the main frame
     * @param gameContext the game context
     */
    public MainMenuPanel(GameMainFrame gameMainFrame, GameContext gameContext) {
        this.gameMainFrame = gameMainFrame;
        this.gameContext = gameContext;
        initialize();
    }

    /**
     * Initializes variables and layout for the panel.
     */
    private void initialize() {
        setBounds(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        setBackground(new Color(240, 240, 240).darker());
        setLayout(new BorderLayout());

        // int rows, int cols, int hgap, int vgap
        GridLayout gridLayout = new GridLayout(5, 1, 0, 50);
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(gridLayout);

        // top, left, bottom, right
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(50, 400, 50, 400));
        optionsPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);

        // title
        TitlePanel titlePanel = new TitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);

        // instructions
        instructionsButton = new GameButton(INSTRUCTIONS_COMMAND);
        instructionsButton.addActionListener(this);
        JPanel instructionsButtonPanel = new JPanel();
        instructionsButtonPanel.setLayout(new BorderLayout());
        instructionsButtonPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        instructionsButtonPanel.add(instructionsButton);
        optionsPanel.add(instructionsButtonPanel);

        // start
        startButton = new GameButton(START_COMMAND);
        startButton.addActionListener(this);
        JPanel startButtonPanel = new JPanel();
        startButtonPanel.setLayout(new BorderLayout());
        startButtonPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        startButtonPanel.add(startButton);
        optionsPanel.add(startButtonPanel);

        // scores
        scoresButton = new GameButton(SCORES_COMMAND);
        scoresButton.addActionListener(this);
        JPanel scoresButtonPanel = new JPanel();
        scoresButtonPanel.setLayout(new BorderLayout());
        scoresButtonPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        scoresButtonPanel.add(scoresButton);
        optionsPanel.add(scoresButtonPanel);

        // credits
        creditsButton = new GameButton(CREDITS_COMMAND);
        creditsButton.addActionListener(this);
        JPanel creditsButtonPanel = new JPanel();
        creditsButtonPanel.setLayout(new BorderLayout());
        creditsButtonPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        creditsButtonPanel.add(creditsButton);
        optionsPanel.add(creditsButtonPanel);

        // exit
        exitButton = new GameButton(EXIT_COMMAND);
        exitButton.addActionListener(this);
        JPanel exitButtonPanel = new JPanel();
        exitButtonPanel.setLayout(new BorderLayout());
        exitButtonPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        exitButtonPanel.add(exitButton);
        optionsPanel.add(exitButtonPanel);
    }

    /**
     * This void method responds to the action commands of the buttons.
     *
     * @param e the ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String buttonString = e.getActionCommand();
        switch (buttonString) {
            case INSTRUCTIONS_COMMAND:
                gameMainFrame.openInstructions();
                break;
            case START_COMMAND:
                instructionsButton.setEnabled(false);
                startButton.setEnabled(false);
                scoresButton.setEnabled(false);
                creditsButton.setEnabled(false);
                exitButton.setEnabled(false);
                String username = JOptionPane.showInputDialog("Enter your name");
                if (!StringUtils.isBlank(username)) {
                    CountDownLatch readyLatch = new CountDownLatch(1);
                    gameContext.initialize(username, readyLatch);
                    new Thread(() -> {
                        try {
                            readyLatch.await();
                            SwingUtilities.invokeLater(() -> {
                                gameMainFrame.openGame();
                            });
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }).start();
                } else {
                    instructionsButton.setEnabled(true);
                    startButton.setEnabled(true);
                    scoresButton.setEnabled(true);
                    creditsButton.setEnabled(true);
                    exitButton.setEnabled(true);
                }
                break;
            case SCORES_COMMAND:
                gameMainFrame.showScores();
                break;
            case CREDITS_COMMAND:
                gameMainFrame.showCredits();
                break;
            case EXIT_COMMAND:
                gameMainFrame.exit();
                break;
            default:
                break;
        }
    }
}
