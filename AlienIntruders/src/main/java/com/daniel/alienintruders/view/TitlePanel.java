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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The title panel contains the name of the program.
 *
 * @author Bryan Daniel
 */
public class TitlePanel extends JPanel {

    /**
     * Constants
     */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor for the title panel, no parameters
     */
    TitlePanel() {
        this.initialize();
    }

    /**
     * Sets variables for the title panel.
     */
    private void initialize() {
        JLabel titleLabel = new JLabel(GameMainFrame.GAME_TITLE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.CENTER);
    }

    /**
     * This overrides the paintComponent method to display a gradient color
     * background.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(240, 240, 240);
        Color color2 = color1.darker();
        Color[] colors = {color2, color1, color1, color2};
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, 50);
        float[] fractions = {0.0f, 0.4f, 0.6f, 1.0f};
        LinearGradientPaint gp = new LinearGradientPaint(start, end, fractions,
                colors);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
