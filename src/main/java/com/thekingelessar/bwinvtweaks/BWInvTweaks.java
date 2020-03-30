package com.thekingelessar.bwinvtweaks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod (modid = BWInvTweaks.MODID, version = BWInvTweaks.VERSION)
public class BWInvTweaks
{
    public static final String MODID = "bwinvtweaks";
    public static final String VERSION = "0.1";
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new EventHandlerBW());
    }
}
