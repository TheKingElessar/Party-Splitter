package com.thekingelessar.trashtalk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.thekingelessar.trashtalk.ChatMessageHandler.useShout;
import static com.thekingelessar.trashtalk.TrashTalk.chatMessageHandler;

public class TTEventHandler
{
    @SideOnly (Side.CLIENT)
    @SubscribeEvent (priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(InputEvent.KeyInputEvent event)
    {
        
        KeyBinding[] keyBindings = TrashTalk.keyBindings;
        
        if (keyBindings[0].isPressed())
        {
            String trashMessage = chatMessageHandler.getMessage();
            
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            
            if (useShout)
            {
                player.sendChatMessage("/shout " + trashMessage);
            }
            else
            {
                player.sendChatMessage(trashMessage);
            }
        }
        
        if (keyBindings[1].isPressed())
        {
            System.out.println("Reloading messages");
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.BOLD.toString() + "Trash Talk\n" + EnumChatFormatting.RESET.toString() + "Reloading messages, noob. Maybe if you were good at the game you wouldn't need to do this, huh?\n"));
            chatMessageHandler.loadMessages();
        }
        
        if (keyBindings[2].isPressed())
        {
            useShout = !useShout;
            
            String shoutColor;
            if (useShout)
            {
                shoutColor = EnumChatFormatting.GREEN.toString();
            }
            else
            {
                shoutColor = EnumChatFormatting.RED.toString();
            }
            
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.BOLD.toString() + "Trash Talk\n" + EnumChatFormatting.RESET.toString() + "Use /shout set to: " + shoutColor + useShout + "\n"));
        }
        
    }
}
