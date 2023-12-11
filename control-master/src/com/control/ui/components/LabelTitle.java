package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class LabelTitle extends JLabel
{
	private static final long serialVersionUID = -4907398432470574744L;

	public LabelTitle()
	{
		super();
	}

	public LabelTitle(String text)
	{
		super(text);
	}

	@Override
	public void updateUI()
	{
		super.updateUI();

		setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));
		setFont(UIManager.getFont("TextField.font").deriveFont(22.0f));
		setForeground(Color.decode("#000000"));
		setBackground(Color.decode("#ffffff"));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setOpaque(true);
	}
}
