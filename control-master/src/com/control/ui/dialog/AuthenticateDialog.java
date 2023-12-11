package com.control.ui.dialog;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.control.service.AccessService;
import com.control.ui.components.ButtonField;
import com.control.ui.components.Dialog;
import com.control.ui.components.LabelField;
import com.control.ui.components.LabelText;
import com.control.ui.components.LabelTitle;
import com.control.ui.components.Panel;
import com.control.ui.components.PasswordField;
import com.control.ui.components.layout.GridHelper;

public class AuthenticateDialog extends Dialog
{
    private static final long serialVersionUID = -8744561471901720714L;

    private LabelText labelMessage;
    private PasswordField textPassword;
    private ButtonField buttonAuthenticate;
    private ButtonField buttonCancel;

    private boolean authenticate;

    public AuthenticateDialog()
    {
        authenticate = false;

        // =======================================================
        Panel panel = new Panel();
        GridHelper layout = new GridHelper(panel);
        {
            int gridy = 0;
            {
                LabelTitle labelTitle = new LabelTitle("Panel de control");

                layout.constrains().weightx = 1.0;
                layout.constrains().gridwidth = 2;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                labelMessage = new LabelText("Introduce tus credenciales de usuario para continuar.");

                layout.constrains().weightx = 1.0;
                layout.constrains().gridwidth = 2;
                layout.add(labelMessage, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Contraseña");

                layout.constrains().weightx = 1.0;
                layout.constrains().gridwidth = 2;
                layout.add(labelField, 0, gridy++);
            }
            {
                textPassword = new PasswordField();

                layout.constrains().weightx = 1.0;
                layout.constrains().gridwidth = 2;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textPassword, 0, gridy++);
            }
            {
                buttonCancel = new ButtonField("Cancelar");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.SOUTHEAST;
                layout.constrains().weightx = 1.0;
                layout.constrains().weighty = 1.0;
                layout.constrains().insets = new Insets(20, 20, 20, 10);
                layout.add(buttonCancel, 0, gridy);
            }
            {
                buttonAuthenticate = new ButtonField("Aceptar");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.SOUTHEAST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 1.0;
                layout.constrains().insets = new Insets(20, 10, 20, 20);
                layout.add(buttonAuthenticate, 1, gridy++);
            }
        }

        // -------------------------------------------------------
        add(panel);

        // =======================================================
        textPassword.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER)
                {
                    authenticate = false;
                    try
                    {
                        AccessService accessService = new AccessService();
                        String stringPassword = new String(textPassword.getPassword());
                        authenticate = accessService.authenticate(stringPassword);
                    }
                    catch (Exception ex)
                    {
                        authenticate = false;
                    }
                    if (!authenticate)
                    {
                        labelMessage.setText("La contraseña no es valida. Comprueba el bloqueo de mayúsculas e inténtalo de nuevo.");
                        labelMessage.setFont(UIManager.getFont("Label.font").deriveFont(13.0f));
                        labelMessage.setForeground(Color.decode("#de5347"));
                        textPassword.selectAll();
                        textPassword.requestFocus();
                    }
                    else
                    {
                        setVisible(false);
                    }
                }
                else if (c == KeyEvent.VK_ESCAPE)
                {
                    setVisible(false);
                }
            }
        });

        buttonAuthenticate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                authenticate = false;
                try
                {
                    AccessService accessService = new AccessService();
                    String stringPassword = new String(textPassword.getPassword());
                    authenticate = accessService.authenticate(stringPassword);
                }
                catch (Exception ex)
                {
                    authenticate = false;
                }
                if (!authenticate)
                {
                    labelMessage.setText("La contraseña no es valida. Comprueba el bloqueo de mayúsculas e inténtalo de nuevo.");
                    labelMessage.setFont(UIManager.getFont("Label.font").deriveFont(13.0f));
                    textPassword.selectAll();
                    textPassword.requestFocus();
                }
                else
                {
                    setVisible(false);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
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
        setSize(420, 260);

        setLocationRelativeTo(null);
        requestFocus();

        setModal(true);
        setVisible(true);
    }

    public boolean isAuthenticate()
    {
        return authenticate;
    }
}
