package com.control.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

public class SwitchField extends JToggleButton
{
	private static final long serialVersionUID = -4248103043622540202L;

	private String enabled = "Activado";
	private String disabled = "Desactivado";

	public SwitchField(boolean selected)
	{
		this.initialize(selected);
	}

	public SwitchField(boolean selected, String enabled, String disabled)
	{
		this.enabled = enabled;
		this.disabled = disabled;

		this.initialize(selected);
	}

	private void initialize(boolean selected)
	{
		ItemListener itemListener = new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				JToggleButton toggle = (JToggleButton) e.getSource();
				toggle.setContentAreaFilled(false);
				toggle.setBorderPainted(false);
				toggle.setFocusPainted(false);
				toggle.setOpaque(true);

				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					toggle.setText(enabled);

					toggle.setFont(UIManager.getFont("Label.font").deriveFont(13.0f));
					toggle.setForeground(Color.decode("#ffffff"));
					toggle.setBackground(Color.decode("#0079da"));
					toggle.setAlignmentX(Component.LEFT_ALIGNMENT);
					toggle.setOpaque(true);
				}
				else
				{
					toggle.setText(disabled);

					toggle.setFont(UIManager.getFont("Label.font").deriveFont(13.0f));
					toggle.setForeground(Color.decode("#ffffff"));
					toggle.setBackground(Color.decode("#393939"));
					toggle.setAlignmentX(Component.LEFT_ALIGNMENT);
					toggle.setOpaque(true);
				}
			}
		};
		addItemListener(itemListener);

		setSelected(!selected);
		doClick();
	}
}
