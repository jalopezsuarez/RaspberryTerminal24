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

    public final static String NETWORK_FILE = "/etc/dhcpcd.conf";

    public final static String NETWORK_INTERFACE_REGEX = "interface eth0";
    public final static String NETWORK_ADDRESS_REGEX = "static ip_address";
    public final static String NETWORK_ROUTERS_REGEX = "static routers";
    public final static String NETWORK_DOMAINS_REGEX = "static domain_name_servers";

    public final static String WIRELESS_INTERFACE_REGEX = "interface wlan0";
    public final static String WIRELESS_ADDRESS_REGEX = "static ip_address";
    public final static String WIRELESS_ROUTERS_REGEX = "static routers";
    public final static String WIRELESS_DOMAINS_REGEX = "static domain_name_servers";

    public final static String WIRELESS_COMMAND = "/usr/sbin/wpa_gui";

    // =======================================================
    public NetworkQuery populate() throws Exception
    {
        NetworkQuery populate = null;
        populate = new NetworkQuery();

        // =======================================================
        List<String> contentNetwork = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NetworkService.NETWORK_FILE)))
        {
            String str;
            while ((str = br.readLine()) != null)
            {
                contentNetwork.add(str);
            }
        }

        // -------------------------------------------------------
        int eth0 = -1;
        int wlan0 = -1;

        String eth0AddressField = "";
        String eth0RoutersField = "";
        String eth0NameServersField = "";

        String wlan0AddressField = "";
        String wlan0RoutersField = "";
        String wlan0NameServersField = "";

        for (int i = 0; i < contentNetwork.size(); i++)
        {
            if (contentNetwork.get(i).trim().equals(NetworkService.NETWORK_INTERFACE_REGEX))
            {
                eth0 = i;
                if (contentNetwork.get(i + 1).trim().startsWith(NetworkService.NETWORK_ADDRESS_REGEX))
                {
                    String arr[] = contentNetwork.get(i + 1).split("=");
                    if (arr.length > 1 && arr[1].trim().length() > 0)
                    {
                        eth0AddressField = arr[1].replaceAll("\\s+", "").toLowerCase();
                    }
                }
                if (contentNetwork.get(i + 2).trim().startsWith(NetworkService.NETWORK_ROUTERS_REGEX))
                {
                    String arr[] = contentNetwork.get(i + 2).split("=");
                    if (arr.length > 1 && arr[1].trim().length() > 0)
                    {
                        eth0RoutersField = arr[1].replaceAll("\\s+", "").toLowerCase();
                    }
                }
                if (contentNetwork.get(i + 3).trim().startsWith(NetworkService.NETWORK_DOMAINS_REGEX))
                {
                    String arr[] = contentNetwork.get(i + 3).split("=");
                    if (arr.length > 1 && arr[1].trim().length() > 0)
                    {
                        eth0NameServersField = arr[1].trim().toLowerCase();
                    }
                }
            }

            // -------------------------------------------------------
            if (contentNetwork.get(i).trim().equals(NetworkService.WIRELESS_INTERFACE_REGEX))
            {
                wlan0 = i;
                if (contentNetwork.get(i + 1).trim().startsWith(NetworkService.WIRELESS_ADDRESS_REGEX))
                {
                    String arr[] = contentNetwork.get(i + 1).split("=");
                    if (arr.length > 1 && arr[1].trim().length() > 0)
                    {
                        wlan0AddressField = arr[1].replaceAll("\\s+", "").toLowerCase();
                    }
                }
                if (contentNetwork.get(i + 2).trim().startsWith(NetworkService.WIRELESS_ROUTERS_REGEX))
                {
                    String arr[] = contentNetwork.get(i + 2).split("=");
                    if (arr.length > 1 && arr[1].trim().length() > 0)
                    {
                        wlan0RoutersField = arr[1].replaceAll("\\s+", "").toLowerCase();
                    }
                }
                if (contentNetwork.get(i + 3).trim().startsWith(NetworkService.WIRELESS_DOMAINS_REGEX))
                {
                    String arr[] = contentNetwork.get(i + 3).split("=");
                    if (arr.length > 1 && arr[1].trim().length() > 0)
                    {
                        wlan0NameServersField = arr[1].trim().toLowerCase();
                    }
                }
            }
        }

        // =======================================================
        if (eth0 > 0)
        {
            populate.setNetworkDHCP(false);

            IPv4 ipv4 = new IPv4(eth0AddressField);
            populate.setNetworkAddress(ipv4.getIP());
            populate.setNetworkMask(ipv4.getNetmask());

            populate.setNetworkRouters(eth0RoutersField);
            populate.setNetworkDomainNameServers(eth0NameServersField);
        }

        if (wlan0 > 0)
        {
            populate.setWirelessDHCP(false);

            IPv4 ipv4 = new IPv4(wlan0AddressField);
            populate.setWirelessAddress(ipv4.getIP());
            populate.setWirelessMask(ipv4.getNetmask());

            populate.setWirelessRouters(wlan0RoutersField);
            populate.setWirelessDomainNameServers(wlan0NameServersField);
        }

        // =======================================================
        boolean networkDHCP = false;
        networkDHCP = networkDHCP || eth0AddressField == null || eth0AddressField.length() <= 0;
        networkDHCP = networkDHCP || eth0RoutersField == null || eth0RoutersField.length() <= 0;
        networkDHCP = networkDHCP || eth0NameServersField == null || eth0NameServersField.length() <= 0;
        populate.setNetworkDHCP(networkDHCP);

        boolean wirelessDHCP = false;
        wirelessDHCP = wirelessDHCP || wlan0AddressField == null || wlan0AddressField.length() <= 0;
        wirelessDHCP = wirelessDHCP || wlan0RoutersField == null || wlan0RoutersField.length() <= 0;
        wirelessDHCP = wirelessDHCP || wlan0NameServersField == null || wlan0NameServersField.length() <= 0;
        populate.setWirelessDHCP(wirelessDHCP);

        // =======================================================
        return populate;
    }

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

    public void depopulate(NetworkQuery network) throws Exception
    {
        List<String> contentNetwork = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NetworkService.NETWORK_FILE)))
        {
            String str;
            while ((str = br.readLine()) != null)
            {
                contentNetwork.add(str);
            }
        }

        // =======================================================
        int eth0 = -1;
        int wlan0 = -1;

        String eth0AddressField = "";
        String eth0RoutersField = "";
        String eth0NameServersField = "";

        String wlan0AddressField = "";
        String wlan0RoutersField = "";
        String wlan0NameServersField = "";

        // =======================================================
        for (int i = 0; i < contentNetwork.size(); i++)
        {
            if (contentNetwork.get(i).trim().startsWith(NetworkService.NETWORK_INTERFACE_REGEX))
            {
                if (contentNetwork.get(i).trim().length() > 0)
                {
                    eth0 = i;
                }
            }
        }

        // -------------------------------------------------------
        try
        {
            IPv4 networkIPv4 = new IPv4(network.getNetworkAddress(), network.getNetworkMask());
            eth0AddressField = networkIPv4.getCIDR();

            eth0RoutersField = network.getNetworkRouters();
            eth0NameServersField = network.getNetworkDomainNameServers();
        }
        catch (Exception ex)
        {
            network.setNetworkDHCP(true);
        }

        // -------------------------------------------------------
        if (eth0 < 0)
        {
            eth0 = contentNetwork.size();
            contentNetwork.add("");
            contentNetwork.add(eth0 + 0, NetworkService.NETWORK_INTERFACE_REGEX);
            contentNetwork.add(eth0 + 1, NetworkService.NETWORK_ADDRESS_REGEX + "=" + eth0AddressField);
            contentNetwork.add(eth0 + 2, NetworkService.NETWORK_ROUTERS_REGEX + "=" + eth0RoutersField);
            contentNetwork.add(eth0 + 3, NetworkService.NETWORK_DOMAINS_REGEX + "=" + eth0NameServersField);
        }
        else
        {
            contentNetwork.set(eth0 + 0, NetworkService.NETWORK_INTERFACE_REGEX);
            contentNetwork.set(eth0 + 1, NetworkService.NETWORK_ADDRESS_REGEX + "=" + eth0AddressField);
            contentNetwork.set(eth0 + 2, NetworkService.NETWORK_ROUTERS_REGEX + "=" + eth0RoutersField);
            contentNetwork.set(eth0 + 3, NetworkService.NETWORK_DOMAINS_REGEX + "=" + eth0NameServersField);
        }

        // =======================================================
        // =======================================================
        for (int i = 0; i < contentNetwork.size(); i++)
        {
            if (contentNetwork.get(i).trim().startsWith(NetworkService.WIRELESS_INTERFACE_REGEX))
            {
                if (contentNetwork.get(i).trim().length() > 0)
                {
                    wlan0 = i;
                }
            }
        }

        // -------------------------------------------------------
        try
        {
            IPv4 wirelessIPv4 = new IPv4(network.getWirelessAddress(), network.getWirelessMask());
            wlan0AddressField = wirelessIPv4.getCIDR();

            wlan0RoutersField = network.getWirelessRouters();
            wlan0NameServersField = network.getWirelessDomainNameServers();
        }
        catch (Exception ex)
        {
            network.setWirelessDHCP(true);
        }

        // -------------------------------------------------------
        if (wlan0 < 0)
        {
            wlan0 = contentNetwork.size();
            contentNetwork.add("");
            contentNetwork.add(wlan0 + 0, NetworkService.WIRELESS_INTERFACE_REGEX);
            contentNetwork.add(wlan0 + 1, NetworkService.WIRELESS_ADDRESS_REGEX + "=" + wlan0AddressField);
            contentNetwork.add(wlan0 + 2, NetworkService.WIRELESS_ROUTERS_REGEX + "=" + wlan0RoutersField);
            contentNetwork.add(wlan0 + 3, NetworkService.WIRELESS_DOMAINS_REGEX + "=" + wlan0NameServersField);
        }
        else
        {
            contentNetwork.set(wlan0 + 0, NetworkService.WIRELESS_INTERFACE_REGEX);
            contentNetwork.set(wlan0 + 1, NetworkService.WIRELESS_ADDRESS_REGEX + "=" + wlan0AddressField);
            contentNetwork.set(wlan0 + 2, NetworkService.WIRELESS_ROUTERS_REGEX + "=" + wlan0RoutersField);
            contentNetwork.set(wlan0 + 3, NetworkService.WIRELESS_DOMAINS_REGEX + "=" + wlan0NameServersField);
        }

        // =======================================================
        // =======================================================
        for (int i = 0; i < contentNetwork.size(); i++)
        {
            if (contentNetwork.get(i).trim().startsWith(NetworkService.NETWORK_INTERFACE_REGEX))
            {
                eth0 = i;
            }
        }

        // -------------------------------------------------------
        if (network.isNetworkDHCP())
        {
            for (int i = eth0; i < eth0 + 4; i++)
            {
                contentNetwork.remove(eth0);
            }
        }

        // =======================================================
        for (int i = 0; i < contentNetwork.size(); i++)
        {
            if (contentNetwork.get(i).trim().startsWith(NetworkService.WIRELESS_INTERFACE_REGEX))
            {
                wlan0 = i;
            }
        }

        // -------------------------------------------------------
        if (network.isWirelessDHCP())
        {
            for (int i = wlan0; i < wlan0 + 4; i++)
            {
                contentNetwork.remove(wlan0);
            }
        }

        // =======================================================
        // =======================================================
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NetworkService.NETWORK_FILE)))
        {
            int emptylines = 0;
            for (String line : contentNetwork)
            {
                if (line != null && line instanceof String && line.trim().length() > 0)
                {
                    emptylines = Math.max(0, --emptylines);
                    bw.write(line);
                    bw.newLine();
                }
                else
                {
                    emptylines = Math.max(0, ++emptylines);
                    if (emptylines < 2)
                    {
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }
            if (bw != null)
            {
                bw.close();
            }
        }

        // =======================================================
        // =======================================================
        {
            ProcessBuilder processBuilder = new ProcessBuilder("service", "dhcpcd", "stop");
            processBuilder.directory(new File("/tmp"));
            Process p = processBuilder.start();
            p.waitFor();
        }
        {
            ProcessBuilder processBuilder = new ProcessBuilder("dhclient", "-r", "eth0");
            processBuilder.directory(new File("/tmp"));
            Process p = processBuilder.start();
            p.waitFor();
        }
        {
            ProcessBuilder processBuilder = new ProcessBuilder("dhclient", "-r", "wlan0");
            processBuilder.directory(new File("/tmp"));
            Process p = processBuilder.start();
            p.waitFor();
        }
        {
            ProcessBuilder processBuilder = new ProcessBuilder("service", "dhcpcd", "start");
            processBuilder.directory(new File("/tmp"));
            Process p = processBuilder.start();
            p.waitFor();
        }

        // =======================================================
        // =======================================================
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
