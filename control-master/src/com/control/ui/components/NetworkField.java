package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NetworkField extends JTextField
{
	private static final long serialVersionUID = -8627628165310407192L;

	public NetworkField()
	{
		super();

		// -------------------------------------------------------

		addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				String prop = evt.getPropertyName();
				if (prop.equals(new String("editable")))
				{
					Object newValue = evt.getNewValue();
					if (newValue != null && newValue instanceof Boolean && ((Boolean) newValue).booleanValue() == false)
					{
						setBackground(Color.decode("#cccccd"));
					}
					else
					{
						setBackground(Color.decode("#ffffff"));
					}
				}
			}
		});
		
		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if (c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE)
				{
					if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '.'))
					{
						e.consume();
					}
				}
			}
		});		
	}

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
