package com.thekingelessar.trashtalk;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ChatMessageHandler
{
    
    public static List<String> messagesList = new ArrayList();
    public static int messageIndex = -1;
    public static boolean useShout = false;
    
    public ChatMessageHandler()
    {
        loadMessages();
    }
    
    public static String getMessage()
    {
        if (!(messageIndex >= (messagesList.size() - 1)))
        {
            messageIndex++;
        }
        else
        {
            messageIndex = 0;
        }
        return messagesList.get(messageIndex);
    }
    
    public void loadMessages()
    {
        JSONParser parser = new JSONParser();
        try
        {
            Object parsedFile;
    
            File f = new File("./mods/TTMessages.json");
            System.out.println("Attempting to read from file: " + f.getCanonicalPath());
    
            if (f.exists() && !f.isDirectory())
            {
                System.out.println("TTMessages.json found.");
                parsedFile = parser.parse(new FileReader(f));
            }
            else
            {
                System.out.println("TTMessages.json not found. Creating one.");
    
                String respath = "/messages/TTMessages.json";
                InputStream in = ChatMessageHandler.class.getResourceAsStream(respath);
                if ( in == null )
                    throw new Exception("Resource not found: " + respath);
    
                String internalFileString = IOUtils.toString(in, StandardCharsets.UTF_8);
                parsedFile = parser.parse(internalFileString);
    
                PrintWriter writer = new PrintWriter("./mods/TTMessages.json", "UTF-8");
                writer.println(internalFileString);
                writer.close();
                System.out.println("TTMessages.json file created.");
            }
    
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) parsedFile;
    
            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray messageList = (JSONArray) jsonObject.get("messages");
    
            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = messageList.iterator();
            while (iterator.hasNext())
            {
                messagesList.add(((Object) iterator.next()).toString());
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR! YOUR \"TTMessages.json\" FILE IS FORMATTED INCORRECTLY. Try using https://jsonlint.com/ to check it.");
            e.printStackTrace();
        }
    
    }
    
}