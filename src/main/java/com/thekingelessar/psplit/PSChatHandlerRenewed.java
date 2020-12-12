package com.thekingelessar.psplit;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

import static com.thekingelessar.chatcooldownmanager.TickHandler.scheduledCommands;
import static com.thekingelessar.psplit.PartyManager.*;
import static com.thekingelessar.psplit.SelfManager.waitingForInvite;
import static com.thekingelessar.psplit.SelfManager.waitingForKick;
import static net.minecraft.client.Minecraft.getMinecraft;

public class PSChatHandlerRenewed
{
    
    @SubscribeEvent (priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onChatRecievedEvent(ClientChatReceivedEvent chatEvent)
    {
        String fullMessage = chatEvent.message.getUnformattedText();
        
        if (updatingParty)
        {
            if (!fullMessage.contains("You are not currently in a party."))
            {
                PartyManager.updateParty(chatEvent);
            }
            else
            {
                updatingParty = false;
            }
        }
        
        if (fullMessage.contains("You have joined ") || fullMessage.contains("[PSPLIT Update]")) //  || fullMessage.contains("You'll be partying with: ")
        {
            scheduledCommands.add("/pchat [PSPLIT Enabled] " + getMinecraft().thePlayer.getName());
        }
        
        if (fullMessage.contains("[PSPLIT Enabled] "))
        {
            String[] splitMessage = fullMessage.split(" ");
            String hasPlayer = splitMessage[splitMessage.length - 1];
            if (!hasPSPLIT.contains(hasPlayer))
            {
                hasPSPLIT.add(hasPlayer);
            }
        }
        
        // From [MVP+] FrozenFruit: Hi
        if (waitingForConfimation)
        {
            if (fullMessage.contains(otherLeader))
            {
                String[] messageList = fullMessage.split(" ");
                if (messageList[2].equals(otherLeader) && messageList[3].equals("party_creation_finished"))
                {
                    waitingForConfimation = false;
                    currentlySplitting = false;
                    scheduledCommands.add("/duel " + otherLeader);
                }
            }
        }
        
        // Read party information, send the invite, then start waiting for the other member
        // [PSPLIT] Party 1: FrozenFruit, Willis644
        if (fullMessage.contains("[PSPLIT] Party 1: "))
        {
            String[] partyMessage = fullMessage.split(", ");
            partyLeader = partyMessage[0];
            partyLeader = partyLeader.split(" ")[5];
            mainMember = partyMessage[1];
        }
        
        // [MVP+] FrozenFruit: [PSPLIT] Party 2: Willis644, ForgeSmith
        // [MVP+] FrozenFruit: [PSPLIT] Party 2: Willis644
        // ForgeSmith
        if (fullMessage.contains("[PSPLIT] Party 2: "))
        {
            String[] partyMessage = fullMessage.split(", ");
            otherLeader = partyMessage[0];
            otherLeader = otherLeader.split(" ")[5];
            System.out.println(otherLeader);
            otherMember = partyMessage[1];
            
            waitingForKick = true;
        }
        
        // You have been kicked from the party by [MVP+] FrozenFruit
        // You have been kicked from the party by Cyberes
        if (waitingForKick)
        {
            if (fullMessage.contains("You have been kicked from the party by "))
            {
                String[] kickMessage = fullMessage.split(" ");
                getMinecraft().thePlayer.addChatMessage(new ChatComponentText("lol okay " + Arrays.toString(kickMessage)));
                if (kickMessage[kickMessage.length - 1].contains(partyLeader)) // Todo not working
                {
                    waitingForKick = false;
                    scheduledCommands.add("/p " + otherMember);
                }
            }
        }
        
        // Cyberes joined the party.
        if (fullMessage.contains(otherMember + "joined the party."))
        {
            if (waitingForOtherMember)
            {
                scheduledCommands.add(String.format("/tell %s party_creation_finished", partyLeader));
            }
        }
        
        if (partyLeader != null)
        {
            if (fullMessage.contains(partyLeader))
            {
                String[] messageList = fullMessage.split(" ");
                // From [MPV] FrozenFruit: reform_party
                if (messageList[1].contains("MVP") || messageList[1].contains("VIP"))
                {
                    messageList[2] = messageList[2].replace(":", "");
                    if (messageList[2].contains(partyLeader) && messageList[3].contains("reform_party"))
                    {
                        scheduledCommands.add("/pchat [PSPLIT] Reform party");
                        scheduledCommands.add("/p disband");
                        scheduledCommands.add("/p accept " + partyLeader);
                    }
                }
                else
                {
                    messageList[1] = messageList[1].replace(":", "");
                    if (messageList[1].contains(partyLeader) && messageList[2].contains("reform_party"))
                    {
                        scheduledCommands.add("/pchat [PSPLIT] Reform party");
                        scheduledCommands.add("/p disband");
                        scheduledCommands.add("/p accept " + partyLeader);
                    }
                    
                }
            }
        }
        
        if (fullMessage.contains("[PSPLIT] Reform party"))
        {
            waitingForInvite = true;
        }
        
        if (waitingForInvite)
        {
            // FrozenFruit has invited you to join their party!
            if (fullMessage.contains(partyLeader + "has invited you to join their party!"))
            {
                scheduledCommands.add("/p accept " + partyLeader);
            }
        }
    }
    
}
