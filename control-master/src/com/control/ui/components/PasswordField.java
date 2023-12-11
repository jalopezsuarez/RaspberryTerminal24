package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class PasswordField extends JPasswordField
{
	private static final long serialVersionUID = -5649732527164158429L;

	@Override
	public void updateUI()
	{
		super.updateUI();

		setFont(UIManager.getFont("Label.font").deriveFont(12.0f));
		setForeground(Color.decode("#0b0b0b"));
		setBackground(Color.decode("#ffffff"));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setOpaque(true);

		Border lines = BorderFactory.createLineBorder(Color.decode("#bbbbbb"), 3);
		Border empty = BorderFactory.createEmptyBorder(5, 6, 5, 6);
		setBorder(new CompoundBorder(lines, empty));
	}
}
