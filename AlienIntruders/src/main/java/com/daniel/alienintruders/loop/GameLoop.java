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
package com.daniel.alienintruders.loop;

import com.daniel.alienintruders.context.GameContext;
import com.daniel.alienintruders.sprite.Alien;
import com.daniel.alienintruders.sprite.AlienMissile;
import com.daniel.alienintruders.sprite.Laser;
import com.daniel.alienintruders.sprite.Spaceship;
import com.daniel.alienintruders.view.GamePanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ActionListener implementation for the game loop.
 *
 * @author Bryan Daniel
 */
public class GameLoop implements ActionListener {

    /**
     * The game panel
     */
    private final GamePanel gamePanel;

    /**
     * The spaceship
     */
    private final GameContext gameContext;

    /**
     * The logger for this class
     */
    private final Logger logger;

    /**
     * Sets the value for the game panel.
     *
     * @param gamePanel the game panel
     * @param gameContext the game context
     */
    public GameLoop(GamePanel gamePanel, GameContext gameContext) {
        this.gamePanel = gamePanel;
        this.gameContext = gameContext;
        logger = LogManager.getLogger(GameLoop.class);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doLoop();
    }

    /**
     * Executes updates and repaints the panel.
     */
    public void doLoop() {
        update();
        gamePanel.repaint();
    }

    /**
     * Performs updates to game objects.
     */
    private void update() {
        if (gameContext != null && gameContext.isGameInitialized()) {
            Spaceship spaceship = gameContext.getSpaceship();
            if (spaceship.isDead() && !gamePanel.isOptionShowing()) {
                gamePanel.setOptionShowing(true);
                gamePanel.showOptionDialog(GamePanel.DEAD_DIALOG_MESSAGE, GamePanel.DEAD_DIALOG_TITLE);
            } else {
                spaceship.move();
            }
            Laser laser = spaceship.getLaser();
            if (laser != null) {
                laser.move();
                for (Alien alien : gameContext.getAlienList()) {
                    if (!alien.isDead() && !laser.isDead() && alien.isColliding(laser)) {
                        alien.setDead(true);
                        laser.setDead(true);
                        gameContext.getLivingAliens().put(alien, Boolean.FALSE);
                        new Thread(() -> {
                            gameContext.getExplosionAudioPlayer().playSound();
                        }).start();
                        int alienPoints = alien.getPoints();
                        int currentScore = gameContext.getPlayerState().getScore();
                        gameContext.getPlayerState().setScore(currentScore + alienPoints);
                        break;
                    }
                }
            }

            boolean kamikaziMode = true;
            boolean allAliensDead = true;
            boolean noMissilesFlying = true;
            for (Alien alien : gameContext.getAlienList()) {
                alien.move();
                if (!alien.isDead()) {
                    alien.fireMissile();
                    allAliensDead = false;
                }
                AlienMissile firedMissile = alien.getFiredMissile();
                if (firedMissile != null) {
                    firedMissile.move();
                    if (!spaceship.isDead() && !firedMissile.isDead() && spaceship.isColliding(firedMissile)) {
                        spaceship.setDead(true);
                        spaceship.setExplode(true);
                        firedMissile.setDead(true);
                        new Thread(() -> {
                            gameContext.getExplosionAudioPlayer().playSound();
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            gameContext.getBackgroundAudioPlayer().stopSound();
                            gameContext.getBackgroundAudioPlayer().close();
                            gameContext.getGameLostAudioPlayer().playSound();
                        }).start();
                    }
                    if (!firedMissile.isDead()) {
                        noMissilesFlying = false;
                    }
                }
                if (alien.isArmed() || alien.isKamikaziMode()) {
                    kamikaziMode = false;
                }
            }
            for (Alien alien : gameContext.getAlienList()) {
                if (kamikaziMode) {
                    alien.setKamikaziMode(true);
                }
                if (!spaceship.isDead() && !alien.isDead() && alien.isColliding(spaceship)) {
                    spaceship.setDead(true);
                    spaceship.setExplode(true);
                    alien.setDead(true);
                    new Thread(() -> {
                        gameContext.getExplosionAudioPlayer().playSound();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        gameContext.getBackgroundAudioPlayer().stopSound();
                        gameContext.getBackgroundAudioPlayer().close();
                        gameContext.getGameLostAudioPlayer().playSound();
                    }).start();
                }
            }
            if (allAliensDead && !spaceship.isDead() && noMissilesFlying && !gamePanel.isVictoryShowing()) {
                gamePanel.setVictoryShowing(true);
                gamePanel.showVictory();
            }
        }
    }
}
