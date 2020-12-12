package com.thekingelessar.psplit;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static com.thekingelessar.psplit.PSplit.currentPlayer;
import static com.thekingelessar.psplit.ServerJoinEventHandler.*;
import static com.thekingelessar.psplit.ServerJoinEventHandler.onHypixel;
import static com.thekingelessar.psplit.ServerJoinEventHandler.waitingForServerJoin;

public class PSTickHandlerRenewed
{
    public static List<String> scheduledCommands = new ArrayList<String>();
    public static List<String> scheduledChat = new ArrayList<String>();
    
    public static int ticksSinceLastCommand = 11;
    public static int ticksSinceLastChat = 71;
    
    @SideOnly (Side.CLIENT)
    @SubscribeEvent (priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(TickEvent.ClientTickEvent tickEvent)
    {
        if (ticksSinceLastChat > 70 && scheduledChat.size() > 0)
        {
            currentPlayer.sendChatMessage(scheduledChat.remove(0));
        }
        
        if (ticksSinceLastCommand > 10 && scheduledCommands.size() > 0)
        {
            currentPlayer.sendChatMessage(scheduledCommands.remove(0));
        }
        
        ticksSinceLastCommand++;
        ticksSinceLastChat++;
    
        if (waitingForServerJoin && onHypixel && serverJoinCooldown == 0)
        {
            testRank();
        }
    
        if(waitingForServerJoin && serverJoinCooldown > 0) {
            serverJoinCooldown--;
        }
    }
}