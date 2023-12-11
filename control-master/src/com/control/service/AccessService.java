package com.control.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.control.system.SHA512Crypt;

public class AccessService
{
    public Boolean authenticate(String isPassword) throws Exception
    {
        Boolean response = false;

        final String passwd = "/etc/passwd";
        final String shadow = "/etc/shadow";

        String isUsername = System.getenv("SUDO_USER");
        if (isUsername == null)
        {
            isUsername = System.getProperty("user.name");
        }

        File f_passwd = new File(passwd);
        File f_shadow = new File(shadow);
        BufferedReader in;
        if (f_shadow.exists())
        {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(f_shadow)));
        }
        else
        {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(f_passwd)));
        }
        String line;
        line = in.readLine();

        while (line != null)
        {
            if (line.startsWith(isUsername))
            {
                break;
            }
            line = in.readLine();
        }

        StringTokenizer tok = new StringTokenizer(line, ":");
        @SuppressWarnings("unused")
        String login = tok.nextToken();
        String password = tok.nextToken();

        response = SHA512Crypt.verifyPassword(isPassword, password);

        in.close();

        return response;
    }

}
