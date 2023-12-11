package com.control.exceptions;

public class DNSNetworkException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 8994085385607642912L;

    public DNSNetworkException()
    {
        super("Los valores especificados como servidores DNS son incorrectos.");
    }
}
