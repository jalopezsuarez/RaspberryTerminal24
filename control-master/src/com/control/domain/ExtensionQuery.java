/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.control.domain;

/**
 *
 * @author administrator
 */
public class ExtensionQuery
{
    private boolean autorunEnabled;
    private String autorunCommand;
    private String autorunDirectory;

    public ExtensionQuery()
    {
        autorunEnabled = false;
        autorunCommand = "";
        autorunDirectory = "";
    }

    public boolean isAutorunEnabled()
    {
        return autorunEnabled;
    }

    public void setAutorunEnabled(boolean autorunEnabled)
    {
        this.autorunEnabled = autorunEnabled;
    }

    public String getAutorunCommand()
    {
        return autorunCommand;
    }

    public void setAutorunCommand(String autorunCommand)
    {
        this.autorunCommand = autorunCommand;
    }

    public String getAutorunDirectory()
    {
        return autorunDirectory;
    }

    public void setAutorunDirectory(String autorunDirectory)
    {
        this.autorunDirectory = autorunDirectory;
    }

}
