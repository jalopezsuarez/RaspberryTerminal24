package com.control.ui.application;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.control.ui.controller.application.ApplicationController;
import com.control.ui.dialog.AuthenticateDialog;
import com.control.ui.dialog.BootDialog;

public class ApplicationDelegate
{
    ApplicationController applicationController = null;

    public ApplicationDelegate()
    {
        AuthenticateDialog authenticateDialog = new AuthenticateDialog();
        if (authenticateDialog.isAuthenticate())
        {
            applicationController = new ApplicationController();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            int relativeWidth = (int) Math.round(screenSize.getWidth() - ((screenSize.getWidth() * 8) / 100));
            int relativeHeight = (int) Math.round(screenSize.getHeight() - ((screenSize.getWidth() * 8) / 100));

            int with = Math.min(820, relativeWidth);
            int height = Math.min(648, relativeHeight);

            applicationController.setSize(new Dimension(with, height));
            applicationController.setPreferredSize(new Dimension(with, height));
            applicationController.pack();

            applicationController.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            applicationController.setLocationRelativeTo(null);
            applicationController.setState(Frame.NORMAL);
            applicationController.setExtendedState(Frame.NORMAL);
            applicationController.requestFocusInWindow();
            applicationController.toFront();

            applicationController.setVisible(true);
        }
        else
        {
            System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        System.setProperty("apple.awt.graphics.UseQuartz", "true");
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("sun.java2d.xrender", "true");
        System.setProperty("swing.aatext", "true");

        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                if (args != null && args.length > 0)
                {
                    if (args[0].equals("--poweroff"))
                    {
                        BootDialog boot = new BootDialog();
                        boot.setModal(false);
                        boot.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    }
                }
                else
                {
                    new ApplicationDelegate();
                }
            }
        });
    }

}
