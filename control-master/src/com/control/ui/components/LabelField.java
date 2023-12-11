package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class LabelField extends JLabel
{
	private static final long serialVersionUID = 1614374200505453947L;

	public LabelField(String text)
	{
		super(text);
	}

	@Override
	public void updateUI()
	{
		super.updateUI();

		setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));

		setFont(UIManager.getFont("TextField.font").deriveFont(13.0f));
		setForeground(Color.decode("#000000"));
		setBackground(Color.decode("#ffffff"));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setOpaque(true);
	}
}
