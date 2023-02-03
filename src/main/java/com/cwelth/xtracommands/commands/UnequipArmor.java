package com.cwelth.xtracommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class UnequipArmor {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("unequiparmor")
                .executes( cs -> {
                    ServerPlayer player = cs.getSource().getPlayerOrException();

                    ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                    ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                    ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
                    ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);

                    player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
                    player.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Blocks.AIR));
                    player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
                    player.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));

                    if(!player.getInventory().add(head)) player.drop(head, true);
                    if(!player.getInventory().add(chest)) player.drop(chest, true);
                    if(!player.getInventory().add(legs)) player.drop(legs, true);
                    if(!player.getInventory().add(feet)) player.drop(feet, true);

                    return 0;
                });
    }
}
