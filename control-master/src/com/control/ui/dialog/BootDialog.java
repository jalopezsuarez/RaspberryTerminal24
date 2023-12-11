package com.control.ui.dialog;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.control.service.SystemService;
import com.control.ui.components.ButtonField;
import com.control.ui.components.Dialog;
import com.control.ui.components.LabelText;
import com.control.ui.components.LabelTitle;
import com.control.ui.components.Panel;
import com.control.ui.components.layout.GridHelper;

public class BootDialog extends Dialog
{
    private static final long serialVersionUID = -9222880740802138673L;

    private ButtonField buttonReboot = null;
    private ButtonField buttonShutdown = null;

    public BootDialog()
    {
        Panel panel = new Panel();
        GridHelper layout = new GridHelper(panel);
        {
            int gridy = 0;
            {
                LabelTitle labelField = new LabelTitle("Confirmar apagar terminal");

                layout.constrains().weightx = 1.0;
                layout.constrains().gridwidth = 2;
                layout.add(labelField, 0, gridy++);
            }
            {
                LabelText labelField = new LabelText("¿Seguro que quieres apagar o reiniciar el terminal?");

                layout.constrains().weightx = 1.0;
                layout.constrains().gridwidth = 2;
                layout.add(labelField, 0, gridy++);
            }
            {
                buttonReboot = new ButtonField("Reiniciar");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.SOUTHEAST;
                layout.constrains().weightx = 1.0;
                layout.constrains().weighty = 1.0;
                layout.constrains().insets = new Insets(20, 20, 20, 10);
                layout.add(buttonReboot, 0, gridy);
            }
            {
                buttonShutdown = new ButtonField("Apagar");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.SOUTHEAST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 1.0;
                layout.constrains().insets = new Insets(20, 10, 20, 20);
                layout.add(buttonShutdown, 1, gridy++);
            }
        }

        // -------------------------------------------------------
        add(panel);

        // =======================================================
        buttonReboot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    SystemService systemService = new SystemService();
                    systemService.reboot();

                    System.exit(0);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        buttonShutdown.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    SystemService systemService = new SystemService();
                    systemService.shutdown();

                    System.exit(0);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Excepción", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // =======================================================
        {
            KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
            Action action = new AbstractAction()
            {
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e)
                {
                    setVisible(false);
                }
            };
            getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, "ESCAPE");
            getRootPane().getActionMap().put("ESCAPE", action);
        }

        // =======================================================
        pack();
        setSize(420, 180);

        setLocationRelativeTo(null);
        requestFocus();

        setModal(true);
        setVisible(true);
    }

}
