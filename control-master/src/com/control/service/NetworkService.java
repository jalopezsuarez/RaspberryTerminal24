package com.control.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.control.domain.NetworkQuery;
import com.control.exceptions.AddressNetworkException;
import com.control.exceptions.AddressWirelessException;
import com.control.exceptions.DNSNetworkException;
import com.control.exceptions.DNSWirelessException;
import com.control.exceptions.RouterNetworkException;
import com.control.exceptions.RouterWirelessException;
import com.control.system.IPv4;

public class NetworkService
{
    // =======================================================

    public final static String NETWORK_FILE = "/etc/network/interfaces";

    public final static String WIRELESS_COMMAND = "/usr/sbin/wpa_gui";

    // =======================================================
    
    public NetworkQuery populate() throws Exception 
    {
        NetworkQuery networkQuery = new NetworkQuery();
        try (BufferedReader br = new BufferedReader(new FileReader(NETWORK_FILE))) 
        {
            String line;
            boolean isEth0 = false;
            boolean isWlan0 = false;

            while ((line = br.readLine()) != null) 
            {
                if (line.contains("auto eth0")) 
                {
                    isEth0 = true;
                    isWlan0 = false;
                } 
                else if (isEth0) 
                {
                    if (line.trim().startsWith("iface eth0 inet dhcp")) 
                    {
                        networkQuery.setNetworkDHCP(true);
                    } 
                    else if (line.trim().startsWith("iface eth0 inet static")) 
                    {
                        networkQuery.setNetworkDHCP(false);
                    } 
                    else if (line.trim().startsWith("address")) 
                    {
                        networkQuery.setNetworkAddress(line.split(" ")[1]);
                    } 
                    else if (line.trim().startsWith("netmask")) 
                    {
                        networkQuery.setNetworkMask(line.split(" ")[1]);
                    } 
                    else if (line.trim().startsWith("gateway")) 
                    {
                        networkQuery.setNetworkRouters(line.split(" ")[1]);
                    } 
                    else if (line.trim().startsWith("dns-nameservers")) 
                    {
                        networkQuery.setNetworkDomainNameServers(line.split(" ")[1]);
                    } 
                }
                
                // =======================================================                
                
                if (line.contains("allow-hotplug wlan0")) 
                {
                    isEth0 = false;
                    isWlan0 = true;
                } 
                else if (isWlan0) 
                {
                    if (line.trim().startsWith("iface wlan0 inet dhcp")) 
                    {
                        networkQuery.setWirelessDHCP(true);
                    } 
                    else if (line.trim().startsWith("iface wlan0 inet static")) 
                    {
                        networkQuery.setWirelessDHCP(false);
                    } 
                    else if (line.trim().startsWith("address")) 
                    {
                        networkQuery.setWirelessAddress(line.split(" ")[1]);
                    } 
                    else if (line.trim().startsWith("netmask")) 
                    {
                        networkQuery.setWirelessMask(line.split(" ")[1]);
                    } 
                    else if (line.trim().startsWith("gateway")) 
                    {
                        networkQuery.setWirelessRouters(line.split(" ")[1]);
                    } 
                    else if (line.trim().startsWith("dns-nameservers")) 
                    {
                        networkQuery.setWirelessDomainNameServers(line.split(" ")[1]);
                    } 
                }
            }
        }
        
        return networkQuery;
    }

    public void depopulate(NetworkQuery networkQuery) throws Exception 
    {
        List<String> lines = new ArrayList<>();
        
        // =======================================================        

        lines.add("# terminal: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        lines.add("# Warning! Network configuration automatically generated and modifications may be overwritten and lost.");

        lines.add(" ");
        lines.add("auto lo");
        lines.add("iface lo inet loopback");

        lines.add(" ");
        lines.add("auto eth0");
        if (networkQuery.isNetworkDHCP()) 
        {
            lines.add("iface eth0 inet dhcp");
        } 
        else 
        {
            lines.add("iface eth0 inet static");
            if (networkQuery.getNetworkAddress() != null && networkQuery.getNetworkAddress().trim().length() > 0)
                lines.add("address " + networkQuery.getNetworkAddress());
            if (networkQuery.getNetworkMask() != null && networkQuery.getNetworkMask().trim().length() > 0)
                lines.add("netmask " + networkQuery.getNetworkMask());
            if (networkQuery.getNetworkRouters() != null && networkQuery.getNetworkRouters().trim().length() > 0)
                lines.add("gateway " + networkQuery.getNetworkRouters());
            if (networkQuery.getNetworkDomainNameServers() != null && networkQuery.getNetworkDomainNameServers().trim().length() > 0)
                lines.add("dns-nameservers " + networkQuery.getNetworkDomainNameServers());
        }
        
        // =======================================================        

        lines.add(" ");
        lines.add("allow-hotplug wlan0");
        if (networkQuery.isWirelessDHCP()) 
        {
            lines.add("iface wlan0 inet dhcp");
            lines.add("wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf");
        } 
        else 
        {
            lines.add("iface wlan0 inet static");
            lines.add("wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf");
            if (networkQuery.getWirelessAddress() != null && networkQuery.getWirelessAddress().trim().length() > 0)
                lines.add("address " + networkQuery.getWirelessAddress());
            if (networkQuery.getWirelessMask() != null && networkQuery.getWirelessMask().trim().length() > 0)
                lines.add("netmask " + networkQuery.getWirelessMask());
            if (networkQuery.getWirelessRouters() != null && networkQuery.getWirelessRouters().trim().length() > 0)
                lines.add("gateway " + networkQuery.getWirelessRouters());
            if (networkQuery.getWirelessDomainNameServers() != null && networkQuery.getWirelessDomainNameServers().trim().length() > 0)
                lines.add("dns-nameservers " + networkQuery.getWirelessDomainNameServers());
        }
        
        // =======================================================        

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NETWORK_FILE))) 
        {
            for (String line : lines) 
            {
                bw.write(line);
                bw.newLine();
            }
        }

        // =======================================================
        // =======================================================
        {
            ProcessBuilder processBuilder = new ProcessBuilder("ifdown", "--force", "--ignore-errors", "eth0", "wlan0");
            processBuilder.directory(new File("/tmp"));
            Process p = processBuilder.start();
            p.waitFor();
        }
        {
            ProcessBuilder processBuilder = new ProcessBuilder("ifup", "--force", "--ignore-errors", "eth0", "wlan0");
            processBuilder.directory(new File("/tmp"));
            Process p = processBuilder.start();
            //p.waitFor();
        }
        
        // =======================================================
        // =======================================================
    }
    
    // =======================================================
    
    public void validateNetwork(NetworkQuery network) throws Exception
    {
        try
        {
            IPv4 networkIPv4 = new IPv4(network.getNetworkAddress(), network.getNetworkMask());
            networkIPv4.getCIDR();
        }
        catch (Exception ex)
        {
            throw new AddressNetworkException();
        }

        try
        {
            String router = network.getNetworkRouters().trim();

            String[] st = router.split("\\.");
            if (st.length != 4)
            {
                throw new NumberFormatException("Invalid IP address: " + router);
            }
            for (int n = 0; n < st.length; n++)
            {
                int value = Integer.parseInt(st[n]);
                if (value != (value & 0xff))
                {
                    throw new NumberFormatException("Invalid IP address: " + router);
                }
            }
        }
        catch (Exception ex)
        {
            throw new RouterNetworkException();
        }

        try
        {
            String domains = network.getNetworkDomainNameServers().trim();
            String[] dns = domains.split(" ");
            for (int i = 0; i < dns.length; i++)
            {
                Pattern regex = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
                Matcher match = regex.matcher(dns[i].trim());
                if (!match.find() || match.groupCount() != 4)
                {
                    throw new DNSNetworkException();
                }
            }
        }
        catch (Exception ex)
        {
            throw new DNSNetworkException();
        }
    }

    public void validateWireless(NetworkQuery network) throws Exception
    {
        try
        {
            IPv4 networkIPv4 = new IPv4(network.getWirelessAddress(), network.getWirelessMask());
            networkIPv4.getCIDR();
        }
        catch (Exception ex)
        {
            throw new AddressWirelessException();
        }

        try
        {
            String router = network.getWirelessRouters().trim();

            String[] st = router.split("\\.");
            if (st.length != 4)
            {
                throw new NumberFormatException("Invalid IP address: " + router);
            }
            for (int n = 0; n < st.length; n++)
            {
                int value = Integer.parseInt(st[n]);
                if (value != (value & 0xff))
                {
                    throw new NumberFormatException("Invalid IP address: " + router);
                }
            }
        }
        catch (Exception ex)
        {
            throw new RouterWirelessException();
        }

        try
        {
            String domains = network.getWirelessDomainNameServers().trim();
            String[] dns = domains.split(" ");
            for (int i = 0; i < dns.length; i++)
            {
                Pattern regex = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
                Matcher match = regex.matcher(dns[i].trim());
                if (!match.find() || match.groupCount() != 4)
                {
                    throw new DNSWirelessException();
                }
            }
        }
        catch (Exception ex)
        {
            throw new DNSWirelessException();
        }
    }   

    // =======================================================
    
    public void launchWireless() throws Exception
    {
        Runtime.getRuntime().exec(new String[]
        {
            "sh", "-c", "sudo -u pi " + NetworkService.WIRELESS_COMMAND
        });
    }
}
