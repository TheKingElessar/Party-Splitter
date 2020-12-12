package com.thekingelessar.partysplitter;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.Timer;
import java.util.TimerTask;

public class ServerConnectionHandler
{
    @SubscribeEvent
    public void onClientConnectToServer(FMLNetworkEvent.ClientConnectedToServerEvent e)
    {
        boolean isHypixel = false;
        String[] s = e.manager.getRemoteAddress().toString().split("/");
        if (s.length < 1)
        {
            isHypixel = false;
        }
        else
        {
            if (s[0].endsWith(".hypixel.net"))
            {
                isHypixel = true;
            }
            
            if (isHypixel)
            {
                Timer r = new Timer();
                this.scheduleInitialPSplitMessage(r);
            }
            
        }
    }
    
    public void scheduleInitialPSplitMessage(final Timer r)
    {
        TimerTask t = new TimerTask()
        {
            public void run()
            {
                if (Minecraft.getMinecraft().thePlayer == null)
                {
                    ServerConnectionHandler.this.scheduleInitialPSplitMessage(r);
                }
                else
                {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/pchat [PSPLIT Enabled] " + Minecraft.getMinecraft().thePlayer.getName());
                    r.cancel();
                }
                
            }
        };
        r.schedule(t, 1000L);
    }
    
}
