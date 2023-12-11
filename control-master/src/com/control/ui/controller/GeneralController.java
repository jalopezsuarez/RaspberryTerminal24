package com.control.ui.controller;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.control.domain.GeneralQuery;
import com.control.service.GeneralService;
import com.control.ui.application.ViewController;
import com.control.ui.components.LabelField;
import com.control.ui.components.LabelText;
import com.control.ui.components.LabelTitle;
import com.control.ui.components.Panel;
import com.control.ui.components.SwitchField;
import com.control.ui.components.TextField;
import com.control.ui.components.LinkField;
import com.control.ui.components.layout.GridHelper;

public class GeneralController extends ViewController
{
    private static final long serialVersionUID = -3310121325723682807L;

    // =======================================================
    private TextField textTerminal = null;
    private SwitchField switchDisplay = null;

    private LabelTitle labelGeneral = null;
    private LinkField linkKeyboard = null;
    private SwitchField switchKeyboard = null;

    // =======================================================
    public GeneralController()
    {
        Panel panel = new Panel();
        setViewportView(panel);

        GridHelper layout = new GridHelper(panel);
        {
            int gridy = 0;

            // -------------------------------------------------------
            {
                labelGeneral = new LabelTitle("General");

                layout.constrains().weightx = 1.0;
                layout.add(labelGeneral, 0, gridy++);
            }
            {
                LabelText labelText = new LabelText("Utiliza la siguiente información para identificar este terminal en la red. Puedes cambiar el nombre del equipo en la red. Los cambios podrán afectar a los accesos a los recursos de red.");

                layout.constrains().weightx = 1.0;
                layout.add(labelText, 0, gridy++);
            }
            {
                LabelField labelTitle = new LabelField("Nombre del equipo");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                textTerminal = new TextField();

                layout.constrains().weightx = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textTerminal, 0, gridy++);
            }

            // -------------------------------------------------------
            {
                LabelTitle labelTitle = new LabelTitle("Ahorro de Energía");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                LabelText labelText = new LabelText("Configurar el modo de suspensión y apagado de la pantalla. Ajustes por defecto establecer para 15 minutos (900 segundos) de inactividad.");

                layout.constrains().weightx = 1.0;
                layout.add(labelText, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Impedir que el terminal entre en modo reposo apagando la pantalla.");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                switchDisplay = new SwitchField(false, "Siempre encendido", "Ahorro de energia");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(10, 20, 0, 10);
                layout.add(switchDisplay, 0, gridy++);
            }

            // -------------------------------------------------------
            {
                LabelTitle labelTitle = new LabelTitle("Teclado en pantalla");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                LabelText labelText = new LabelText("El teclado en pantalla muestra un teclado visual con todas las teclas estándar. Se puede utilizar con un raton o con una pantalla tactil.");

                layout.constrains().weightx = 1.0;
                layout.add(labelText, 0, gridy++);
            }
            {
                linkKeyboard = new LinkField("Abrir teclado en pantalla");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(10, 20, 0, 10);
                layout.add(linkKeyboard, 0, gridy++);
            }
            {
                switchKeyboard = new SwitchField(false, "Activado", "Desactivado");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(10, 20, 10, 10);
                layout.add(switchKeyboard, 0, gridy++);
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
            initializeSectionTerminal();

            initializeSectionDisplay();

            initializeSectionKeyboard();

            // =======================================================
            // =======================================================
            labelGeneral.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() == 2)
                    {
                        if (e.isControlDown() && e.isAltDown())
                        {
                            try
                            {
                                GeneralService generalService = new GeneralService();
                                generalService.lauchTerminal();
                            }
                            catch (Exception ex)
                            {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (General)", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }
                }
            });

            textTerminal.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    char c = e.getKeyChar();
                    if (c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE)
                    {
                        if (Character.isSpaceChar(c))
                        {
                            e.consume();
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e)
                {
                    try
                    {
                        GeneralService generalService = new GeneralService();
                        generalService.validateTerminal(textTerminal.getText());
                        textTerminal.setForeground(Color.decode("#0b0b0b"));

                        JTextField textField = (JTextField) e.getSource();
                        generalService.depopulateTerminal(textField.getText());
                    }
                    catch (Exception ex)
                    {
                        textTerminal.setForeground(Color.decode("#de5347"));
                    }
                }
            });

            // -------------------------------------------------------
            switchDisplay.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        SwitchField switcher = (SwitchField) e.getSource();

                        GeneralService generalService = new GeneralService();
                        generalService.depopulateDisplay(switcher.isSelected());
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (General)", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            // -------------------------------------------------------
            linkKeyboard.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        GeneralService generalService = new GeneralService();
                        generalService.launchKeyboard();
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (General)", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            // -------------------------------------------------------
            switchKeyboard.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        SwitchField switcher = (SwitchField) e.getSource();

                        GeneralService generalService = new GeneralService();
                        generalService.depopulateKeyboard(switcher.isSelected());
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (General)", JOptionPane.PLAIN_MESSAGE);
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

    // =======================================================
    private void initializeSectionTerminal()
    {
        try
        {
            GeneralService generalService = new GeneralService();
            GeneralQuery generalQuery = generalService.populateTerminal();
            textTerminal.setText(generalQuery.getTerminal());
        }
        catch (Exception ex)
        {
        }
    }

    // -------------------------------------------------------
    private void initializeSectionDisplay()
    {
        try
        {
            GeneralService generalService = new GeneralService();
            switchDisplay.setSelected(generalService.populateDisplay());
        }
        catch (Exception ex)
        {
        }
    }

    // -------------------------------------------------------
    private void initializeSectionKeyboard()
    {
        try
        {
            GeneralService generalService = new GeneralService();
            switchKeyboard.setSelected(generalService.populateKeyboard());
        }
        catch (Exception ex)
        {
        }
    }
}
