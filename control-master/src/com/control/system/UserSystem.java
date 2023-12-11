package com.control.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserSystem
{
    private static String userdir = null;

    public static String userdir() throws Exception
    {
        if (userdir == null)
        {
            String user = System.getenv("SUDO_USER");
            if (user == null)
            {
                user = System.getProperty("user.name");
            }
            String response = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]
            {
                "sh", "-c", "echo ~" + user
            }).getInputStream())).readLine();

            while (response != null && response.length() > 0 && (response.charAt(response.length() - 1) == '\\' || response.charAt(response.length() - 1) == '/'))
            {
                response = response.substring(0, response.length() - 1);
            }

            userdir = response;
        }

        return userdir;
    }
}
