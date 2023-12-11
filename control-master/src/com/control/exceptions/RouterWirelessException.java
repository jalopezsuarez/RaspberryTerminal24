package com.control.exceptions;

public class RouterWirelessException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 8994085385607642912L;

    public RouterWirelessException()
    {
        super("El valor asignado a la puerta de enlace es incorrecto.");
    }
}
