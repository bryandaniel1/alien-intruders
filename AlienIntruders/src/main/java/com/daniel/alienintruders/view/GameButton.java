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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 * Displays and handles events for a button.
 *
 * @author Bryan Daniel
 */
public class GameButton extends JButton {

    /**
     * The button width
     */
    public static final int BUTTON_WIDTH = 200;

    /**
     * The button height
     */
    public static final int BUTTON_HEIGHT = 100;

    /**
     * Sets the values for instance variables and initializes this button.
     *
     * @param buttonText the button text
     */
    public GameButton(String buttonText) {
        super(buttonText);
        this.initialize();
    }

    /**
     * Initializes the button.
     */
    private void initialize() {
        setFont(new Font("Arial", Font.BOLD, 20));
        setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.BLACK);
        addMouseListener(new MouseAdapter() {
            Color previousColor = GameButton.this.getForeground();

            @Override
            public void mouseEntered(MouseEvent me) {
                previousColor = getForeground();
                setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setForeground(previousColor);
            }
        });
    }
}
