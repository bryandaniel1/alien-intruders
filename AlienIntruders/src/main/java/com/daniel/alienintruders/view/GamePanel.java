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

import com.daniel.alienintruders.loop.GameLoop;
import com.daniel.alienintruders.context.GameContext;
import com.daniel.alienintruders.database.GameDataAccess;
import com.daniel.alienintruders.database.entity.Player;
import com.daniel.alienintruders.image.GifFrame;
import com.daniel.alienintruders.image.ImageFactory;
import com.daniel.alienintruders.image.ImageType;
import com.daniel.alienintruders.input.GameInputListener;
import com.daniel.alienintruders.sprite.Alien;
import com.daniel.alienintruders.sprite.AlienMissile;
import com.daniel.alienintruders.sprite.Laser;
import com.daniel.alienintruders.sprite.Spaceship;
import com.daniel.alienintruders.sprite.Sprite;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The panel displaying all objects and events of the game battles.
 *
 * @author Bryan Daniel
 */
public class GamePanel extends JPanel {

    /**
     * The panel width
     */
    public static final int PANEL_WIDTH = 1200;

    /**
     * The panel height
     */
    public static final int PANEL_HEIGHT = 750;

    /**
     * The height of the source image to use for the background
     */
    public static final int BACKGROUND_IMAGE_HEIGHT = 2133;

    /**
     * The time in milliseconds of the loop delay
     */
    public static final int LOOP_DELAY = 17;

    /**
     * The dialog title for a dead player
     */
    public static final String DEAD_DIALOG_TITLE = "YOU DIED!";

    /**
     * The dialog message for a dead player
     */
    public static final String DEAD_DIALOG_MESSAGE = "You died. Do you want to play again?";

    /**
     * The dialog title for a victorious player
     */
    public static final String VICTORY_DIALOG_TITLE = "VICTORY!";

    /**
     * The dialog message for a victorious player
     */
    public static final String VICTORY_DIALOG_MESSAGE = "Great shooting! Do you want to play again?";

    /**
     * The game context
     */
    private final GameContext gameContext;

    /**
     * The main frame
     */
    private final GameMainFrame gameMainFrame;

    /**
     * The background image
     */
    private Image backgroundImage;

    /**
     * The second background image
     */
    private Image secondBackgroundImage;

    /**
     * The first x coordinate of the source image
     */
    private int backgroundImageX1;

    /**
     * The first y coordinate of the source image
     */
    private int backgroundImageY1;

    /**
     * The second x coordinate of the source image
     */
    private int backgroundImageX2;

    /**
     * The second y coordinate of the source image
     */
    private int backgroundImageY2;

    /**
     * The loop timer
     */
    private Timer timer;

    /**
     * Indicates whether or not the options dialog is displayed
     */
    private boolean optionShowing = false;

    /**
     * Indicates whether or not the victory message is displayed
     */
    private boolean victoryShowing = false;

    /**
     * Indicates whether or not the victory animation is playing
     */
    private boolean playingVictoryAnimation = false;

    /**
     * The logger for this class
     */
    private final Logger logger;

    /**
     * Sets the value for the main frame and initializes the game panel.
     *
     * @param gameMainFrame the main frame
     * @param gameContext the game context
     */
    public GamePanel(GameMainFrame gameMainFrame, GameContext gameContext) {
        this.gameMainFrame = gameMainFrame;
        this.gameContext = gameContext;
        logger = LogManager.getLogger(GamePanel.class);
        initialize();
    }

    /**
     * Initializes variables and layout for the panel.
     */
    private void initialize() {
        setBounds(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        backgroundImageX1 = 0;
        backgroundImageY1 = BACKGROUND_IMAGE_HEIGHT - PANEL_HEIGHT;
        backgroundImageX2 = PANEL_WIDTH;
        backgroundImageY2 = BACKGROUND_IMAGE_HEIGHT;
        backgroundImage = ImageFactory.createImage(ImageType.SKY).getImage();
        secondBackgroundImage = ImageFactory.createImage(ImageType.SKY).getImage();
        addKeyListener(new GameInputListener(gameContext));
        setFocusable(true);
        setOptionShowing(false);
        timer = new Timer(LOOP_DELAY, new GameLoop(this, gameContext));
        timer.start();
    }

    /**
     * Starts running the game loop.
     */
    public void startGame() {
        requestFocus();
        int wave = gameContext.getPlayerState().getAlienWave().getWave();
        new Thread(() -> {
            gameContext.getBuildUpAudioPlayer().playSound();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(() -> {
                gameMainFrame.showMessage("WAVE " + wave);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(() -> {
                gameMainFrame.showMessage("FIGHT!");
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(() -> {
                gameMainFrame.removeMessage();
                gameContext.setGameRunning(true);
            });
            gameContext.getBackgroundAudioPlayer().playSound();
        }).start();
    }

    /**
     * Draws the background.
     *
     * @param graphics the Graphics object
     */
    private void drawBackground(Graphics graphics) {
        backgroundImageY1 -= 1;
        backgroundImageY2 -= 1;

        /*
         * If the top of the first background image has dropped into view, fill the space with the bottom of the second
         * image.
         */
        if (backgroundImageY1 < 0 && backgroundImageY2 > 0) {
            graphics.drawImage(secondBackgroundImage,
                    0, 0, // destination x1, y1
                    PANEL_WIDTH, -backgroundImageY1, // destination x2, y2
                    backgroundImageX1, BACKGROUND_IMAGE_HEIGHT + backgroundImageY1, // source x1, y2
                    backgroundImageX2, BACKGROUND_IMAGE_HEIGHT, // source x2, y2
                    null);
            graphics.drawImage(backgroundImage,
                    0, -backgroundImageY1, // destination x1, y1
                    PANEL_WIDTH, PANEL_HEIGHT, // destination x2, y2
                    backgroundImageX1, 0, // source x1, y2
                    backgroundImageX2, backgroundImageY2, // source x2, y2
                    null);
        } else {
            if (backgroundImageY2 <= 0) {
                backgroundImageY1 = BACKGROUND_IMAGE_HEIGHT - PANEL_HEIGHT;
                backgroundImageY2 = BACKGROUND_IMAGE_HEIGHT;
            }
            graphics.drawImage(backgroundImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT,
                    backgroundImageX1, backgroundImageY1, backgroundImageX2,
                    backgroundImageY2, null);
        }
    }

    /**
     * Draws the spaceship.
     *
     * @param graphics the Graphics object
     */
    private void drawSpaceship(Graphics graphics) {
        Spaceship spaceship = gameContext.getSpaceship();
        if (!spaceship.isDead()) {
            graphics.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), Spaceship.SPACESHIP_WIDTH,
                    Spaceship.SPACESHIP_HEIGHT, null);
        }
    }

    /**
     * Draws the laser.
     *
     * @param graphics the Graphics object
     */
    private void drawLaser(Graphics graphics) {
        Spaceship spaceship = gameContext.getSpaceship();
        Laser laser = spaceship.getLaser();
        if (laser != null && !laser.isDead()) {
            graphics.drawImage(laser.getImage(), laser.getX(), laser.getY(), Laser.LASER_WIDTH,
                    Laser.LASER_HEIGHT, null);
        }
    }

    /**
     * Draws the aliens.
     *
     * @param graphics the Graphics object
     */
    private void drawAliens(Graphics graphics) {
        gameContext.getAlienList().stream().
                filter((alien) -> (!alien.isDead())).
                forEachOrdered((alien) -> {
                    graphics.drawImage(alien.getImage(), alien.getX(), alien.getY(), Alien.ALIEN_WIDTH,
                            Alien.ALIEN_HEIGHT, null);
                });
    }

    /**
     * Draws the alien missiles.
     *
     * @param graphics the Graphics object
     */
    private void drawMissiles(Graphics graphics) {
        gameContext.getAlienList().stream().
                filter((alien) -> (alien.getFiredMissile() != null && !alien
                .getFiredMissile().isDead()))
                .forEachOrdered((alien) -> {
                    AlienMissile firedMissile = alien.getFiredMissile();
                    graphics.drawImage(firedMissile.getImage(), firedMissile.getX(), firedMissile.getY(),
                            AlienMissile.MISSILE_WIDTH, AlienMissile.MISSILE_HEIGHT, null);
                });
    }

    /**
     * Adds an explosion for the specified sprite to the explosion queue.
     *
     * @param sprite the sprite to explode
     * @param spriteWidth the sprite width
     * @param spriteHeight the sprite height
     */
    private void addExplosion(Sprite sprite, int spriteWidth, int spriteHeight) {
        ImageReader reader = null;
        try {
            reader = ImageIO.getImageReadersBySuffix("GIF").next();
            ImageInputStream in = ImageIO.createImageInputStream(ImageFactory.class.getClassLoader()
                    .getResourceAsStream(ImageFactory.EXPLOSION_URL));
            reader.setInput(in);
            Queue<GifFrame> animationFrames = new LinkedList<>();
            int count = reader.getNumImages(true);
            for (int i = 0; i < count; i++) {
                BufferedImage bufferedImage = reader.read(i);
                GifFrame animationFrame = new GifFrame(bufferedImage, sprite.getX(), sprite.getY(), spriteWidth,
                        spriteHeight);
                animationFrames.add(animationFrame);
            }
            gameContext.getExplosionAnimationImages().add(animationFrames);
        } catch (IOException ex) {
            logger.error("IOException occurred in addExplosion.", ex);
        } finally {
            if (reader != null) {
                reader.dispose();
            }
        }
    }

    /**
     * Checks for killed aliens, queues an explosion for each, and removes the dead aliens from the living aliens map.
     */
    private void addAlienExplosions() {
        List<Alien> aliensToRemove = new ArrayList<>();
        for (Alien alien : gameContext.getLivingAliens().keySet()) {
            if (!gameContext.getLivingAliens().get(alien)) {
                addExplosion(alien, Alien.ALIEN_WIDTH, Alien.ALIEN_HEIGHT);
                aliensToRemove.add(alien);
            }
        }
        for (Alien alien : aliensToRemove) {
            gameContext.getLivingAliens().remove(alien);
        }
    }

    /**
     * Checks for an exploded spaceship and queues the explosion.
     */
    private void addSpaceshipExplosion() {
        Spaceship spaceship = gameContext.getSpaceship();
        if (spaceship.isDead() && spaceship.isExplode()) {
            spaceship.setExplode(false);
            addExplosion(spaceship, Spaceship.SPACESHIP_WIDTH,
                    Spaceship.SPACESHIP_HEIGHT);
        }
    }

    /**
     * Draws the animations for all explosions.
     *
     * @param graphics the Graphics object
     */
    private void drawExplosions(Graphics graphics) {
        Queue<Queue<GifFrame>> explosionAnimationImages = gameContext.
                getExplosionAnimationImages();
        if (!explosionAnimationImages.isEmpty()) {
            Iterator<Queue<GifFrame>> queueIterator = explosionAnimationImages.iterator();
            while (queueIterator.hasNext()) {
                Queue<GifFrame> animationFrames = queueIterator.next();
                if (animationFrames.isEmpty()) {
                    queueIterator.remove();
                } else {
                    GifFrame gifFrame = animationFrames.remove();
                    graphics.drawImage(gifFrame.getImage(), gifFrame.getX() - gifFrame.getWidth() / 2,
                            gifFrame.getY() - gifFrame.getHeight() / 2, gifFrame.getWidth() * 2,
                            gifFrame.getHeight() * 2, this);
                }
            }
        }
    }

    /**
     * Moves the spaceship forward for final victory.
     */
    private void drawVictoryAnimation() {
        gameContext.getSpaceship().setY(gameContext.getSpaceship().getY() - 1);
        if (gameContext.getSpaceship().getY() < 0 - Spaceship.SPACESHIP_HEIGHT) {
            playingVictoryAnimation = false;
            showOptionDialog(VICTORY_DIALOG_MESSAGE, VICTORY_DIALOG_TITLE);
        }
    }

    /**
     * Draws game objects using the provided Graphics object
     *
     * @param graphics the Graphics object
     */
    private void doDrawing(Graphics graphics) {
        drawBackground(graphics);
        if (gameContext.isGameInitialized()) {
            drawSpaceship(graphics);
            drawAliens(graphics);
            if (gameContext.isGameRunning()) {
                drawLaser(graphics);
                addAlienExplosions();
                drawMissiles(graphics);
                addSpaceshipExplosion();
                drawExplosions(graphics);
            }
            if (playingVictoryAnimation) {
                drawVictoryAnimation();
            }
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        doDrawing(graphics);
    }

    /**
     * Shows the available options when the player dies.
     *
     * @param message the dialog message to display
     * @param title the dialog title
     */
    public void showOptionDialog(String message, String title) {
        Timer deadTimer = new Timer(1, (ActionEvent e) -> {
            String[] options = {"Play Again", "Main Menu", "Exit Game"};
            int selection = JOptionPane.showOptionDialog(this, message, title, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch (selection) {
                case 0:
                    new Thread(() -> {
                        gameContext.getPlayerState().setTerminated(true);
                        gameContext.setGameTimeElapsed(getTimePlayed());
                        if (!VICTORY_DIALOG_MESSAGE.equals(message)) {
                            saveGame();
                        }
                        SwingUtilities.invokeLater(() -> {
                            gameContext.setGameRunning(false);
                            timer.stop();
                            gameMainFrame.restartGame();
                        });
                    }).start();
                    break;
                case 1:
                    new Thread(() -> {
                        gameContext.getPlayerState().setTerminated(true);
                        gameContext.setGameTimeElapsed(getTimePlayed());
                        if (!VICTORY_DIALOG_MESSAGE.equals(message)) {
                            saveGame();
                        }
                        SwingUtilities.invokeLater(() -> {
                            gameMainFrame.showMainMenu();
                        });
                    }).start();
                    break;
                case 2:
                case JOptionPane.CLOSED_OPTION:
                    new Thread(() -> {
                        gameContext.getPlayerState().setTerminated(true);
                        gameContext.setGameTimeElapsed(getTimePlayed());
                        if (!VICTORY_DIALOG_MESSAGE.equals(message)) {
                            saveGame();
                        }
                        SwingUtilities.invokeLater(() -> {
                            gameMainFrame.exit();
                        });
                    }).start();
                    break;
                default:
                    break;
            }
        });
        deadTimer.setRepeats(false);
        deadTimer.start();
    }

    /**
     * Displays the victory message to the player.
     */
    public void showVictory() {
        Timer victoryTimer = new Timer(1, (ActionEvent e) -> {
            gameMainFrame.showMessage("VICTORY!");
            Timer saveTimer = new Timer(1, (ActionEvent e2) -> {
                new Thread(() -> {
                    gameContext.getBackgroundAudioPlayer().stopSound();
                    gameContext.getBackgroundAudioPlayer().close();
                    int currentWave = gameContext.getPlayerState()
                            .getAlienWave().getWave();
                    if (currentWave < GameDataAccess.getAlienWaveCount()) {
                        logger.info("Alien wave defeated: " + currentWave);
                        logger.info("Proceeding to next alien wave...");
                        gameContext.getPlayerState().setAlienWave(GameDataAccess
                                .findAlienWave(currentWave + 1));
                        saveGame();
                        logger.info("Player status has been saved.");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        SwingUtilities.invokeLater(() -> {
                            gameContext.setGameRunning(false);
                            timer.stop();
                            gameMainFrame.removeMessage();
                            gameMainFrame.restartGame();
                        });
                    } else {
                        logger.info("All alien waves have been defeated.");
                        gameContext.getPlayerState().setTerminated(true);
                        gameContext.setGameTimeElapsed(getTimePlayed());
                        saveGame();
                        playingVictoryAnimation = true;
                        gameContext.getVictoryAudioPlayer().playSound();
                        logger.info("Final victory saved.");
                    }
                }).start();
            });
            saveTimer.setRepeats(false);
            saveTimer.start();
        });
        victoryTimer.setRepeats(false);
        victoryTimer.start();
    }

    /**
     * Saves the game. This method should not be executed on the EDT.
     */
    private void saveGame() {
        CountDownLatch savedLatch = new CountDownLatch(1);
        gameContext.save(savedLatch);
        try {
            savedLatch.await();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Gets the value of optionShowing.
     *
     * @return the flag for display of the options dialog
     */
    public boolean isOptionShowing() {
        return optionShowing;
    }

    /**
     * Sets the value of optionShowing.
     *
     * @param optionShowing the new value of optionShowing to set
     */
    public void setOptionShowing(boolean optionShowing) {
        this.optionShowing = optionShowing;
    }

    /**
     * Gets the value of victoryShowing.
     *
     * @return the flag for display of the victory message
     */
    public boolean isVictoryShowing() {
        return victoryShowing;
    }

    /**
     * Sets the value of victoryShowing.
     *
     * @param victoryShowing the new value of victoryShowing to set
     */
    public void setVictoryShowing(boolean victoryShowing) {
        this.victoryShowing = victoryShowing;
    }

    /**
     * Returns the time in seconds in which this game was played.
     *
     * @return the game time
     */
    private long getTimePlayed() {
        Player player = gameContext.getPlayerState().getPlayer();
        return (System.currentTimeMillis() - Timestamp.valueOf(player.getLastGameStarted()).getTime()) / 1000;
    }
}
