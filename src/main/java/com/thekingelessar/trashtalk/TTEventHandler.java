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

import static com.thekingelessar.trashtalk.ChatMessageHandler.*;
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
            
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            
            switch (currentCategoryIndex)
            {
                case 0:
                    player.sendChatMessage(useShout + getInsult());
                    break;
                case 1:
                    player.sendChatMessage(useShout + getEncouragement());
                    break;
                case 2:
                    player.sendChatMessage(useShout + getReaction());
                    break;
            }
            
        }
        
        if (keyBindings[1].isPressed()) // cycle category
        {
            //     useEncouragement = !useEncouragement;
            
            currentCategoryIndex += 1;
            if (currentCategoryIndex >= 3)
            {
                currentCategoryIndex = 0;
            }
            
            String messageColor;
            String categoryMessage;
            
            switch (currentCategoryIndex)
            {
                case 0:
                    messageColor = EnumChatFormatting.RED.toString();
                    categoryMessage = "straight fire";
                    break;
                
                case 1:
                    messageColor = EnumChatFormatting.GREEN.toString();
                    categoryMessage = "straight love and friendship";
                    break;
                
                case 2:
                    messageColor = EnumChatFormatting.BLUE.toString();
                    categoryMessage = "straight reactions";
                    break;
                
                default:
                    messageColor = EnumChatFormatting.WHITE.toString();
                    categoryMessage = "Wait something went wrong I have no idea";
                    break;
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.BOLD.toString() + "Trash Talk\n" + EnumChatFormatting.RESET.toString() + "You are currently spitting: " + messageColor + categoryMessage));
        }
        
        if (keyBindings[2].isPressed())
        {
            if (useShout.isEmpty())
            {
                useShout = "/shout ";
            }
            else
            {
                useShout = "";
            }
            
            String shoutColor;
            if (!useShout.isEmpty())
            {
                shoutColor = EnumChatFormatting.GREEN.toString();
            }
            else
            {
                shoutColor = EnumChatFormatting.RED.toString();
            }
            
            boolean shoutEnabled = !useShout.isEmpty();
            
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.BOLD.toString() + "Trash Talk\n" + EnumChatFormatting.RESET.toString() + "Use /shout set to: " + shoutColor + shoutEnabled));
        }
        
        if (keyBindings[3].isPressed())
        {
            System.out.println("Reloading messages");
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.BOLD.toString() + "Trash Talk\n" + EnumChatFormatting.RESET.toString() + "Reloading messages, noob. Maybe if you were good at the game you wouldn't need to do this, huh?\n"));
            chatMessageHandler.loadMessages();
        }
        
    }
}
