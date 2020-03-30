package com.thekingelessar.bwinvtweaks;

import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
    @SubscribeEvent
    public void onPlayerPickupXP(PlayerPickupXpEvent e) {
        e.entityPlayer.setHealth(0);
    }
}
