package com.control.service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.control.domain.GeneralQuery;
import com.control.exceptions.TerminalGeneralException;
import com.control.system.FileSystem;

public class GeneralService
{
    // =======================================================

    public final static String HOSTNAME_FILE = "/etc/hostname";
    public final static String HOSTNAME_REGEX = "\\s*([\\p{L}0-9\\S]*)\\s*";
    public final static String HOSTNAME_REPLACE = "$0";

    public final static String HOSTS_FILE = "/etc/hosts";
    public final static String HOSTS_REGEX = "127.0.1.1\\s*([\\p{L}0-9\\S]*)\\s*";
    public final static String HOSTS_REPLACE = "127.0.1.1\t$0";

    public final static String SAMBA_FILE = "/etc/samba/smb.conf";
    public final static String SAMBA_REGEX = "\\s*netbios name\\s*=\\s*([\\p{L}0-9\\S]*)\\s*";
    public final static String SAMBA_REPLACE = "netbios name = $0";

    public final static String DISPLAY_FILE = "/etc/lightdm/lightdm.conf";
    public final static String DISPLAY_REGEX = "\\s*(?:(\\s*#\\s*xserver-command\\s*|\\s*xserver-command\\s*)=\\s*X\\s*-s\\s*0\\s*-dpms)\\s*";
    public final static String DISPLAY_REPLACE = "$0=X -s 0 -dpms";
    public final static String DISPLAY_VALUE_ON = "xserver-command";
    public final static String DISPLAY_VALUE_OFF = "#xserver-command";

    public final static String KEYBOARD_FLAG = "/home/pi/terms/var/keyboard.enabled";
    public final static String KEYBOARD_EXEC_COMMAND = "/usr/bin/florence";

    public final static String TERMINAL_EXEC_COMMAND = "/usr/bin/xterm";

    // =======================================================
    public GeneralQuery populateTerminal() throws Exception
    {
        GeneralQuery populate = new GeneralQuery();

        FileSystem systemFile = new FileSystem();
        ArrayList<String> read = systemFile.read(GeneralService.HOSTNAME_FILE, GeneralService.HOSTNAME_REGEX);
        if (read.size() > 0)
        {
            populate.setTerminal(read.get(0).trim());
        }

        return populate;
    }

    public void validateTerminal(String value) throws Exception
    {
        Pattern regex = Pattern.compile("^([\\p{L}0-9-]*)$");
        Matcher match = regex.matcher(value.trim());
        if (!match.find() || match.groupCount() <= 0)
        {
            throw new TerminalGeneralException();
        }
    }

    public void depopulateTerminal(String value) throws Exception
    {
        FileSystem systemFile = new FileSystem();
        systemFile.write(GeneralService.HOSTNAME_FILE, GeneralService.HOSTNAME_REGEX, GeneralService.HOSTNAME_REPLACE, value);
        systemFile.write(GeneralService.HOSTS_FILE, GeneralService.HOSTS_REGEX, GeneralService.HOSTS_REPLACE, value);
        systemFile.write(GeneralService.SAMBA_FILE, GeneralService.SAMBA_REGEX, GeneralService.SAMBA_REPLACE, value);
    }

    public boolean populateDisplay() throws Exception
    {
        boolean populate = false;

        FileSystem systemFile = new FileSystem();
        ArrayList<String> read = systemFile.read(GeneralService.DISPLAY_FILE, GeneralService.DISPLAY_REGEX);
        if (read.size() > 0)
        {
            populate = !read.get(0).trim().startsWith(GeneralService.DISPLAY_VALUE_OFF);
        }

        return populate;
    }

    public void depopulateDisplay(boolean value) throws Exception
    {
        FileSystem systemFile = new FileSystem();

        String write = (value) ? GeneralService.DISPLAY_VALUE_ON : GeneralService.DISPLAY_VALUE_OFF;
        systemFile.write(GeneralService.DISPLAY_FILE, GeneralService.DISPLAY_REGEX, GeneralService.DISPLAY_REPLACE, write);
    }

    public void launchKeyboard() throws Exception
    {
        Runtime.getRuntime().exec(new String[]
        {
            "sh", "-c", "sudo -u pi " + GeneralService.KEYBOARD_EXEC_COMMAND
        });
    }

    public boolean populateKeyboard() throws Exception
    {
        boolean checkMonopuesto = false;

        FileSystem systemFile = new FileSystem();
        checkMonopuesto = systemFile.exists(GeneralService.KEYBOARD_FLAG);

        return checkMonopuesto;
    }

    public void depopulateKeyboard(boolean value) throws Exception
    {
        FileSystem systemFile = new FileSystem();
        if (value)
        {
            systemFile.create(GeneralService.KEYBOARD_FLAG);
        }
        else
        {
            systemFile.delete(GeneralService.KEYBOARD_FLAG);
        }
    }

    public void lauchTerminal() throws Exception
    {
        Runtime.getRuntime().exec(new String[]
        {
            "sh", "-c", "sudo -u pi " + GeneralService.TERMINAL_EXEC_COMMAND
        });
    }
}
