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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays messages to the player.
 *
 * @author Bryan Daniel
 */
public class MessagePanel extends JPanel {

    /**
     * Default constructor.
     */
    public MessagePanel() {
        initialize();
    }

    /**
     * Sets variables for the message panel.
     */
    private void initialize() {
        setBounds(-GamePanel.PANEL_WIDTH, 0, 0, GamePanel.PANEL_HEIGHT);
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    /**
     * Adds a JLabel with the new message to this panel.
     *
     * @param message the new message to display
     */
    public void displayMessage(String message) {
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 40));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setVerticalAlignment(JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        this.removeAll();
        add(messageLabel, BorderLayout.CENTER);
        revalidate();
    }
}
