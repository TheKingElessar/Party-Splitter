package com.thekingelessar.trashtalk;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.input.Keyboard;

@Mod (modid = TrashTalk.MODID, version = TrashTalk.VERSION)
public class TrashTalk
{
    public static final String MODID = "trashtalk";
    public static final String VERSION = "1.3";
    
    public static KeyBinding[] keyBindings;
    public static ChatMessageHandler chatMessageHandler = new ChatMessageHandler();
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        
        keyBindings = new KeyBinding[4];
        
        // instantiate the key bindings
        keyBindings[0] = new KeyBinding("Send Insult", Keyboard.KEY_T, "Trash Talk");
        keyBindings[1] = new KeyBinding("Cycle message category", Keyboard.KEY_G, "Trash Talk");
        keyBindings[2] = new KeyBinding("Toggle /shout", Keyboard.KEY_B, "Trash Talk");
        keyBindings[3] = new KeyBinding("Reload Messages", Keyboard.KEY_P, "Trash Talk");
        
        ClientRegistry.registerKeyBinding(keyBindings[0]);
        ClientRegistry.registerKeyBinding(keyBindings[1]);
        ClientRegistry.registerKeyBinding(keyBindings[2]);
        ClientRegistry.registerKeyBinding(keyBindings[3]);
        
        MinecraftForge.EVENT_BUS.register(new TTEventHandler());
        
    }
}
