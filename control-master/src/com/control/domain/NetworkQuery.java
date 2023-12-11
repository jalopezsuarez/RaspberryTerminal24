package com.control.domain;

public class NetworkQuery
{
    private boolean networkDHCP;
    private String networkAddress;
    private String networkMask;
    private String networkRouters;
    private String networkDomainNameServers;

    private boolean wirelessDHCP;
    private String wirelessAddress;
    private String wirelessMask;
    private String wirelessRouters;
    private String wirelessDomainNameServers;

    public NetworkQuery()
    {
        networkDHCP = true;
        networkAddress = "";
        networkMask = "";
        networkRouters = "";
        networkDomainNameServers = "";

        wirelessDHCP = true;
        wirelessAddress = "";
        wirelessMask = "";
        wirelessRouters = "";
        wirelessDomainNameServers = "";
    }

    public boolean isNetworkDHCP()
    {
        return networkDHCP;
    }

    public void setNetworkDHCP(boolean networkDHCP)
    {
        this.networkDHCP = networkDHCP;
    }

    public String getNetworkAddress()
    {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress)
    {
        this.networkAddress = networkAddress;
    }

    public String getNetworkMask()
    {
        return networkMask;
    }

    public void setNetworkMask(String networkMask)
    {
        this.networkMask = networkMask;
    }

    public String getNetworkRouters()
    {
        return networkRouters;
    }

    public void setNetworkRouters(String networkRouters)
    {
        this.networkRouters = networkRouters;
    }

    public String getNetworkDomainNameServers()
    {
        return networkDomainNameServers;
    }

    public void setNetworkDomainNameServers(String networkDomainNameServers)
    {
        this.networkDomainNameServers = networkDomainNameServers;
    }

    public boolean isWirelessDHCP()
    {
        return wirelessDHCP;
    }

    public void setWirelessDHCP(boolean wirelessDHCP)
    {
        this.wirelessDHCP = wirelessDHCP;
    }

    public String getWirelessAddress()
    {
        return wirelessAddress;
    }

    public void setWirelessAddress(String wirelessAddress)
    {
        this.wirelessAddress = wirelessAddress;
    }

    public String getWirelessMask()
    {
        return wirelessMask;
    }

    public void setWirelessMask(String wirelessMask)
    {
        this.wirelessMask = wirelessMask;
    }

    public String getWirelessRouters()
    {
        return wirelessRouters;
    }

    public void setWirelessRouters(String wirelessRouters)
    {
        this.wirelessRouters = wirelessRouters;
    }

    public String getWirelessDomainNameServers()
    {
        return wirelessDomainNameServers;
    }

    public void setWirelessDomainNameServers(String wirelessDomainNameServers)
    {
        this.wirelessDomainNameServers = wirelessDomainNameServers;
    }
}
