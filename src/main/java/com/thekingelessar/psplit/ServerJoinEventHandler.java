package com.thekingelessar.psplit;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.thekingelessar.psplit.PSplit.currentPlayer;

// Thanks https://github.com/mew/ColourChat/
public class ServerJoinEventHandler
{
    
    public static boolean onHypixel = false;
    public static boolean testingRank = false;
    public static boolean hasChatCooldown = false;
    
    public static boolean waitingForServerJoin = false;
    public static int serverJoinCooldown = 20;
    
    public static void testRank()
    {
        currentPlayer.sendChatMessage("/pchat [PSPLIT Enabled] " + currentPlayer.getName());
        currentPlayer.sendChatMessage("/mypos");
        testingRank = true;
        waitingForServerJoin = false;
    }
    
    @SubscribeEvent
    public void onClientJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent connectedToServerEvent)
    {
        onHypixel = !Minecraft.getMinecraft().isSingleplayer() && connectedToServerEvent.manager.getRemoteAddress().toString().toLowerCase().contains("hypixel.net");
        waitingForServerJoin = true;
    }
    
    @SubscribeEvent
    public void onClientLeaveServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent disconnectionFromServerEvent)
    {
        serverJoinCooldown = 20;
        onHypixel = false;
    }
    
    @SideOnly (Side.CLIENT)
    @SubscribeEvent (priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onWorldJoinEvent(EntityJoinWorldEvent joinWorldEvent)
    {
        if (currentPlayer == null)
        {
            Minecraft mc = Minecraft.getMinecraft();
            currentPlayer = mc.thePlayer;
        }
    }
}
