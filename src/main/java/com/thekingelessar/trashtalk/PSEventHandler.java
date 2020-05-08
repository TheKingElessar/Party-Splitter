package com.thekingelessar.trashtalk;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

import static com.thekingelessar.trashtalk.PSplit.*;

public class PSEventHandler
{
    
    @SubscribeEvent (priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(ClientChatReceivedEvent chatEvent)
    {
        String fullMessage = chatEvent.message.getUnformattedText().replaceAll("\u00a7.", "");
        
        String chatSender = null;
        String chatMessage = null;
        
        if (fullMessage.contains(": "))
        {
            List<String> list = Arrays.asList(fullMessage.split(": "));
            
            if (list.get(0).toLowerCase().contains("] "))
            {
                String[] testArray = list.get(0).split("] ");
                if (testArray.length == 3)
                {
                    list.set(0, testArray[2]);
                }
                else
                {
                    list.set(0, testArray[1]);
                }
                chatSender = list.get(0);
                chatMessage = list.get(1);
                
            }
        }
        
        String partyJoinSample = "You joined ";
        
        if (fullMessage.toLowerCase().contains(partyJoinSample.toLowerCase()))
        {
            currentPlayer.sendChatMessage("/pchat Never split the party.");
        }
        
        if (chatMessage.toLowerCase().contains("Never split the party.".toLowerCase()) && chatSender != currentPlayer.getName())
        {
            priorityPlayers.add(chatSender);
        }
        
        String partyListSample = "Party members ";
        
        if (currentlySplitting)
        {
            if (fullMessage.toLowerCase().contains(partyListSample.toLowerCase()))
            {
                if (fullMessage.toLowerCase().contains((partyListSample + "(4):").toLowerCase()))
                {
                    String trimmedMessage = fullMessage.replace("Party members (4): ", "");
                    trimmedMessage = trimmedMessage.replace("[MVP++] ", "");
                    trimmedMessage = trimmedMessage.replace("[MVP+] ", "");
                    trimmedMessage = trimmedMessage.replace("[VIP+] ", "");
                    trimmedMessage = trimmedMessage.replace("[VIP] ", "");
                    playerList = Arrays.asList(trimmedMessage.trim().split("\\s.\\s"));
                    
                    List<String> firstParty = playerList;
                    List<String> secondParty = new ArrayList<String>();
                    
                    Random randomizer = new Random();
                    String random = firstParty.get(randomizer.nextInt(firstParty.size()));
                    secondParty.add(random);
                    firstParty.remove(random);
                    
                    random = firstParty.get(randomizer.nextInt(firstParty.size()));
                    secondParty.add(random);
                    firstParty.remove(random);
                    
                    boolean firstPartyHasMe = false;
                    
                    for (String player : firstParty)
                    {
                        if (player.equals(currentPlayer.getName()))
                        {
                            firstPartyHasMe = true;
                        }
                    }
                    
                    List<String> otherParty;
                    List<String> myParty;
                    
                    if (firstPartyHasMe)
                    {
                        myParty = firstParty;
                        otherParty = secondParty;
                    }
                    else
                    {
                        myParty = secondParty;
                        otherParty = firstParty;
                    }
                    
                    if (!myParty.get(0).equals(currentPlayer.getName()))
                    {
                        Collections.swap(myParty, 0, 1);
                    }
                    
                    if (priorityPlayers.contains(otherParty.get(1)))
                    {
                        Collections.swap(otherParty, 0, 1);
                    }
                    
                    currentPlayer.sendChatMessage("[You Have Split the Party] " + myParty.get(0) + " " + myParty.get(1) + " " + otherParty.get(0) + " " + otherParty.get(1));
                    currentPlayer.sendChatMessage("/p remove " + myParty.get(1));
                    currentPlayer.sendChatMessage("/p promote " + otherParty.get(0));
                    currentPlayer.sendChatMessage("/p leave");
                    currentPlayer.sendChatMessage("/p " + myParty.get(1));
                    
                    waitingFor = myParty.get(1);
                    otherLeader = otherParty.get(0);
                    mainLeader = currentPlayer.getName();
                    currentlySplitting = false;
                }
                else
                {
                    currentPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.BOLD.toString() + "[Never Split the Party] " + EnumChatFormatting.RESET.toString() + "Your party is not the correct size for this. It must have 4 players."));
                }
            }
        }
        
        if (waitingFor != null)
        {
            
            String waitingForChat = waitingFor + " joined the party!";
            String waitingForErrorChat = "The party invite to " + waitingFor + " has expired.";
            String partyDisbanded = "The party was disbanded because all invites have expired and all members have left.";
            
            if (fullMessage.toLowerCase().contains(waitingForChat.toLowerCase()))
            {
                currentPlayer.sendChatMessage("/duel " + otherLeader);
                waitingFor = null;
            }
            else if (fullMessage.toLowerCase().contains(waitingForErrorChat.toLowerCase()) || fullMessage.toLowerCase().contains(partyDisbanded.toLowerCase()))
            {
                currentPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.BOLD.toString() + "[Never Split the Party] " + EnumChatFormatting.RESET.toString() + "Let's try this again."));
                currentPlayer.sendChatMessage("/p " + waitingFor);
            }
        }
        
        if (chatMessage.toLowerCase().contains("[You Have Split the Party] ".toLowerCase()) && currentPlayer == null)
        {
            List<String> splitMessage = Arrays.asList(chatMessage.trim().split("\\s+"));
            splitMessage.remove(0);
            mainLeader = splitMessage.get(0);
            waitingForDuel = true;
        }
        
        String duelSample = " has invited you to SkyWars Duels!";
        
        if (waitingForDuel)
        {
            if (fullMessage.toLowerCase().contains(duelSample.toLowerCase()))
            {
                if (fullMessage.toLowerCase().contains(otherLeader.toLowerCase()))
                {
                    currentPlayer.sendChatMessage("/duel accept " + otherLeader);
                }
            }
        }
        
        
    }
    
}
// todo: prioritize players that have the mod as party leaders