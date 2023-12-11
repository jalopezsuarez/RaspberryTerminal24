package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ScrollPane extends JScrollPane
{
	private static final long serialVersionUID = 4273884386588761636L;

	public ScrollPane(Component view)
	{
		super(view);

		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JScrollBar scrollBar = this.getVerticalScrollBar();
		scrollBar.setUI(new ScrollbarUI());
		this.setBorder(null);
		this.setBackground(Color.decode("#f1f1f1"));
	}

}
