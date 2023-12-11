package com.control.ui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Panel extends JPanel
{
	private static final long serialVersionUID = -1948845692639116654L;

	@Override
	public void updateUI()
	{
		super.updateUI();
		
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setFont(UIManager.getFont("TextField.font").deriveFont(14f));
		setForeground(Color.decode("#000000"));
		setBackground(Color.decode("#ffffff"));
	}

	@Override
	public Dimension getPreferredSize()
	{
		int h = super.getPreferredSize().height;
		int w = getParent().getSize().width;
		return new Dimension(w, h);
	}
}
