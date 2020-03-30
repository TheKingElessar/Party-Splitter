package com.thekingelessar.bwinvtweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class Loadout
{
    
    public EntityPlayer player;
    public InventoryPlayer playerInventory;
    public ItemStack[] oldItems;
    
    public static final List<Object> slot1 = new ArrayList<Object>();
    public static final List<Object> slot2 = new ArrayList<Object>();
    public static final List<Object> slot3 = new ArrayList<Object>();
    public static final List<Object> slot4 = new ArrayList<Object>();
    public static final List<Object> slot5 = new ArrayList<Object>();
    public static final List<Object> slot6 = new ArrayList<Object>();
    public static final List<Object> slot7 = new ArrayList<Object>();
    public static final List<Object> slot8 = new ArrayList<Object>();
    public static final List<Object> slot9 = new ArrayList<Object>();
    
    public Loadout(PlayerInteractEvent e)
    {
        player = e.entityPlayer;
        playerInventory = player.inventory;
        oldItems = playerInventory.mainInventory;
        
        for (int i = 0; i < oldItems.length; i++)
        {
            if (isPotionInvisibility(oldItems[i]))
            {
                playerInventory.setInventorySlotContents(i, new ItemStack(Blocks.tnt, 1));
            }
        }
        
    }
    
    public static void initSlotLists()
    {
        slot1.add(Items.diamond_sword);
        slot1.add(Items.iron_sword);
        slot1.add(Items.stone_sword);
        slot1.add(Items.wooden_sword);
        
        slot2.add(Items.bow);
        slot2.add(Blocks.wool);
        slot2.add(Blocks.end_stone);
        slot2.add(Blocks.planks);
        
        slot3.add(Items.diamond_pickaxe);
        slot3.add(Items.golden_pickaxe);
        slot3.add(Items.iron_pickaxe);
        slot3.add(Items.stone_pickaxe);
        slot3.add(Items.wooden_pickaxe);
        slot3.add(Blocks.tnt);
        slot3.add(Items.fire_charge);
        slot3.add(Blocks.chest);
        slot3.add(Items.spawn_egg);
        
        slot4.add(Items.diamond_axe);
        slot4.add(Items.golden_axe);
        slot4.add(Items.iron_axe);
        slot4.add(Items.stone_axe);
        slot4.add(Items.wooden_axe);
        slot4.add(Blocks.tnt);
        slot4.add(Items.fire_charge);
        slot4.add(Blocks.chest);
        slot4.add(Items.spawn_egg);
        
        slot5.add(Items.shears);
        slot5.add(Blocks.chest);
        slot5.add(Items.spawn_egg);
        
        slot6.add(Items.milk_bucket);
        slot6.add(Blocks.chest);
        slot6.add(Items.spawn_egg);
        
        slot7.add(Items.potionitem); // Be sure to prioritize invisibility. ItemPotion.getEffects
        slot7.add(Items.spawn_egg);
        
        slot8.add(Items.golden_apple); // Note that this is dependent on the existence of ender pearls
        
        slot9.add(Items.ender_pearl);
        slot9.add(Items.golden_apple);
        
    }
    
    public boolean isPotionInvisibility(ItemStack potion)
    {
        System.out.println("Entered test");
        if (potion != null)
        {
            System.out.println("Not nujll");
            if (potion.getItem() instanceof ItemPotion)
            {
                System.out.println("Instance of potion");
                List<PotionEffect> potionEffects = Items.potionitem.getEffects(potion);
                for (PotionEffect effect : potionEffects)
                {
                    if (effect.getPotionID() == 14)
                    {
                        System.out.println("Invisibility potion found!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
