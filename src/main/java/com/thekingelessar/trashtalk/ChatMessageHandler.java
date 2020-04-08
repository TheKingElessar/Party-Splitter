package com.thekingelessar.trashtalk;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ChatMessageHandler
{
    
    public static List<String> messagesList = new ArrayList();
    public static int messageIndex = -1;
    
    public ChatMessageHandler() {
        loadMessages();
    }
    
    public static String getRandomMessage() {
        if(!(messageIndex >= (messagesList.size() - 1))) {
            messageIndex++;
        } else {
            messageIndex = 0;
        }
        return messagesList.get(messageIndex); // todo: instead of random messages, cyle through them in order to ensur
    }
    
    public static void loadMessages() {
        JSONParser parser = new JSONParser();
        try {
            Object obj;
        
            File f = new File("../TTMessages.json");
        
            if(f.exists() && !f.isDirectory()) {
                obj = parser.parse(new FileReader(f));
            } else
            {
                File internalFile = new File("TTMessages.json");
                System.out.println("Attempting to read from file in: "+ f.getCanonicalPath());
                obj = parser.parse(new FileReader(internalFile));
            }
        
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;
        
            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray messageList = (JSONArray) jsonObject.get("messages");
        
            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = messageList.iterator();
            while (iterator.hasNext()) {
                messagesList.add(((Object) iterator.next()).toString());
            }
        } catch (Exception e) {
            System.out.println("ERROR! YOUR \"TTMessages.json\" FILE IS FORMATTED INCORRECTLY. Try using https://jsonlint.com/ to check it.");
            e.printStackTrace();
        }
    
    }
}
