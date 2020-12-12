package com.thekingelessar.psplit;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.ArrayList;
import java.util.List;

@Mod (modid = PSplit.MODID, version = PSplit.VERSION)
public class PSplit
{
    public static final String MODID = "psplit";
    public static final String VERSION = "1.0";
    
    public static boolean modEnabled = false; // Todo: command to enable/disable the mod
    
    public static EntityPlayerSP currentPlayer = null;
    
    public static final String psplitPrefix = EnumChatFormatting.DARK_PURPLE + "[PSPLIT] " + EnumChatFormatting.RESET;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        System.out.println("[PSPLIT] " + "Party Splitter logging enabled");
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        
        MinecraftForge.EVENT_BUS.register(new PSChatHandlerRenewed());
        MinecraftForge.EVENT_BUS.register(new PSTickHandlerRenewed());
        MinecraftForge.EVENT_BUS.register(new ServerJoinEventHandler());
        
        ClientCommandHandler.instance.registerCommand(new CommandPartySplit());
    }
}
