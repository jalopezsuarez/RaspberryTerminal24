package com.control.service;

import java.io.File;

public class SystemService
{
    // =======================================================

    public final static String REBOOT_COMMAND = "reboot";
    public final static String SHUTDOWN_COMMAND = "halt";

    // =======================================================
    public void reboot() throws Exception
    {
        ProcessBuilder processBuilder = new ProcessBuilder(SystemService.REBOOT_COMMAND);
        processBuilder.directory(new File("/tmp"));
        processBuilder.start();
    }

    public void shutdown() throws Exception
    {
        ProcessBuilder processBuilder = new ProcessBuilder(SystemService.SHUTDOWN_COMMAND);
        processBuilder.directory(new File("/tmp"));
        processBuilder.start();
    }

}
