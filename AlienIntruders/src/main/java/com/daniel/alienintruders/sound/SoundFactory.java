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

import java.io.BufferedInputStream;

/**
 * Creates input streams for audio used in the game.
 *
 * @author Bryan Daniel
 */
public class SoundFactory {

    /**
     * The wav file for the laser shot sound
     */
    public static final String LASER_SHOT_URL = "sounds/shoot.wav";

    /**
     * The wav file for the explosion sound
     */
    public static final String EXPLOSION_URL = "sounds/explosion.wav";

    /**
     * The wav file for the fight intro build up
     */
    public static final String BUILD_UP_URL = "sounds/fight-build-up.wav";

    /**
     * The wav file for the game lost sound
     */
    public static final String GAME_LOST_URL = "sounds/player-losing.wav";

    /**
     * The wav file for the victory sound
     */
    public static final String VICTORY_URL = "sounds/fanfare.wav";

    /**
     * The wav file for the background sound
     */
    public static final String BACKGROUND_URL = "sounds/arcade.wav";

    /**
     * Private constructor - not instantiated
     */
    private SoundFactory() {
    }

    /**
     * Retrieves the sound input stream for the specified type.
     *
     * @param soundType the sound type
     * @return the sound input
     */
    public static BufferedInputStream getAudioInput(SoundType soundType) {
        BufferedInputStream inputStream = null;
        switch (soundType) {
            case LASER:
                inputStream = new BufferedInputStream(SoundFactory.class.getClassLoader().getResourceAsStream(
                        LASER_SHOT_URL));
                break;
            case EXPLOSION:
                inputStream = new BufferedInputStream(SoundFactory.class.getClassLoader().getResourceAsStream(
                        EXPLOSION_URL));
                break;
            case BUILD_UP:
                inputStream = new BufferedInputStream(SoundFactory.class.getClassLoader().getResourceAsStream(
                        BUILD_UP_URL));
                break;
            case GAME_LOST:
                inputStream = new BufferedInputStream(SoundFactory.class.getClassLoader().getResourceAsStream(
                        GAME_LOST_URL));
                break;
            case VICTORY:
                inputStream = new BufferedInputStream(SoundFactory.class.getClassLoader().getResourceAsStream(
                        VICTORY_URL));
                break;
            case BACKGROUND:
                inputStream = new BufferedInputStream(SoundFactory.class.getClassLoader().getResourceAsStream(
                        BACKGROUND_URL));
                break;
            default:
                break;
        }
        return inputStream;
    }
}
