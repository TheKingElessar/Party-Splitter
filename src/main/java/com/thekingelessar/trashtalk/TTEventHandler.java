package com.thekingelessar.trashtalk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.sql.SQLOutput;

public class TTEventHandler
{
    @SideOnly (Side.CLIENT)
    @SubscribeEvent (priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(InputEvent.KeyInputEvent event)
    {
    
        KeyBinding[] keyBindings = TrashTalk.keyBindings;
        
        if (keyBindings[0].isPressed())
        {
            String trashMessage = TrashTalk.chatMessageHandler.getRandomMessage();
            
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            player.sendChatMessage(trashMessage);
        }
        
    }
    
    
}
