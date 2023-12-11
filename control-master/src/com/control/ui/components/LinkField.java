package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class LinkField extends JButton
{
	private static final long serialVersionUID = 6212351455109284036L;

	public LinkField(String text)
	{
		super(text);
	}

	@Override
	public void updateUI()
	{
		super.updateUI();

		setFont(UIManager.getFont("Label.font").deriveFont(13.0f));
		setForeground(Color.decode("#297acd"));
		setBackground(Color.decode("#ffffff"));
		setOpaque(false);

		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setHorizontalTextPosition(SwingConstants.LEFT);
		setHorizontalAlignment(SwingConstants.LEFT);
		setVerticalTextPosition(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);

		setFocusPainted(false);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
}
