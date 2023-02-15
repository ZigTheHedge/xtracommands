package com.cwelth.xtracommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class Quake {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("quake")
                .executes( cs -> {
                    ServerPlayer player = cs.getSource().getPlayerOrException();
                    Level level = player.level;

                    for(int x = -1; x < 2; x++)
                        for(int z = -1; z < 2; z++)
                            {
                                BlockPos pos = new BlockPos(player.getX() + x, player.getY() - 1, player.getZ() + z);
                                if(level.getBlockEntity(pos) == null)
                                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            }

                    return 0;
                });
    }
}
