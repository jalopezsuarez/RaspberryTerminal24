package com.control.system;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NetworkHelper
{
    public String[] network()
    {
        Set<String> addresses = new HashSet<>();
        try
        {
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces()))
            {
                if (!ni.isLoopback() && ni.isUp() && ni.getHardwareAddress() != null)
                {
                    for (InterfaceAddress ia : ni.getInterfaceAddresses())
                    {
                        if (ia.getBroadcast() != null)
                        {
                            addresses.add(ia.getAddress().getHostAddress());
                        }
                    }
                }
            }
        }
        catch (SocketException e)
        {
        }
        return addresses.toArray(new String[0]);
    }

}
