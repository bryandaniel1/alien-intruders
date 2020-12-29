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
import com.daniel.alienintruders.database.GameDataAccess;
import com.daniel.alienintruders.database.entity.AlienWave;
import com.daniel.alienintruders.database.entity.Player;
import com.daniel.alienintruders.database.entity.PlayerState;
import com.daniel.alienintruders.database.entity.ScoreboardEntry;
import com.daniel.alienintruders.image.ImageType;
import com.daniel.alienintruders.image.ImageFactory;
import com.daniel.alienintruders.util.DatabaseUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main frame containing all game UI components.
 *
 * @author Bryan Daniel
 */
public class GameMainFrame extends JFrame {

    /**
     * The title
     */
    public static final String GAME_TITLE = "Alien Intruders";

    /**
     * The background color
     */
    public static final Color BACKGROUND_COLOR = new Color(240, 240, 240)
            .darker();

    /**
     * The game context
     */
    private GameContext gameContext;

    /**
     * The main menu panel
     */
    private MainMenuPanel mainMenuPanel;

    /**
     * The scoreboard panel
     */
    private ScoreboardPanel scoreboardPanel;

    /**
     * The credits panel
     */
    private CreditsPanel creditsPanel;

    /**
     * The instructions panel
     */
    private InstructionsPanel instructionsPanel;

    /**
     * The game panel
     */
    private GamePanel gamePanel;

    /**
     * The message panel
     */
    private MessagePanel messagePanel;

    /**
     * The layered pane containing the panels
     */
    private JLayeredPane layeredPane;

    /**
     * The logger for this class.
     */
    private final Logger logger;

    /**
     * Default constructor.
     */
    public GameMainFrame() {
        logger = LogManager.getLogger(GameMainFrame.class);
        initialize();
    }

    /**
     * Initializes frame variables and layout.
     */
    private void initialize() {
        DatabaseUtil.verifyDatabase();
        gameContext = GameContext.getInstance();

        layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.BLACK);
        layeredPane.setOpaque(true);
        addLayers();
        add(layeredPane);

        setTitle(GAME_TITLE);
        setSize(new Dimension(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT));
        setIconImage(ImageFactory.createImage(ImageType.MOTHERSHIP).getImage());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds all panels to this frame.
     */
    private void addLayers() {
        gamePanel = new GamePanel(this, gameContext);
        mainMenuPanel = new MainMenuPanel(this, gameContext);
        messagePanel = new MessagePanel();

        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(mainMenuPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(messagePanel, JLayeredPane.POPUP_LAYER);
    }

    /**
     * Displays the main menu panel.
     */
    void showMainMenu() {
        logger.info("Returning to main menu...");
        mainMenuPanel.setLocation(-GamePanel.PANEL_WIDTH, 0);
        Timer startTimer = new Timer(1, (ActionEvent e) -> {
            mainMenuPanel.setLocation(mainMenuPanel.getX() + 1, 0);
            if (mainMenuPanel.getX() == 0) {
                ((Timer) e.getSource()).stop();
                layeredPane.removeAll();
                GameContext.destroy();
                gameContext = GameContext.getInstance();
                addLayers();
                logger.info("Main menu displayed.");
            }
        });
        startTimer.start();
    }

    /**
     * Slides the info panel aside and starts the game.
     */
    void openGame() {
        logger.info("Opening a new game...");
        Timer startTimer = new Timer(1, (ActionEvent e) -> {
            mainMenuPanel.setLocation(mainMenuPanel.getX() - 1, 0);
            if (mainMenuPanel.getX() + mainMenuPanel.getWidth() == 0) {
                ((Timer) e.getSource()).stop();
                gamePanel.startGame();
                logger.info("Game started.");
            }
        });
        startTimer.start();
    }

    /**
     * Restarts the game.
     */
    void restartGame() {
        logger.info("Restarting a game...");
        layeredPane.removeAll();
        PlayerState playerState = gameContext.getPlayerState();
        Player player = playerState.getPlayer();
        String playerName = player.getName();
        GameContext.destroy();
        CountDownLatch readyLatch = new CountDownLatch(1);
        gameContext = GameContext.getInstance();
        gameContext.initialize(playerName, readyLatch);
        new Thread(() -> {
            try {
                readyLatch.await();
                SwingUtilities.invokeLater(() -> {
                    addLayers();
                    mainMenuPanel.setLocation(-GamePanel.PANEL_WIDTH, 0);
                    gamePanel.startGame();
                    logger.info("Game restarted.");
                });
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Shows the given message to the player.
     *
     * @param message the message to display
     */
    void showMessage(String message) {
        messagePanel.setBounds(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        messagePanel.displayMessage(message);
    }

    /**
     * Moves the message panel out of view.
     */
    void removeMessage() {
        messagePanel.setLocation(-GamePanel.PANEL_WIDTH, 0);
    }

    /**
     * Slides the instructions panel into view.
     */
    void openInstructions() {
        new Thread(() -> {
            List<AlienWave> alienWaves = GameDataAccess.findAllAlienWaves();
            SwingUtilities.invokeLater(() -> {
                instructionsPanel = new InstructionsPanel(this, alienWaves);
                layeredPane.add(instructionsPanel, JLayeredPane.MODAL_LAYER);
                mainMenuPanel.setLocation(-GamePanel.PANEL_WIDTH, 0);
            });
        }).start();
    }

    /**
     * Closes the instructions.
     */
    void closeInstructions() {
        instructionsPanel.setBounds(-GamePanel.PANEL_WIDTH, 0, 0, GamePanel.PANEL_HEIGHT);
        mainMenuPanel.setLocation(0, 0);
    }

    /**
     * Displays the list of player scores.
     */
    void showScores() {
        new Thread(() -> {
            List<ScoreboardEntry> scores = GameDataAccess.findAllScores();
            SwingUtilities.invokeLater(() -> {
                scoreboardPanel = new ScoreboardPanel(this, scores);
                layeredPane.add(scoreboardPanel, JLayeredPane.MODAL_LAYER);
                mainMenuPanel.setLocation(-GamePanel.PANEL_WIDTH, 0);
            });
        }).start();
    }

    /**
     * Closes the scoreboard.
     */
    void closeScoreboard() {
        scoreboardPanel.setBounds(-GamePanel.PANEL_WIDTH, 0, 0, GamePanel.PANEL_HEIGHT);
        mainMenuPanel.setLocation(0, 0);
    }

    /**
     * Displays the game credits.
     */
    void showCredits() {
        new Thread(() -> {
            List<ScoreboardEntry> scores = GameDataAccess.findAllScores();
            SwingUtilities.invokeLater(() -> {
                creditsPanel = new CreditsPanel(this);
                layeredPane.add(creditsPanel, JLayeredPane.MODAL_LAYER);
                mainMenuPanel.setLocation(-GamePanel.PANEL_WIDTH, 0);
            });
        }).start();
    }

    /**
     * Closes the credits panel.
     */
    void closeCredits() {
        creditsPanel.setBounds(-GamePanel.PANEL_WIDTH, 0, 0, GamePanel.PANEL_HEIGHT);
        mainMenuPanel.setLocation(0, 0);
    }

    /**
     * Exits the game.
     */
    void exit() {
        System.exit(0);
    }
}
