package com.thekingelessar.trashtalk;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ChatMessageHandler
{
    
    public static ArrayList<String> insultList = new ArrayList<String>();
    public static ArrayList<String> encouragementList = new ArrayList<String>();
    
    public static int insultIndex = -1;
    public static int encouragementIndex = -1;
    
    public static boolean useShout = false;
    public static boolean useEncouragement = false;
    
    public ChatMessageHandler()
    {
        loadMessages();
    }
    
    public static String getInsult()
    {
        if (!(insultIndex >= (insultList.size() - 1)))
        {
            insultIndex++;
        }
        else
        {
            insultIndex = 0;
        }
        return insultList.get(insultIndex);
    }
    
    public static String getEncouragement()
    {
        if (!(encouragementIndex >= (encouragementList.size() - 1)))
        {
            encouragementIndex++;
        }
        else
        {
            encouragementIndex = 0;
        }
        return encouragementList.get(encouragementIndex);
    }
    
    
    public void loadMessages()
    {
        try
        {
            String fileContents;
            File f = new File("./mods/TTMessages.json");
            System.out.println("Attempting to read from file: " + f.getCanonicalPath());
            
            if (f.exists() && !f.isDirectory())
            {
                Scanner scanner = new Scanner(f);
                fileContents = scanner.useDelimiter("\\A").next();
                scanner.close();
                System.out.println("TTMessages.json found.");
            }
            else
            {
                System.out.println("TTMessages.json not found. Creating one.");
                
                String respath = "/messages/TTMessages.json";
                InputStream in = ChatMessageHandler.class.getResourceAsStream(respath);
                if (in == null)
                {
                    throw new Exception("Resource not found: " + respath);
                }
                
                fileContents = IOUtils.toString(in, "UTF-8");
                
                PrintWriter writer = new PrintWriter("./mods/TTMessages.json", "UTF-8");
                writer.println(fileContents);
                writer.close();
                System.out.println("TTMessages.json file created.");
            }
            
            JSONObject fullJSONObject = new JSONObject(fileContents);
            JSONArray insultArray = fullJSONObject.getJSONArray("insults");
            JSONArray encouragementArray = fullJSONObject.getJSONArray("encouragement");
            
            for (int i = 0; i < insultArray.length(); i++)
            {
                insultList.add(insultArray.getString(i));
            }
            
            for (int i = 0; i < encouragementArray.length(); i++)
            {
                encouragementList.add(encouragementArray.getString(i));
            }
            
            Collections.shuffle(insultList);
            Collections.shuffle(encouragementList);
            
        }
        catch (Exception e)
        {
            System.out.println("ERROR! YOUR \"TTMessages.json\" FILE IS FORMATTED INCORRECTLY. Try using https://jsonlint.com/ to check it.");
            e.printStackTrace();
        }
        
    }
    
}