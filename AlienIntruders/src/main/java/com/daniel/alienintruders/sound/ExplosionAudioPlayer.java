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
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Responsible for playing the explosion sounds.
 *
 * @author Bryan Daniel
 */
public final class ExplosionAudioPlayer extends AudioPlayer {

    /**
     * The logger for this class
     */
    private final Logger logger;

    /**
     * Initializes the audio player.
     */
    public ExplosionAudioPlayer() {
        super();
        logger = LogManager.getLogger(ExplosionAudioPlayer.class);
        initialize();
    }

    @Override
    protected void initialize() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(SoundFactory.getAudioInput(SoundType.EXPLOSION));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(dataLineInfo);
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logger.error("Exception occurred in ExplosionAudioPlayer.initialize().", ex);
        }
    }
}
