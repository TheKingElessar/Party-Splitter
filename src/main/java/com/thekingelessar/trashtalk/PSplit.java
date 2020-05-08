package com.thekingelessar.trashtalk;

import net.minecraft.client.entity.EntityPlayerSP;
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
    public static final String VERSION = "0.1";
    
    public static boolean currentlySplitting = false;
    public static List<String> playerList = new ArrayList<String>();
    public static EntityPlayerSP currentPlayer = null;
    
    public static String waitingFor = null;
    public static String otherLeader;
    public static String mainLeader;
    
    public static boolean waitingForDuel = false;
    
    public static List<String> priorityPlayers = new ArrayList<String>();
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        
        MinecraftForge.EVENT_BUS.register(new PSEventHandler());
        ClientCommandHandler.instance.registerCommand(new CommandPartySplit());
        
    }
    
}
