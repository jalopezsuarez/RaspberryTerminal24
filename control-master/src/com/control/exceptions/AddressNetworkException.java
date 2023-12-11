package com.control.exceptions;

public class AddressNetworkException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 8994085385607642912L;

    public AddressNetworkException()
    {
        super("Los valores de la dirección IPv4 o mascara de subred son incorrectos.");
    }

}
