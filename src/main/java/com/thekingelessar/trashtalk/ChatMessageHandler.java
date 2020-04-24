package com.thekingelessar.trashtalk;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ChatMessageHandler
{
    
    public static ArrayList<String> insultList = new ArrayList<String>();
    public static ArrayList<String> encouragementList = new ArrayList<String>();
    public static ArrayList<String> reactionList = new ArrayList<String>();
    
    public static int insultIndex = -1;
    public static int encouragementIndex = -1;
    public static int reactionIndex = -1;
    
    public static String useShout = "";
    
    public static String[] currentCategoryArray = {"insult", "encouragement", "reaction"};
    public static int currentCategoryIndex = 0;
    
    public static boolean useEncouragement = false;
    
    public ChatMessageHandler()
    {
        loadMessages();
    }
    
    public enum MessageCategory
    {
        INSULT,
        ENCOURAGEMENT,
        REACTION
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
    
    public static String getReaction()
    {
        if (!(reactionIndex >= (reactionList.size() - 1)))
        {
            reactionIndex++;
        }
        else
        {
            reactionIndex = 0;
        }
        return reactionList.get(reactionIndex);
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
            JSONArray reactionArray = fullJSONObject.getJSONArray("reaction");
            
            for (int i = 0; i < insultArray.length(); i++)
            {
                insultList.add(insultArray.getString(i));
            }
            
            for (int i = 0; i < encouragementArray.length(); i++)
            {
                encouragementList.add(encouragementArray.getString(i));
            }
            
            for (int i = 0; i < reactionArray.length(); i++)
            {
                reactionList.add(reactionArray.getString(i));
            }
            
            
            Collections.shuffle(insultList);
            Collections.shuffle(encouragementList);
            Collections.shuffle(reactionList);
            
        }
        catch (Exception e)
        {
            System.out.println("ERROR! YOUR \"TTMessages.json\" FILE IS FORMATTED INCORRECTLY. Try using https://jsonlint.com/ to check it. Loading preset messages.");
            e.printStackTrace();
            
            String respath = "/messages/TTMessages.json";
            InputStream in = ChatMessageHandler.class.getResourceAsStream(respath);
            if (in == null)
            {
                try
                {
                    throw new Exception("Resource not found: " + respath);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            
            try
            {
                String fileContents = IOUtils.toString(in, "UTF-8");
                
                JSONObject fullJSONObject = new JSONObject(fileContents);
                JSONArray insultArray = fullJSONObject.getJSONArray("insults");
                JSONArray encouragementArray = fullJSONObject.getJSONArray("encouragement");
                JSONArray reactionArray = fullJSONObject.getJSONArray("reaction");
                
                for (int i = 0; i < insultArray.length(); i++)
                {
                    insultList.add(insultArray.getString(i));
                }
                
                for (int i = 0; i < encouragementArray.length(); i++)
                {
                    encouragementList.add(encouragementArray.getString(i));
                }
                System.out.println(encouragementList);
                
                for (int i = 0; i < reactionArray.length(); i++)
                {
                    reactionList.add(reactionArray.getString(i));
                }
                
                
                Collections.shuffle(insultList);
                Collections.shuffle(encouragementList);
                Collections.shuffle(reactionList);
                
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            
        }
        
    }
    
}