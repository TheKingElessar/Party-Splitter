package com.thekingelessar.psplit;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.thekingelessar.psplit.PSplit.currentPlayer;
import static com.thekingelessar.psplit.PSplit.psplitPrefix;
import static com.thekingelessar.psplit.PartyManager.*;

public class CommandPartySplit extends CommandBase
{
    
    @Override
    public String getCommandName()
    {
        return "psplit";
    }
    
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
    
    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return null;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }
    
    @Override
    public void processCommand(ICommandSender ics, String[] args) throws CommandException
    {
        if (args == null || args.length == 0)
        {
            try
            {
                sendMessageToServer();
                currentlySplitting = true;
                return;
            }
            catch (Exception e)
            {
                System.out.println("[PSPLIT] " + "Tried to send a message to the server, but can't because this isn't a client. This mod is made for clients.");
                return;
            }
        }
        
        if ("update".equals(args[0]))
        {
            try
            {
                sendMessageToServer();
                return;
            }
            catch (Exception e)
            {
                System.out.println("[PSPLIT] " + "Tried to send a message to the server, but can't because this isn't a client. This mod is made for clients.");
                return;
            }
        }
        else if ("go".equals(args[0]))
        {
            if (currentlySplitting)
            {
                currentPlayer.addChatMessage(new ChatComponentText(psplitPrefix + "You're already splitting the party. To reset, use " + ChatFormatting.YELLOW + "/psplit reset"));
            }
            else if (getPartyLeader().equals(currentPlayer.getName()))
            {
                currentlySplitting = true;
                shuffleTeams();
            }
            else
            {
                currentPlayer.addChatMessage(new ChatComponentText(psplitPrefix + "You must be the party leader to split the party."));
            }
            return;
        }
        else if ("reform".equals(args[0]))
        {
            if (otherLeader != null)
            {
                currentPlayer.sendChatMessage(String.format("/tell %s reform_party", otherLeader));
                currentPlayer.sendChatMessage(String.format("/p %s", otherLeader));
                currentPlayer.sendChatMessage(String.format("/p %s", otherMember));
            }
            return;
        }
    }
    
    @SideOnly (Side.CLIENT)
    public void sendMessageToServer()
    {
        currentPlayer.sendChatMessage("/p list");
        updatingParty = true;
        waitingListLeader = true;
    }
    
}
