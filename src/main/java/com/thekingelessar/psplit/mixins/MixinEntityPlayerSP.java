package com.thekingelessar.psplit.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Field;

import static com.thekingelessar.psplit.PSTickHandlerRenewed.*;
import static com.thekingelessar.psplit.ServerJoinEventHandler.hasChatCooldown;
import static com.thekingelessar.psplit.ServerJoinEventHandler.onHypixel;

// Thanks https://github.com/mew/ColourChat/
@Mixin (EntityPlayerSP.class)
public class MixinEntityPlayerSP
{
    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;
    
    @Overwrite
    public void sendChatMessage(String message) // Todo: https://i.kym-cdn.com/entries/icons/original/000/031/671/cover1.jpg
    {
        System.out.println("pchat chat list: " + scheduledChat);
        System.out.println("pchat command list: " + scheduledCommands);
    
        System.out.println("pchat message: " + message);
    
        if (onHypixel && hasChatCooldown)
        {
            // Queue chat and commands
            if (message.charAt(0) == "/".charAt(0))
            {
                if (ticksSinceLastCommand < 11)
                {
                    scheduledCommands.add(message);
                    return;
                }
            }
            else
            {
                if (ticksSinceLastChat < 71)
                {
                    System.out.println("Saved message: " + message);
                    scheduledChat.add(message);
                    return;
                }
            }
        }
        else if (onHypixel)
        {
            // Queue only commands
            if (message.charAt(0) == "/".charAt(0))
            {
                if (ticksSinceLastCommand < 11)
                {
                    scheduledCommands.add(message);
                    return;
                }
            }
        }
        
        if (message.charAt(0) == "/".charAt(0))
        {
            ticksSinceLastCommand = 0;
        }
        else
        {
            ticksSinceLastChat = 0;
        }
        
        C01PacketChatMessage packet = new C01PacketChatMessage(message);
        if (message.length() > 100)
        {
            try
            {
                Field field = C01PacketChatMessage.class.getDeclaredField("message");
                field.setAccessible(true);
                if (message.length() > 256)
                {
                    field.set(packet, message.substring(0, 256));
                }
                else
                {
                    field.set(packet, message);
                }
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
        }
        
        this.sendQueue.addToSendQueue(packet);
        
    }
}