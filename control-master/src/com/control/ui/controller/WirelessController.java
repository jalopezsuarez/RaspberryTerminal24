package com.control.ui.controller;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.control.domain.NetworkQuery;
import com.control.service.NetworkService;
import com.control.ui.application.ViewController;
import com.control.ui.components.ButtonField;
import com.control.ui.components.LabelField;
import com.control.ui.components.LabelText;
import com.control.ui.components.LabelTitle;
import com.control.ui.components.LinkField;
import com.control.ui.components.Panel;
import com.control.ui.components.SwitchField;
import com.control.ui.components.NetworkField;
import com.control.ui.components.TextField;
import com.control.ui.components.layout.GridHelper;

public class WirelessController extends ViewController
{
    private static final long serialVersionUID = -6811646952481056707L;

    // =======================================================
    public LinkField linkWireless;

    public NetworkField textAddress;
    public NetworkField textMask;
    public NetworkField textRouter;
    public TextField textDomains;

    public SwitchField switchDHCP;
    public ButtonField buttonApply;

    // =======================================================
    public WirelessController()
    {
        Panel panel = new Panel();
        setViewportView(panel);

        GridHelper layout = new GridHelper(panel);
        {
            int gridy = 0;
            {
                LabelTitle labelTitle = new LabelTitle("Redes Wi-Fi");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                LabelText labelText = new LabelText("Se accedera automaticamente a las redes conocidas. Si no hay ninguna red conocida disponible, debes buscar y seleccionar una manualmente.");

                layout.constrains().weightx = 1.0;
                layout.add(labelText, 0, gridy++);
            }
            {
                linkWireless = new LinkField("Buscar redes Wi-Fi");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(10, 20, 0, 10);
                layout.add(linkWireless, 0, gridy++);
            }
            {
                LabelTitle labelTitle = new LabelTitle("Configuracion Wi-Fi");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Contectar vía DHCP");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                switchDHCP = new SwitchField(true);

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(10, 20, 0, 10);
                layout.add(switchDHCP, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Direccion IPv4");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                textAddress = new NetworkField();

                layout.constrains().weightx = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textAddress, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Mascara de subred");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                textMask = new NetworkField();

                layout.constrains().weightx = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textMask, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Puerta de enlace");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                textRouter = new NetworkField();

                layout.constrains().weightx = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textRouter, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Servidores DNS");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                textDomains = new TextField();

                layout.constrains().weightx = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textDomains, 0, gridy++);
            }
            {
                buttonApply = new ButtonField("Aplicar");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(20, 20, 0, 10);
                layout.add(buttonApply, 0, gridy++);
            }

            // -------------------------------------------------------
            {
                JLabel vertical = new JLabel();

                layout.constrains().weightx = 1.0;
                layout.constrains().weighty = 1.0;
                layout.constrains().insets = new Insets(30, 0, 0, 0);
                layout.add(vertical, 0, gridy++);
            }

            // =======================================================
            // =======================================================
            try
            {
                switchDHCP.setSelected(true);
                textAddress.setEditable(false);
                textMask.setEditable(false);
                textRouter.setEditable(false);
                textDomains.setEditable(false);

                // -------------------------------------------------------
                NetworkService networkService = new NetworkService();
                NetworkQuery networkQuery = networkService.populate();

                // -------------------------------------------------------
                switchDHCP.setSelected(networkQuery.isWirelessDHCP());
                textAddress.setText(networkQuery.getWirelessAddress());
                textMask.setText(networkQuery.getWirelessMask());
                textRouter.setText(networkQuery.getWirelessRouters());
                textDomains.setText(networkQuery.getWirelessDomainNameServers());

                // -------------------------------------------------------
                if (!networkQuery.isWirelessDHCP())
                {
                    textAddress.setEditable(true);
                    textMask.setEditable(true);
                    textRouter.setEditable(true);
                    textDomains.setEditable(true);
                }
            }
            catch (Exception ex)
            {
            }

            // =======================================================
            // =======================================================
            linkWireless.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        NetworkService networkService = new NetworkService();
                        networkService.launchWireless();
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Redes Wi-Fi)", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            // -------------------------------------------------------
            switchDHCP.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        SwitchField switcher = (SwitchField) e.getSource();
                        if (switcher.isSelected())
                        {
                            textAddress.setEditable(false);
                            textMask.setEditable(false);
                            textRouter.setEditable(false);
                            textDomains.setEditable(false);
                        }
                        else
                        {
                            textAddress.setEditable(true);
                            textMask.setEditable(true);
                            textRouter.setEditable(true);
                            textDomains.setEditable(true);
                        }
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Redes Wi-Fi)", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            // -------------------------------------------------------
            buttonApply.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        NetworkService networkService = new NetworkService();

                        NetworkQuery networkQuery = networkService.populate();
                        networkQuery.setWirelessDHCP(switchDHCP.isSelected());
                        networkQuery.setWirelessAddress(textAddress.getText());
                        networkQuery.setWirelessMask(textMask.getText());
                        networkQuery.setWirelessRouters(textRouter.getText());
                        networkQuery.setWirelessDomainNameServers(textDomains.getText());

                        // -------------------------------------------------------
                        networkService.validateWireless(networkQuery);

                        // -------------------------------------------------------
                        new Thread(() ->
                        {
                            try
                            {
                                networkService.depopulate(networkQuery);
                                JOptionPane.showMessageDialog(null, "Los ajustes de red se estan actualizando...\nComprueba que los cambios se han realizado correctamente.", "Panel de control (Redes Wi-Fi)", JOptionPane.PLAIN_MESSAGE);
                            }
                            catch (Exception ex)
                            {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Redes Wi-Fi)", JOptionPane.PLAIN_MESSAGE);
                            }
                        }).start();
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Redes Wi-Fi)", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
        }
    }

    @Override
    public void viewDidLoad()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void viewDidAppear()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void viewDidDisappear()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void backButtonAction()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void leftButtonAction()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void doneButtonAction()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void rightButtonAction()
    {
        // TODO Auto-generated method stub

    }

}
