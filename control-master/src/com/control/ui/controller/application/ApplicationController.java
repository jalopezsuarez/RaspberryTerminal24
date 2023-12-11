package com.control.ui.controller.application;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.control.ui.application.NavigationController;
import com.control.ui.application.ViewController;
import com.control.ui.components.ButtonField;
import com.control.ui.components.Frame;
import com.control.ui.components.LabelText;
import com.control.ui.components.Panel;
import com.control.ui.components.ScrollPane;
import com.control.ui.components.layout.GridHelper;
import com.control.ui.components.menu.MenuList;
import com.control.ui.controller.ExtensionController;
import com.control.ui.controller.GeneralController;
import com.control.ui.controller.NetworkController;
import com.control.ui.controller.RemoteController;
import com.control.ui.controller.WirelessController;
import com.control.ui.dialog.BootDialog;
import java.util.Vector;

public class ApplicationController extends Frame
{
    private static final long serialVersionUID = -1107138774557164094L;

    // =======================================================
    private static RemoteController remoteController;
    private static NetworkController networkController;
    private static WirelessController wirelessController;
    private static ExtensionController extensionController;
    private static GeneralController generalController;

    private NavigationController navigationController;

    // =======================================================
    private ButtonField buttonBoot = null;
    private MenuList<?> menu = null;

    // ~ Methods
    // =======================================================
    public ApplicationController()
    {
        this.setTitle("Panel de Control");

        // -------------------------------------------------------
        Panel contentPane = new Panel();
        contentPane.setLayout(new CardLayout());

        Vector<ViewController> items = new Vector<ViewController>();
        {
            navigationController = new NavigationController(this, contentPane);

            remoteController = (RemoteController) navigationController.initViewController(RemoteController.class.getName(), "Escritorios remotos");
            networkController = (NetworkController) navigationController.initViewController(NetworkController.class.getName(), "Ajustes de Red");
            wirelessController = (WirelessController) navigationController.initViewController(WirelessController.class.getName(), "Redes Wi-Fi");
            extensionController = (ExtensionController) navigationController.initViewController(ExtensionController.class.getName(), "Extensiones");
            generalController = (GeneralController) navigationController.initViewController(GeneralController.class.getName(), "General");

            items.add(remoteController);
            items.add(networkController);
            items.add(wirelessController);
            items.add(extensionController);
            items.add(generalController);
        }

        menu = new MenuList<Object>(items);
        menu.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    navigationController.pushViewController((ViewController) menu.getSelectedValue());
                }
            }
        });

        // -------------------------------------------------------
        ScrollPane menuPane = new ScrollPane(menu);

        // -------------------------------------------------------
        Panel applicationPane = new Panel();
        applicationPane.setBackground(Color.decode("#e7e7e7"));
        GridHelper applicationLayout = new GridHelper(applicationPane);
        {
            LabelText labelText = new LabelText("Algunos cambios en la configuracion pueden requerir que se reinice el terminal para que se apliquen. Recuerda reiniciar el terminal una vez terminada la configuracion. ");
            labelText.setFont(UIManager.getFont("TextField.font").deriveFont(12.0f));
            labelText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            applicationLayout.constrains().weightx = 1.0;
            applicationLayout.constrains().weighty = 0.0;
            applicationLayout.constrains().fill = GridBagConstraints.BOTH;
            applicationLayout.constrains().anchor = GridBagConstraints.CENTER;

            applicationLayout.add(labelText, 0, 0);
        }
        {
            buttonBoot = new ButtonField("Reiniciar ahora");

            applicationLayout.constrains().weightx = 0.0;
            applicationLayout.constrains().weighty = 1.0;
            applicationLayout.constrains().fill = GridBagConstraints.HORIZONTAL;
            applicationLayout.constrains().anchor = GridBagConstraints.CENTER;
            applicationLayout.constrains().insets = new Insets(10, 10, 10, 10);

            applicationLayout.add(buttonBoot, 1, 0);
        }

        this.setLayout(new BorderLayout());

        this.add(applicationPane, BorderLayout.NORTH);
        this.add(menuPane, BorderLayout.WEST);
        this.add(contentPane, BorderLayout.CENTER);

        // =======================================================
        navigationController.pushViewController(remoteController);

        // =======================================================
        buttonBoot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    new BootDialog();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // =======================================================
        {
            KeyStroke stroke = KeyStroke.getKeyStroke("UP");
            Action action = new AbstractAction()
            {
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e)
                {
                    menuKeyEventUp();
                }
            };
            getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, "UP");
            getRootPane().getActionMap().put("UP", action);
        }
        {
            KeyStroke stroke = KeyStroke.getKeyStroke("DOWN");
            Action action = new AbstractAction()
            {
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e)
                {
                    menuKeyEventDown();
                }
            };
            getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, "DOWN");
            getRootPane().getActionMap().put("DOWN", action);
        }
        {
            KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
            Action action = new AbstractAction()
            {
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0);
                }
            };
            getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, "ESCAPE");
            getRootPane().getActionMap().put("ESCAPE", action);
        }
    }

    private void menuKeyEventUp()
    {
        int selected = menu.getSelectedIndex();
        selected--;
        menu.setSelectedIndex(Math.max(selected, 0));
    }

    private void menuKeyEventDown()
    {
        int selected = menu.getSelectedIndex();
        selected++;
        menu.setSelectedIndex(Math.min(selected, menu.getModel().getSize()));
    }
}
