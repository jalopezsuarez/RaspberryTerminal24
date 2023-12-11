package com.control.exceptions;

public class RouterNetworkException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 8994085385607642912L;

    public RouterNetworkException()
    {
        super("El valor asignado a la puerta de enlace es incorrecto.");
    }

}
