package com.thekingelessar.bwinvtweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerBW
{
    @SubscribeEvent
    public void onInventoryChange(PlayerInteractEvent e)
    {
        Loadout loadout = new Loadout(e);
    }
}
