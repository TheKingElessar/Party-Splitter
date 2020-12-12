package com.thekingelessar.psplit;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

import static com.thekingelessar.psplit.PSplit.currentPlayer;
import static com.thekingelessar.psplit.PartyManager.*;
import static com.thekingelessar.psplit.SelfManager.waitingForInvite;
import static com.thekingelessar.psplit.SelfManager.waitingForKick;
import static com.thekingelessar.psplit.ServerJoinEventHandler.hasChatCooldown;
import static com.thekingelessar.psplit.ServerJoinEventHandler.testingRank;

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
            } else {
                updatingParty = false;
            }
        }
        
        if (fullMessage.contains("You have joined ") || fullMessage.contains("[PSPLIT Update]")) //  || fullMessage.contains("You'll be partying with: ")
        {
            currentPlayer.sendChatMessage("/pchat [PSPLIT Enabled] " + currentPlayer.getName());
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
                    currentPlayer.sendChatMessage("/duel " + otherLeader);
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
                currentPlayer.addChatMessage(new ChatComponentText("lol okay " + Arrays.toString(kickMessage)));
                if (kickMessage[kickMessage.length - 1].contains(partyLeader)) // Todo not working
                {
                    waitingForKick = false;
                    currentPlayer.sendChatMessage("/p " + otherMember);
                }
            }
        }
        
        // Cyberes joined the party.
        if (fullMessage.contains(otherMember + "joined the party."))
        {
            if (waitingForOtherMember)
            {
                currentPlayer.sendChatMessage(String.format("/tell %s party_creation_finished", partyLeader));
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
                        currentPlayer.sendChatMessage("/pchat [PSPLIT] Reform party");
                        currentPlayer.sendChatMessage("/p disband");
                        currentPlayer.sendChatMessage("/p accept " + partyLeader);
                    }
                }
                else
                {
                    messageList[1] = messageList[1].replace(":", "");
                    if (messageList[1].contains(partyLeader) && messageList[2].contains("reform_party"))
                    {
                        currentPlayer.sendChatMessage("/pchat [PSPLIT] Reform party");
                        currentPlayer.sendChatMessage("/p disband");
                        currentPlayer.sendChatMessage("/p accept " + partyLeader);
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
                currentPlayer.sendChatMessage("/p accept " + partyLeader);
            }
        }
        
        if (testingRank)
        {
            if (fullMessage.contains("You must be vip or higher to use this command!"))
            {
                testingRank = false;
                hasChatCooldown = true;
                System.out.println("Has chat cooldown " + hasChatCooldown);
            }
        }
        
    }
    
}
