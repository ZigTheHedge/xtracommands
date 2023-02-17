package com.cwelth.xtracommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.lwjgl.system.CallbackI;

public class DeepPit {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("pit")
                .then(Commands.literal("water")
                        .executes( cs -> {
                            ServerPlayer player = cs.getSource().getPlayerOrException();

                            Level level = player.level;

                            int crawlerHeight = level.getMinBuildHeight() + 4;
                            for(int x = -2; x < 3; x++)
                                for(int z = -2; z < 3; z++)
                                    for(int y = 0; y < 5; y++)
                                    {
                                        BlockPos pos = new BlockPos(player.getX() + x, crawlerHeight + y, player.getZ() + z);
                                        level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                                    }

                            player.unRide();
                            player.teleportTo(player.getX(), crawlerHeight, player.getZ());

                            return 0;
                        })
                )
                .then(Commands.literal("lava")
                        .executes( cs -> {
                            ServerPlayer player = cs.getSource().getPlayerOrException();

                            Level level = player.level;

                            int crawlerHeight = level.getMinBuildHeight() + 4;
                            for(int x = -2; x < 3; x++)
                                for(int z = -2; z < 3; z++)
                                    for(int y = 0; y < 5; y++)
                                    {
                                        BlockPos pos = new BlockPos(player.getX() + x, crawlerHeight + y, player.getZ() + z);
                                        level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                                    }

                            player.unRide();
                            player.teleportTo(player.getX(), crawlerHeight, player.getZ());

                            return 0;
                        })
                )
                .executes( cs -> {
                    ServerPlayer player = cs.getSource().getPlayerOrException();

                    Level level = player.level;

                    int genHeight = level.getMinBuildHeight();
                    boolean holeFound = false;
                    int crawlerHeight = genHeight;

                    while(crawlerHeight < player.getY())
                    {
                        BlockPos pos = new BlockPos(player.getX(), crawlerHeight, player.getZ());
                        if(level.getBlockState(pos).isAir())
                        {
                            if(level.getBlockState(pos.above()).isAir())
                            {
                                holeFound = true;
                                break;
                            }
                        }
                        crawlerHeight++;
                    }

                    if(!holeFound)
                    {
                        crawlerHeight = genHeight + 4;
                        for(int x = -2; x < 3; x++)
                            for(int z = -2; z < 3; z++)
                                for(int y = 0; y < 5; y++)
                                {
                                    BlockPos pos = new BlockPos(player.getX() + x, crawlerHeight + y, player.getZ() + z);
                                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                                }
                    }

                    player.unRide();
                    player.teleportTo(player.getX(), crawlerHeight, player.getZ());

                    return 0;
                });
    }
}
