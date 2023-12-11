package com.control.ui.controller;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.control.service.RemminaService;
import com.control.ui.application.ViewController;
import com.control.ui.components.LabelField;
import com.control.ui.components.LabelText;
import com.control.ui.components.LabelTitle;
import com.control.ui.components.LinkField;
import com.control.ui.components.Panel;
import com.control.ui.components.SwitchField;
import com.control.ui.components.layout.GridHelper;

public class RemoteController extends ViewController
{
    private static final long serialVersionUID = 2000758101043773840L;

    // =======================================================
    private LinkField linkRemmina = null;
    private SwitchField switchMonopuesto = null;

    // =======================================================
    public RemoteController()
    {
        Panel panel = new Panel();
        setViewportView(panel);

        GridHelper layout = new GridHelper(panel);
        {
            int gridy = 0;
            {
                LabelTitle labelTitle = new LabelTitle("Conexiones a Escritorios remotos");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                LabelText labelText = new LabelText(
                        "Todos los cambios realizados se aplicaran una vez reiniciado el terminal. Es recomendable no guardar las contraseñas en las conexiones remotas. Tener en cuenta la ubicación del terminal para evitar accesos no autorizados a los equipos.");

                layout.constrains().weightx = 1.0;
                layout.add(labelText, 0, gridy++);
            }
            {
                linkRemmina = new LinkField("Abrir conexiones a escritorios remotos");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(10, 20, 0, 10);
                layout.add(linkRemmina, 0, gridy++);
            }
            {
                LabelTitle labelTitle = new LabelTitle("Monopuesto");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                LabelText labelText = new LabelText("Cuando se active el Monopuesto conectara automaticamente a una Conexión de Escritorio Remoto. Tener en cuenta la ubicación del terminal para evitar accesos no autorizados a los equipos.");

                layout.constrains().weightx = 1.0;
                layout.add(labelText, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Monopuesto (Habilitar / Deshabilitar)");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                switchMonopuesto = new SwitchField(false);

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(10, 20, 0, 10);
                layout.add(switchMonopuesto, 0, gridy++);
            }
            {
                JLabel vertical = new JLabel();

                layout.constrains().weightx = 1.0;
                layout.constrains().weighty = 1.0;
                layout.constrains().insets = new Insets(30, 0, 0, 0);
                layout.add(vertical, 0, gridy++);
            }
        }

        // =======================================================
        // =======================================================
        try
        {
            RemminaService remminaService = new RemminaService();
            switchMonopuesto.setSelected(remminaService.pouplateMonopuesto());
        }
        catch (Exception ex)
        {
        }

        // =======================================================
        // =======================================================
        linkRemmina.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    RemminaService remminaService = new RemminaService();
                    remminaService.launchRemmina();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Escritorios Remotos)", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // -------------------------------------------------------
        switchMonopuesto.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    SwitchField switcher = (SwitchField) e.getSource();

                    RemminaService remminaService = new RemminaService();
                    remminaService.depopulateMonopuesto(switcher.isSelected());
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Escritorios Remotos)", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
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
