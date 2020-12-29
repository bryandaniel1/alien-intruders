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

import com.daniel.alienintruders.credits.Credit;
import com.daniel.alienintruders.credits.CreditsIO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLEditorKit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Displays credits for the game.
 *
 * @author Bryan Daniel
 */
public class CreditsPanel extends JPanel implements ActionListener {

    /**
     * The command to close the scoreboard
     */
    public static final String CLOSE_COMMAND = "Close";

    /**
     * The game development type credit
     */
    public static final String GAME_DEV_CREDIT = "Game Development";

    /**
     * The sounds type credit
     */
    public static final String SOUND_CREDIT = "Sounds";

    /**
     * The images type credit
     */
    public static final String IMAGE_CREDIT = "Images";

    /**
     * The preferred width of the JScrollPane
     */
    public static final int PREFERRED_WIDTH = 700;

    /**
     * The preferred height of the JScrollPane
     */
    public static final int PREFERRED_HEIGHT = 400;

    /**
     * The main frame
     */
    private final GameMainFrame gameMainFrame;

    /**
     * The logger for this class.
     */
    private final Logger logger;

    /**
     * Sets the value for the main frame.
     *
     * @param gameMainFrame the main frame
     */
    public CreditsPanel(GameMainFrame gameMainFrame) {
        this.gameMainFrame = gameMainFrame;
        logger = LogManager.getLogger(CreditsPanel.class);
        initialize();
    }

    /**
     * Initializes this scoreboard.
     */
    private void initialize() {
        setBounds(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        setBackground(GameMainFrame.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        JPanel creditsPanel = new JPanel(new GridBagLayout());
        creditsPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        JEditorPane creditsPane = new JEditorPane();
        creditsPane.setEditable(false);
        creditsPane.setEditorKit(new HTMLEditorKit());
        creditsPane.setText(writeHtmlText());
        creditsPane.addHyperlinkListener((hyperlinkEvent) -> {
            if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(hyperlinkEvent.getURL().toURI());
                } catch (IOException | URISyntaxException ex) {
                    logger.error("An exception occurred while clicking a hyperlink in CreditsPanel.", ex);
                }
            }
        });
        creditsPane.setPreferredSize(new Dimension(PREFERRED_WIDTH,
                PREFERRED_HEIGHT));

        JScrollPane scrollPane = new JScrollPane(creditsPane,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(10, 20, 10, 20);  //top, left, bottom, right
        creditsPanel.add(scrollPane, constraints);

        GameButton closeButton = new GameButton(CLOSE_COMMAND);
        closeButton.addActionListener(this);
        JPanel closeButtonPanel = new JPanel();
        closeButtonPanel.setBackground(GameMainFrame.BACKGROUND_COLOR);
        closeButtonPanel.add(closeButton);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(10, 20, 65, 20);  //top, left, bottom, right
        constraints.anchor = GridBagConstraints.SOUTH;
        creditsPanel.add(closeButtonPanel, constraints);

        TitledBorder scoreboardBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Credits",
                TitledBorder.CENTER, TitledBorder.TOP);
        scoreboardBorder.setTitleFont(new Font("Arial", Font.BOLD, 20));
        scoreboardBorder.setTitleColor(Color.BLACK);
        creditsPanel.setBorder(scoreboardBorder);

        add(new TitlePanel(), BorderLayout.NORTH);
        add(creditsPanel, BorderLayout.CENTER);

        /*
         * Need to wait a bit to set scrolls to beginning
         */
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMinimum());
            horizontalScrollBar.setValue(horizontalScrollBar.getMinimum());
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonString = e.getActionCommand();
        switch (buttonString) {
            case CLOSE_COMMAND:
                gameMainFrame.closeCredits();
                break;
            default:
                break;
        }
    }

    /**
     * Creates and returns the HTML string for the credits pane.
     *
     * @return the HTML string
     */
    private String writeHtmlText() {
        StringBuilder labelText = new StringBuilder();
        List<Credit> credits = CreditsIO.findCredits();
        labelText.append("<html>");
        labelText.append("<h2>");
        labelText.append(GAME_DEV_CREDIT);
        labelText.append(":</h2>");
        for (Credit credit : credits) {
            if (GAME_DEV_CREDIT.equals(credit.getType())) {
                labelText.append("<p>");
                labelText.append(credit.getWork());
                labelText.append(" by ");
                labelText.append(credit.getBy());
                labelText.append("</p>");
                labelText.append("<br/><br/>");
            }
        }
        labelText.append("<h2>");
        labelText.append(SOUND_CREDIT);
        labelText.append(":</h2>");
        for (Credit credit : credits) {
            if (SOUND_CREDIT.equals(credit.getType())) {
                labelText.append("<p>");
                labelText.append(credit.getWork());
                labelText.append(" by ");
                labelText.append(credit.getBy());
                labelText.append("<br/><a href=\"");
                labelText.append(credit.getWhere());
                labelText.append("\">");
                labelText.append(credit.getWhere());
                labelText.append("</a>&nbsp;");
                labelText.append(credit.getLicenseInfo());
                labelText.append("</p>");
                labelText.append("<br/><br/>");
            }
        }
        labelText.append("<h2>");
        labelText.append(IMAGE_CREDIT);
        labelText.append(":</h2>");
        for (Credit credit : credits) {
            if (IMAGE_CREDIT.equals(credit.getType())) {
                labelText.append("<p>");
                labelText.append(credit.getWork());
                labelText.append(" by ");
                labelText.append(credit.getBy());
                labelText.append("<br/><a href=\"");
                labelText.append(credit.getWhere());
                labelText.append("\">");
                labelText.append(credit.getWhere());
                labelText.append("</a>&nbsp;");
                labelText.append(credit.getLicenseInfo());
                labelText.append("</p>");
                labelText.append("<br/><br/>");
            }
        }
        labelText.append("</html>");
        return labelText.toString();
    }
}
