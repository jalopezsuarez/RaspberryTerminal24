package com.control.exceptions;

public class DNSWirelessException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 8994085385607642912L;

    public DNSWirelessException()
    {
        super("Los valores especificados como servidores DNS son incorrectos.");
    }
}
