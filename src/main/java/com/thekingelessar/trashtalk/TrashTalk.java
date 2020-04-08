package com.thekingelessar.trashtalk;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod (modid = TrashTalk.MODID, version = TrashTalk.VERSION)
public class TrashTalk
{
    public static final String MODID = "trashtalk";
    public static final String VERSION = "0.1";
    
    public static KeyBinding[] keyBindings;
    public static ChatMessageHandler chatMessageHandler = new ChatMessageHandler();
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    
        keyBindings = new KeyBinding[1];
    
        // instantiate the key bindings
        keyBindings[0] = new KeyBinding("key.sendtrashtalk", Keyboard.KEY_U, "key.categories.TrashTalk");
    
        ClientRegistry.registerKeyBinding(keyBindings[0]);
    
        MinecraftForge.EVENT_BUS.register(new TTEventHandler());
    
    }
}
