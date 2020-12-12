package com.thekingelessar.partysplitter;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.thekingelessar.chatcooldownmanager.TickHandler.scheduledCommands;
import static com.thekingelessar.partysplitter.PSplit.PSPLIT_PREFIX;
import static net.minecraft.client.Minecraft.getMinecraft;

public class PartyManager
{
    public static boolean updatingParty = false;
    
    public static boolean waitingListLeader = false;
    public static boolean waitingListMembers = false;
    public static boolean waitingListModerators = false;
    
    public static boolean currentlySplitting = false;
    
    public static boolean waitingForConfimation = false;
    
    static public List<String> hasPSPLIT = new ArrayList<String>();
    
    static public List<String> partyModeratorList = new ArrayList<String>();
    static public List<String> partyMemberList = new ArrayList<String>();
    static public List<String> partyModMemberList = new ArrayList<String>();
    
    static public List<String> party1 = new ArrayList<String>();
    static public List<String> party2 = new ArrayList<String>();
    
    static public String partyLeader = null;
    static public String mainMember = null;
    
    static String otherLeader = null;
    static String otherMember = null;
    
    static boolean waitingForOtherMember = false;
    
    public static void setPartyLeader(String partyLeader)
    {
        PartyManager.partyLeader = partyLeader;
    }
    
    public static String getPartyLeader()
    {
        return partyLeader;
    }
    
    public static void shuffleTeams()
    {
        int partySize = partyModMemberList.size();
        if (partySize != 4)
        {
            getMinecraft().thePlayer.addChatMessage(new ChatComponentText(PSPLIT_PREFIX + String.format("Your party has %s members, not 4 members!", partySize)));
            return;
        }
        
        List<String> partyListCopy = new ArrayList<String>(partyModMemberList);
        
        party1.add(partyLeader);
        partyListCopy.remove(partyLeader);
        
        Random randomizer = new Random();
        otherLeader = partyListCopy.get(randomizer.nextInt(partyListCopy.size()));
        
        for (String playerName : hasPSPLIT)
        {
            if (partyListCopy.contains(playerName))
            {
                otherLeader = playerName;
            }
        }
        
        partyListCopy.remove(otherLeader);
        otherMember = partyListCopy.get(randomizer.nextInt(partyListCopy.size()));
        partyListCopy.remove(otherMember);
        
        party2.add(otherLeader);
        party2.add(otherMember);
        
        mainMember = partyListCopy.get(randomizer.nextInt(partyListCopy.size()));
        party1.add(mainMember);
        
        scheduledCommands.add(String.format("/pchat [PSPLIT] Party 1: %s, %s", partyLeader, mainMember));
        scheduledCommands.add(String.format("/pchat [PSPLIT] Party 2: %s, %s", otherLeader, otherMember));
        scheduledCommands.add(String.format("/pchat [PSPLIT] Once your party is formed, message me: /tell %s party_creation_finished", partyLeader));
        
        for (String player : party2)
        {
            System.out.println("removing " + player);
            scheduledCommands.add("/p remove " + player);
        }
        
        waitingForConfimation = true;
        
    }
    
    public static void updateParty(ClientChatReceivedEvent chatEvent)
    {
        
        String fullMessage = chatEvent.message.getUnformattedText();
        
        if (waitingListLeader)
        {
            partyModeratorList.clear();
            if (fullMessage.contains("Party Leader: "))
            {
                String[] splitMessage = fullMessage.split(" ");
                
                String partyLeaderName;
                if (splitMessage[2].contains("MVP") || splitMessage[2].contains("VIP"))
                {
                    partyLeaderName = splitMessage[3];
                }
                else
                {
                    partyLeaderName = splitMessage[2];
                }
                setPartyLeader(partyLeaderName);
                
                waitingListMembers = true;
                waitingListModerators = true;
                waitingListLeader = false;
                
                chatEvent.setResult(Event.Result.ALLOW);
            }
        }
        
        if (waitingListModerators)
        {
            if (fullMessage.contains("Party Moderators: "))
            {
                String[] splitMessage = fullMessage.split(" ");
                partyModeratorList.clear();
                
                for (int i = 0; i < splitMessage.length; i++)
                {
                    if (i < 2)
                    {
                        continue;
                    }
                    
                    if (splitMessage[i].length() == 1)
                    {
                        continue;
                    }
                    
                    if (splitMessage[i].contains("MVP") || splitMessage[i].contains("VIP"))
                    {
                        continue;
                    }
                    
                    partyModeratorList.add(splitMessage[i]);
                }
                
                waitingListModerators = false;
                
                chatEvent.setResult(Event.Result.ALLOW);
                
            }
        }
        
        if (waitingListMembers)
        {
            // Party Leader: [MVP+] FrozenFruit ?
            // Party Moderators: Cyberes ?
            // Party Members: Cyberes ? Mokabu456 ?
            
            if (fullMessage.contains("Party Members: "))
            {
                String[] splitMessage = fullMessage.split(" ");
                partyMemberList.clear();
                
                for (int i = 0; i < splitMessage.length; i++)
                {
                    if (i < 2)
                    {
                        continue;
                    }
                    
                    if (splitMessage[i].length() == 1)
                    {
                        continue;
                    }
                    
                    if (splitMessage[i].contains("MVP") || splitMessage[i].contains("VIP"))
                    {
                        continue;
                    }
                    
                    partyMemberList.add(splitMessage[i]);
                }
                
                waitingListMembers = false;
                waitingListModerators = false;
                
                chatEvent.setResult(Event.Result.ALLOW);
                
            }
        }
        
        if (updatingParty && !waitingListLeader && !waitingListModerators && !waitingListMembers)
        {
            partyModMemberList.clear();
            partyModMemberList.add(getPartyLeader());
            partyModMemberList.addAll(partyModeratorList);
            partyModMemberList.addAll(partyMemberList);
            
            getMinecraft().thePlayer.addChatMessage(new ChatComponentText(PSPLIT_PREFIX + String.format("%s's party: " + partyModMemberList, getPartyLeader())));
            
            updatingParty = false;
            
            if (currentlySplitting)
            {
                shuffleTeams();
            }
        }
        
    }
    
}
