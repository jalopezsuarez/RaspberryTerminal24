package com.control.service;

import com.control.system.FileSystem;

public class RemminaService
{
    // =======================================================

    public final static String REMMINA_LAUNCH_COMMAND = "/usr/bin/remmina";
    public final static String MONOPUESTO_FLAG = "/home/pi/terms/var/monopuesto.enabled";

    // =======================================================
    public void launchRemmina() throws Exception
    {
        Runtime.getRuntime().exec(new String[]
        {
            "sh", "-c", "sudo -u pi " + RemminaService.REMMINA_LAUNCH_COMMAND
        });
    }

    public boolean pouplateMonopuesto() throws Exception
    {
        boolean checkMonopuesto = false;

        FileSystem systemFile = new FileSystem();
        checkMonopuesto = systemFile.exists(RemminaService.MONOPUESTO_FLAG);

        return checkMonopuesto;
    }

    public void depopulateMonopuesto(boolean value) throws Exception
    {
        FileSystem systemFile = new FileSystem();
        if (value)
        {
            systemFile.create(RemminaService.MONOPUESTO_FLAG);
        }
        else
        {
            systemFile.delete(RemminaService.MONOPUESTO_FLAG);
        }
    }

}
