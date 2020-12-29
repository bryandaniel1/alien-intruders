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
package com.daniel.alienintruders.context;

import com.daniel.alienintruders.database.GameDataAccess;
import com.daniel.alienintruders.database.entity.AlienWave;
import com.daniel.alienintruders.database.entity.PlayerState;
import com.daniel.alienintruders.image.GifFrame;
import com.daniel.alienintruders.sprite.Alien;
import com.daniel.alienintruders.sprite.Spaceship;
import com.daniel.alienintruders.sound.AudioPlayer;
import com.daniel.alienintruders.sound.BackgroundAudioPlayer;
import com.daniel.alienintruders.sound.BuildUpAudioPlayer;
import com.daniel.alienintruders.sound.ExplosionAudioPlayer;
import com.daniel.alienintruders.sound.GameLostAudioPlayer;
import com.daniel.alienintruders.sound.LaserAudioPlayer;
import com.daniel.alienintruders.sound.VictoryAudioPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * Holds frequently used values and functions for the game.
 *
 * @author Bryan Daniel
 */
public class GameContext {

    /**
     * The game context
     */
    private static GameContext gameContext;

    /**
     * The number of alien columns currently in play
     */
    private int alienColumns;

    /**
     * The protagonist of the game
     */
    private Spaceship spaceship;

    /**
     * The list of aliens
     */
    private ArrayList<Alien> alienList;

    /**
     * The map to keep track of living aliens
     */
    private Map<Alien, Boolean> livingAliens;

    /**
     * The queue to hold images for multiple explosion animations
     */
    private Queue<Queue<GifFrame>> explosionAnimationImages;

    /**
     * The audio player for lasers
     */
    private AudioPlayer laserAudioPlayer;

    /**
     * The audio player for explosions
     */
    private AudioPlayer explosionAudioPlayer;

    /**
     * The audio player for a lost game
     */
    private AudioPlayer gameLostAudioPlayer;

    /**
     * The audio player for victory
     */
    private AudioPlayer victoryAudioPlayer;

    /**
     * The audio player for the fight intro
     */
    private AudioPlayer buildUpAudioPlayer;

    /**
     * The audio player for the background sound
     */
    private AudioPlayer backgroundAudioPlayer;

    /**
     * Indicates whether or not the game is initialized
     */
    private boolean gameInitialized = false;

    /**
     * Indicates whether or not the game is still running
     */
    private boolean gameRunning = false;

    /**
     * The saved player state
     */
    private PlayerState playerState;

    /**
     * The game time elapsed in seconds
     */
    private long gameTimeElapsed;

    /**
     * Private constructor.
     */
    private GameContext() {
    }

    /**
     * Returns the singleton instance of gameContext.
     *
     * @return the game context
     */
    public synchronized static GameContext getInstance() {
        if (gameContext == null) {
            gameContext = new GameContext();
        }
        return gameContext;
    }

    /**
     * Cleans up resources and destroys the context.
     */
    public synchronized static void destroy() {
        gameContext.getLaserAudioPlayer().close();
        gameContext.getExplosionAudioPlayer().close();
        gameContext.getGameLostAudioPlayer().close();
        gameContext.getVictoryAudioPlayer().close();
        gameContext.getBuildUpAudioPlayer().close();
        gameContext = null;
    }

    /**
     * Initializes variables the game context.
     *
     * @param playerNameInput the input for the player's name
     * @param readyLatch the latch to signal that the game is ready to begin
     */
    public void initialize(String playerNameInput, CountDownLatch readyLatch) {
        new Thread(() -> {
            GameDataAccess.loadGameContext(playerNameInput, this);
            createSprites(readyLatch);
        }).start();
    }

    /**
     * Saves this context. If the game is terminated the score is also saved.
     *
     * @param savedLatch the latch to signal that the data is saved
     */
    public void save(CountDownLatch savedLatch) {
        new Thread(() -> {
            GameDataAccess.saveGameContext(this);
            if (getPlayerState().isTerminated()) {
                GameDataAccess.saveScore(this);
            }
            savedLatch.countDown();
        }).start();
    }

    /**
     * Instantiates the Sprite objects of the game.
     *
     * @param readyLatch the latch to signal that the game is ready to begin
     */
    private void createSprites(CountDownLatch readyLatch) {
        spaceship = new Spaceship(this);
        alienList = new ArrayList<>();
        livingAliens = new HashMap<>();
        explosionAnimationImages = new LinkedList<>();
        laserAudioPlayer = new LaserAudioPlayer();
        explosionAudioPlayer = new ExplosionAudioPlayer();
        gameLostAudioPlayer = new GameLostAudioPlayer();
        victoryAudioPlayer = new VictoryAudioPlayer();
        buildUpAudioPlayer = new BuildUpAudioPlayer();
        backgroundAudioPlayer = new BackgroundAudioPlayer();
        AlienWave alienWave = gameContext.getPlayerState().getAlienWave();
        alienColumns = alienWave.getNumberOfColumns();
        for (int i = 0; i < alienWave.getNumberOfRows(); i++) {
            for (int j = 0; j < alienColumns; j++) {
                Alien alien = new Alien(Alien.ALIEN_INIT_X + 50 * j, Alien.ALIEN_INIT_Y + 50 * i, this);
                alienList.add(alien);
            }
        }
        for (Alien alien : alienList) {
            livingAliens.put(alien, Boolean.TRUE);
        }
        readyLatch.countDown();
        gameInitialized = true;
    }

    /**
     * Gets the value of alienColumns.
     *
     * @return the number of alien columns
     */
    public int getAlienColumns() {
        return alienColumns;
    }

    /**
     * Gets the value of spaceship.
     *
     * @return the spaceship
     */
    public Spaceship getSpaceship() {
        return spaceship;
    }

    /**
     * Gets the value of gameInitialized.
     *
     * @return the flag indicating whether or not the game is ready
     */
    public boolean isGameInitialized() {
        return gameInitialized;
    }

    /**
     * Gets the value of gameRunning.
     *
     * @return the flag for game running
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Sets the value of gameRunning.
     *
     * @param gameRunning the value of gameRunning to set
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * Gets the value of alienList.
     *
     * @return the list of aliens
     */
    public ArrayList<Alien> getAlienList() {
        return alienList;
    }

    /**
     * Gets the value of livingAliens.
     *
     * @return the map of living aliens
     */
    public Map<Alien, Boolean> getLivingAliens() {
        return livingAliens;
    }

    /**
     * Gets the value of explosionAnimationImages.
     *
     * @return the queue of explosion animation images
     */
    public Queue<Queue<GifFrame>> getExplosionAnimationImages() {
        return explosionAnimationImages;
    }

    /**
     * Gets the value of laserAudioPlayer.
     *
     * @return the laser audio player
     */
    public AudioPlayer getLaserAudioPlayer() {
        return laserAudioPlayer;
    }

    /**
     * Gets the value of explosionAudioPlayer.
     *
     * @return the explosion audio player
     */
    public AudioPlayer getExplosionAudioPlayer() {
        return explosionAudioPlayer;
    }

    /**
     * Gets the value of gameLostAudioPlayer.
     *
     * @return the game-lost audio player
     */
    public AudioPlayer getGameLostAudioPlayer() {
        return gameLostAudioPlayer;
    }

    /**
     * Gets the value of victoryAudioPlayer.
     *
     * @return the victory audio player
     */
    public AudioPlayer getVictoryAudioPlayer() {
        return victoryAudioPlayer;
    }

    /**
     * Gets the value of buildUpAudioPlayer.
     *
     * @return the build-up audio player
     */
    public AudioPlayer getBuildUpAudioPlayer() {
        return buildUpAudioPlayer;
    }

    /**
     * Gets the value of backgroundAudioPlayer.
     *
     * @return the background audio player
     */
    public AudioPlayer getBackgroundAudioPlayer() {
        return backgroundAudioPlayer;
    }

    /**
     * Gets the value of playerState.
     *
     * @return the player state
     */
    public PlayerState getPlayerState() {
        return playerState;
    }

    /**
     * Sets the value of playerState.
     *
     * @param playerState the new player state to set
     */
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * Gets the value of gameTimeElapsed.
     *
     * @return the time elapsed for the game
     */
    public long getGameTimeElapsed() {
        return gameTimeElapsed;
    }

    /**
     * Sets the value of gameTimeElapsed.
     *
     * @param gameTimeElapsed the new gameTimeElapsed to set
     */
    public void setGameTimeElapsed(long gameTimeElapsed) {
        this.gameTimeElapsed = gameTimeElapsed;
    }
}
