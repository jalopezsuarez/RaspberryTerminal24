package com.control.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSystem
{
    public ArrayList<String> read(String filename, String pattern) throws Exception
    {
        ArrayList<String> response = new ArrayList<String>();

        Pattern regexp = Pattern.compile(pattern);
        Matcher matcher = regexp.matcher("");

        File resource = new File(filename);
        BufferedReader in = null;
        if (resource.exists())
        {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line = null;
            while ((line = in.readLine()) != null)
            {
                matcher.reset(line);
                if (matcher.find())
                {
                    Pattern regex = Pattern.compile(pattern);
                    Matcher match = regex.matcher(line);
                    if (match.find() && match.groupCount() > 0)
                    {
                        for (int i = 1; i <= match.groupCount(); i++)
                        {
                            response.add(match.group(i));
                        }
                    }
                }
            }
            if (in != null)
            {
                in.close();
            }
        }

        return response;
    }

    public void write(String filename, String pattern, String replace, String value) throws Exception
    {
        ArrayList<String> values = new ArrayList<String>();
        values.add(value);
        this.write(filename, pattern, replace, values);
    }

    public void write(String filename, String pattern, String replace, ArrayList<String> values) throws Exception
    {
        File resource = new File(filename);
        File directories = new File(resource.getParent());
        if (!directories.exists())
        {
            boolean create = directories.mkdirs();
            if (!create)
            {
                throw new Exception("No such file or directory (" + filename + ")");
            }
        }

        // -------------------------------------------------------
        String swapname = filename + ".swap";
        try
        {
            File swapResource = new File(swapname);
            swapResource.delete();
        }
        catch (Exception ex)
        {
        }

        // -------------------------------------------------------
        PrintWriter bw = null;
        BufferedReader br = null;

        try
        {
            bw = new PrintWriter(new FileWriter(swapname));
            br = new BufferedReader(new FileReader(filename));

            // -------------------------------------------------------
            Pattern regexp = Pattern.compile(pattern);
            Matcher matcher = regexp.matcher("");

            String line;
            while ((line = br.readLine()) != null)
            {
                matcher.reset(line);
                if (matcher.find())
                {
                    int count = 0;
                    String replacement = replace;
                    for (String value : values)
                    {
                        String param = "$" + count++;
                        while (replacement.contains(param))
                        {
                            replacement = replacement.replace(param, value);
                        }
                    }
                    line = replacement;
                }
                bw.println(line);
            }

            // -------------------------------------------------------
            if (br != null)
            {
                br.close();
            }
            File deleteResource = new File(filename);
            deleteResource.delete();

            if (bw != null)
            {
                bw.close();
            }
            File swapResource = new File(swapname);
            swapResource.renameTo(deleteResource);

        }
        catch (Exception ex)
        {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
            }
            catch (Exception ex1)
            {
            }
            try
            {
                File swapResource = new File(swapname);
                swapResource.delete();
            }
            catch (Exception ex2)
            {
            }

            throw new Exception("Cannot access the file specified (" + filename + ")");
        }
    }

    public void create(String filename) throws Exception
    {
        this.create(filename, "");
    }

    public void create(String filename, String content) throws Exception
    {
        File resource = new File(filename);
        File directories = new File(resource.getParent());
        if (!directories.exists())
        {
            boolean create = directories.mkdirs();
            if (!create)
            {
                throw new Exception("No such file or directory (" + filename + ").");
            }
        }

        // -------------------------------------------------------
        PrintWriter bw = null;
        bw = new PrintWriter(new FileWriter(filename));
        bw.write(content);
        if (bw != null)
        {
            bw.close();
        }
    }

    public void delete(String filename) throws Exception
    {
        File deleteResource = new File(filename);
        deleteResource.delete();
    }

    public boolean exists(String filename) throws Exception
    {
        boolean exists = false;
        File resource = new File(filename);
        exists = resource.exists();

        return exists;
    }

}
