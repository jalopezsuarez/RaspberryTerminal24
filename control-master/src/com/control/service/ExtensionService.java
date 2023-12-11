package com.control.service;

import com.control.domain.ExtensionQuery;
import com.control.system.FileSystem;
import java.util.ArrayList;

public class ExtensionService
{
    // =======================================================

    private final static String AUTORUN_FLAGS = "/home/pi/terms/var/autorun.enabled";

    private final static String AUTORUN_FILE = "/etc/systemd/system/autorun.service";
    private final static String AUTORUN_COMMAND_REGEX = "\\s*ExecStart\\s*=\\s*([\\p{L}0-9\\S ]*)\\s*";
    private final static String AUTORUN_COMMAND_REPLACE = "ExecStart=$0";
    private final static String AUTORUN_DIRECTORY_REGEX = "\\s*WorkingDirectory\\s*=\\s*([\\p{L}0-9\\S ]*)\\s*";
    private final static String AUTORUN_DIRECTORY_REPLACE = "WorkingDirectory=$0";

    public enum ExtensionEnum
    {
        AUTORUN_COMMAND, AUTORUN_DIRECTORY, AUTORUN_FLAGS
    }

    // =======================================================
    public ExtensionQuery populateAutorun() throws Exception
    {
        ExtensionQuery populate = new ExtensionQuery();
        {
            FileSystem systemFile = new FileSystem();
            ArrayList<String> read = systemFile.read(ExtensionService.AUTORUN_FILE, ExtensionService.AUTORUN_COMMAND_REGEX);
            if (read.size() > 0)
            {
                populate.setAutorunCommand(read.get(0).trim());
            }
        }
        {
            FileSystem systemFile = new FileSystem();
            ArrayList<String> read = systemFile.read(ExtensionService.AUTORUN_FILE, ExtensionService.AUTORUN_DIRECTORY_REGEX);
            if (read.size() > 0)
            {
                populate.setAutorunDirectory(read.get(0).trim());
            }
        }
        {
            FileSystem systemFile = new FileSystem();
            boolean checkAutorun = systemFile.exists(ExtensionService.AUTORUN_FLAGS);
            populate.setAutorunEnabled(checkAutorun);
        }

        return populate;
    }

    public void depopulateAutorun(ExtensionQuery value, ExtensionEnum extension) throws Exception
    {
        if (extension == ExtensionEnum.AUTORUN_COMMAND)
        {
            FileSystem systemFile = new FileSystem();
            systemFile.write(ExtensionService.AUTORUN_FILE, ExtensionService.AUTORUN_COMMAND_REGEX, ExtensionService.AUTORUN_COMMAND_REPLACE, value.getAutorunCommand());
        }
        else if (extension == ExtensionEnum.AUTORUN_DIRECTORY)
        {
            FileSystem systemFile = new FileSystem();
            systemFile.write(ExtensionService.AUTORUN_FILE, ExtensionService.AUTORUN_DIRECTORY_REGEX, ExtensionService.AUTORUN_DIRECTORY_REPLACE, value.getAutorunDirectory());
        }
        else if (extension == ExtensionEnum.AUTORUN_FLAGS)
        {
            FileSystem systemFile = new FileSystem();
            if (value.isAutorunEnabled())
            {
                systemFile.create(ExtensionService.AUTORUN_FLAGS);
            }
            else
            {
                systemFile.delete(ExtensionService.AUTORUN_FLAGS);
            }
        }
    }

}
