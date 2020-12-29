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
package com.daniel.alienintruders.sound;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import org.apache.logging.log4j.LogManager;

/**
 * Plays sounds in the game.
 *
 * @author Bryan Daniel
 */
public abstract class AudioPlayer {

    /**
     * The audio input stream
     */
    protected AudioInputStream audioInputStream;

    /**
     * The clip
     */
    protected Clip clip;

    /**
     * Initializes the audio player.
     */
    protected abstract void initialize();

    /**
     * Plays the sound.
     */
    public void playSound() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            clip.start();
        }
    }

    /**
     * Stops playing the sound.
     */
    public void stopSound() {
        if (clip != null) {
            clip.stop();
        }
    }

    /**
     * Cleans up resources.
     */
    public void close() {
        if (clip != null) {
            clip.close();
        }
        if (audioInputStream != null) {
            try {
                audioInputStream.close();
            } catch (IOException ex) {
                LogManager.getLogger(AudioPlayer.class).error("IOException occurred in close().", ex);
            }
        }
    }
}
