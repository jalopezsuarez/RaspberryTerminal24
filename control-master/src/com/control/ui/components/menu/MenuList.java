package com.control.ui.components.menu;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class MenuList<E> extends JList<Object>
{
    private static final long serialVersionUID = -5067915857707954401L;

    public MenuList(final Vector<? extends E> listData)
    {
        super(listData);

        this.setCellRenderer(new MenuListCellRenderer());

        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setFixedCellHeight(40);

        UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());

        this.setFont(UIManager.getFont("TextField.font").deriveFont(14f));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setOpaque(true);

        this.setSelectedIndex(0);
        this.setFocusable(false);

        this.setSelectionBackground(Color.decode("#8fc0e9"));
        this.setSelectionForeground(Color.decode("#141a20"));

        this.setBackground(Color.decode("#f2f2f2"));
        this.setForeground(Color.decode("#141a20"));

        this.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        this.setVisibleRowCount(0);
    }

    @Override
    public void updateUI()
    {
        super.updateUI();
    }

}
