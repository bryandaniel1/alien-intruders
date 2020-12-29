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
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Applies the desired colors to the table.
 *
 * @author Bryan Daniel
 */
public class ScoreboardCellRenderer implements TableCellRenderer {

    /**
     * The color for even rows
     */
    public static final Color EVEN_ROW_COLOR = Color.BLACK;

    /**
     * The color for odd rows
     */
    public static final Color ODD_ROW_COLOR = Color.DARK_GRAY;

    /**
     * The color for text
     */
    public static final Color TEXT_COLOR = Color.YELLOW;

    /**
     * The default renderer
     */
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);

        if (row % 2 == 0) {
            rendererComponent.setBackground(EVEN_ROW_COLOR);
        } else {
            rendererComponent.setBackground(ODD_ROW_COLOR);
        }
        rendererComponent.setForeground(TEXT_COLOR);

        return rendererComponent;
    }
}
