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
package com.daniel.alienintruders.image;

import javax.swing.ImageIcon;

/**
 * Creates ImageIcon objects for all image types.
 *
 * @author Bryan Daniel
 */
public class ImageFactory {

    /**
     * The URL for the alien image
     */
    public static final String ALIEN_URL = "images/SpaceInvadersAlien.png";

    /**
     * The URL for the alien missile image
     */
    public static final String ALIEN_MISSILE_URL = "images/SpaceInvadersAlienBomb.png";

    /**
     * The URL for the alien mothership image
     */
    public static final String MOTHERSHIP_URL = "images/SpaceInvadersMothership.png";

    /**
     * The URL for the spaceship image
     */
    public static final String SPACESHIP_URL = "images/SpaceInvadersLaserCannon.png";

    /**
     * The URL for the laser image
     */
    public static final String SPACESHIP_LASER_URL = "images/SpaceInvadersLaser.png";

    /**
     * The URL for the sky image
     */
    public static final String SKY_URL = "images/stars.jpg";

    /**
     * The URL for the explosion image
     */
    public static final String EXPLOSION_URL = "images/Explosion.gif";

    /**
     * Private constructor - not instantiated
     */
    private ImageFactory() {
    }

    /**
     * Creates and returns an ImageIcon for the specified image type.
     *
     * @param imageType the image type
     * @return the ImageIcon object
     */
    public static ImageIcon createImage(ImageType imageType) {
        ImageIcon imageIcon = null;
        switch (imageType) {
            case ALIEN:
                imageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
                        .getResource(ALIEN_URL));
                break;
            case ALIEN_MISSILE:
                imageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
                        .getResource(ALIEN_MISSILE_URL));
                break;
            case MOTHERSHIP:
                imageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
                        .getResource(MOTHERSHIP_URL));
                break;
            case SPACESHIP:
                imageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
                        .getResource(SPACESHIP_URL));
                break;
            case SPACESHIP_LASER:
                imageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
                        .getResource(SPACESHIP_LASER_URL));
                break;
            case SKY:
                imageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
                        .getResource(SKY_URL));
                break;
            case EXPLOSION:
                imageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
                        .getResource(EXPLOSION_URL));
                break;
            default:
                break;
        }
        return imageIcon;
    }
}
