package com.cwelth.xtracommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class DropHeldItems {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("drophelditems")
                .executes( cs -> {
                    ServerPlayer player = cs.getSource().getPlayerOrException();

                    ItemStack leftHand = player.getItemInHand(InteractionHand.MAIN_HAND);
                    ItemStack rightHand = player.getItemInHand(InteractionHand.OFF_HAND);
                    player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Blocks.AIR));
                    player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Blocks.AIR));
                    player.drop(leftHand, true);
                    player.drop(rightHand, true);
                    return 0;
                });
    }
}
