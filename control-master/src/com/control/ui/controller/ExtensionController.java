package com.control.ui.controller;

import com.control.domain.ExtensionQuery;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

import com.control.service.ExtensionService;
import com.control.service.ExtensionService.ExtensionEnum;
import com.control.ui.application.ViewController;
import com.control.ui.components.LabelField;
import com.control.ui.components.LabelText;
import com.control.ui.components.LabelTitle;
import com.control.ui.components.LinkField;
import com.control.ui.components.Panel;
import com.control.ui.components.SwitchField;
import com.control.ui.components.TextField;
import com.control.ui.components.layout.GridHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicFileChooserUI;

public class ExtensionController extends ViewController
{
    private static final long serialVersionUID = -3310121325723682807L;

    private LinkField linkApplication = null;
    private TextField textApplication = null;
    private LinkField linkDirectory = null;
    private TextField textDirectory = null;
    private SwitchField switchAutorun = null;

    public ExtensionController()
    {
        Panel panel = new Panel();
        setViewportView(panel);

        GridHelper layout = new GridHelper(panel);
        {
            int gridy = 0;

            // -------------------------------------------------------
            {
                LabelTitle labelTitle = new LabelTitle("Programa de inicio");

                layout.constrains().weightx = 1.0;
                layout.add(labelTitle, 0, gridy++);
            }
            {
                LabelText labelField = new LabelText("Aplicaciones que se ejecutan automáticamente al iniciarse el terminal. Puedes establecer que aplicación se arrancará de forma automática.");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Linea de comando");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                textApplication = new TextField();

                layout.constrains().weightx = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textApplication, 0, gridy++);
            }
            {
                linkApplication = new LinkField("Buscar archivo de aplicación");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(linkApplication, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Directorio de trabajo");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                textDirectory = new TextField();

                layout.constrains().weightx = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(textDirectory, 0, gridy++);
            }
            {
                linkDirectory = new LinkField("Seleccionar directorio de trabajo");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(linkDirectory, 0, gridy++);
            }
            {
                LabelField labelField = new LabelField("Activar / Desactivar programa de inicio");

                layout.constrains().weightx = 1.0;
                layout.add(labelField, 0, gridy++);
            }
            {
                switchAutorun = new SwitchField(false, "Activado", "Desactivado");

                layout.constrains().fill = GridBagConstraints.NONE;
                layout.constrains().anchor = GridBagConstraints.NORTHWEST;
                layout.constrains().weightx = 0.0;
                layout.constrains().weighty = 0.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(switchAutorun, 0, gridy++);
            }

            // -------------------------------------------------------
            {
                JLabel vertical = new JLabel();

                layout.constrains().weightx = 1.0;
                layout.constrains().weighty = 1.0;
                layout.constrains().insets = new Insets(5, 20, 0, 20);
                layout.add(vertical, 0, gridy++);
            }

            // =======================================================
            // =======================================================
            linkApplication.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        UIManager.put("FileChooser.readOnly", Boolean.TRUE);
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new File("/home/pi"));
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        fileChooser.setAcceptAllFileFilterUsed(false);
                        {
                            BasicFileChooserUI ui = (BasicFileChooserUI) fileChooser.getUI();
                            Action folder = ui.getNewFolderAction();
                            folder.setEnabled(false);
                        }
                        {
                            BasicFileChooserUI ui = (BasicFileChooserUI) fileChooser.getUI();
                            Action folder = ui.getGoHomeAction();
                            folder.setEnabled(false);
                        }

                        int rVal = fileChooser.showOpenDialog(null);
                        if (rVal == JFileChooser.APPROVE_OPTION)
                        {
                            ExtensionService extensionService = new ExtensionService();
                            ExtensionQuery autorunQuery = new ExtensionQuery();
                            autorunQuery.setAutorunCommand(fileChooser.getSelectedFile().toString());
                            extensionService.depopulateAutorun(autorunQuery, ExtensionEnum.AUTORUN_COMMAND);

                            textApplication.setText(fileChooser.getSelectedFile().toString());
                        }
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Extensiones)", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            linkDirectory.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        UIManager.put("FileChooser.readOnly", Boolean.TRUE);
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new File("/home/pi"));
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        fileChooser.setAcceptAllFileFilterUsed(false);
                        {
                            BasicFileChooserUI ui = (BasicFileChooserUI) fileChooser.getUI();
                            Action folder = ui.getNewFolderAction();
                            folder.setEnabled(false);
                        }
                        {
                            BasicFileChooserUI ui = (BasicFileChooserUI) fileChooser.getUI();
                            Action folder = ui.getGoHomeAction();
                            folder.setEnabled(false);
                        }

                        int rVal = fileChooser.showOpenDialog(null);
                        if (rVal == JFileChooser.APPROVE_OPTION)
                        {
                            ExtensionService extensionService = new ExtensionService();
                            ExtensionQuery autorunQuery = new ExtensionQuery();
                            autorunQuery.setAutorunDirectory(fileChooser.getSelectedFile().toString());
                            extensionService.depopulateAutorun(autorunQuery, ExtensionEnum.AUTORUN_DIRECTORY);

                            textDirectory.setText(fileChooser.getSelectedFile().toString());
                        }
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Extensiones)", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            // =======================================================
            // =======================================================
            try
            {
                ExtensionService extensionService = new ExtensionService();
                ExtensionQuery autorunQuery = extensionService.populateAutorun();

                // -------------------------------------------------------
                textApplication.setText(autorunQuery.getAutorunCommand());
                textDirectory.setText(autorunQuery.getAutorunDirectory());
                switchAutorun.setSelected(autorunQuery.isAutorunEnabled());
            }
            catch (Exception ex)
            {
            }

            // =======================================================
            // =======================================================
            textApplication.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    if (!e.isTemporary())
                    {
                        try
                        {
                            TextField text = (TextField) e.getSource();
                            ExtensionService extensionService = new ExtensionService();
                            ExtensionQuery autorunQuery = new ExtensionQuery();
                            autorunQuery.setAutorunCommand(text.getText());
                            extensionService.depopulateAutorun(autorunQuery, ExtensionEnum.AUTORUN_COMMAND);
                        }
                        catch (Exception ex)
                        {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Extension)", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            });

            textDirectory.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    if (!e.isTemporary())
                    {
                        try
                        {
                            TextField text = (TextField) e.getSource();
                            ExtensionService extensionService = new ExtensionService();
                            ExtensionQuery autorunQuery = new ExtensionQuery();
                            autorunQuery.setAutorunDirectory(text.getText());
                            extensionService.depopulateAutorun(autorunQuery, ExtensionEnum.AUTORUN_DIRECTORY);
                        }
                        catch (Exception ex)
                        {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Extension)", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            });

            switchAutorun.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        SwitchField switcher = (SwitchField) e.getSource();
                        ExtensionService extensionService = new ExtensionService();
                        ExtensionQuery autorunQuery = new ExtensionQuery();
                        autorunQuery.setAutorunEnabled(switcher.isSelected());
                        extensionService.depopulateAutorun(autorunQuery, ExtensionEnum.AUTORUN_FLAGS);
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido algun problema con la configuracion.\n" + ex.getMessage(), "Panel de control (Extension)", JOptionPane.PLAIN_MESSAGE);
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
