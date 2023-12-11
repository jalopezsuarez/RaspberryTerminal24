package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ButtonField extends JButton
{
	private static final long serialVersionUID = -6507491737343039770L;

	public ButtonField(String text)
	{
		super(text);

		setFont(UIManager.getFont("Label.font").deriveFont(13.0f));
		setForeground(Color.decode("#0b0b0b"));
		setBackground(Color.decode("#cccccd"));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setOpaque(true);

		Border line = new LineBorder(Color.decode("#cccccd"));
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		setBorder(compound);
		
		setFocusPainted(false);
	}

	@Override
	public void updateUI()
	{
		super.updateUI();
	}
}
