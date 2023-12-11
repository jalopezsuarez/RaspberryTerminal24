package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class LabelText extends JTextArea
{
	private static final long serialVersionUID = -5327785119832661908L;

	public LabelText(String text)
	{
		super(text);
	}

	@Override
	public void updateUI()
	{
		super.updateUI();

		setFont(UIManager.getFont("TextField.font").deriveFont(13.0f));
		setForeground(Color.decode("#0b0b0b"));
		setBackground(Color.decode("#ffffff"));
		setOpaque(false);

		setAlignmentX(Component.LEFT_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);

		setWrapStyleWord(true);
		setLineWrap(true);
		setEditable(false);
		setFocusable(false);

		setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));
	}
}
