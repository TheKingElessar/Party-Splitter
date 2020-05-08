package com.thekingelessar.trashtalk;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        MinecraftForge.EVENT_BUS.register(this);
        try
        {
            sendMessageToServer();
        }
        catch (Exception e)
        {
            System.out.println("Tried to send a message to the server, but can't because this isn't a client. This mod is made for clients.");
        }
    }
    
    // When you initiate the party split, it makes a note that it’s in the process of splitting and then asks for the party list. Whenever a chat message is received, it checks if it’s the party list, and if so it updates the internal list of players. Then it checks if it’s in the process of splitting; if it is, it goes ahead and does stuff.
    @SideOnly (Side.CLIENT)
    public void sendMessageToServer()
    {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        PSplit.currentPlayer = player;
        player.sendChatMessage("/p list");
        PSplit.currentlySplitting = true;
    }
    
}
