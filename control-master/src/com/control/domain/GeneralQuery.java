package com.control.domain;

public class GeneralQuery
{
    private String terminal;
    private String display;

    public GeneralQuery()
    {
        terminal = "terminal";
        display = "900";
    }

    public String getTerminal()
    {
        return terminal;
    }

    public void setTerminal(String terminal)
    {
        this.terminal = terminal;
    }

    public String getDisplay()
    {
        return display;
    }

    public void setDisplay(String display)
    {
        this.display = display;
    }

}
