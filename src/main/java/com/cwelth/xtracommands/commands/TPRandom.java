package com.cwelth.xtracommands.commands;

import com.cwelth.xtracommands.XtraCommands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;

import java.awt.*;

public class TPRandom {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("rtp")
                .then(Commands.argument("min_distance", IntegerArgumentType.integer())
                        .then(Commands.argument("max_distance", IntegerArgumentType.integer())
                            .executes( cs -> {
                                ServerPlayer player = cs.getSource().getPlayerOrException();
                                Level level = player.level;
                                double min_distance = IntegerArgumentType.getInteger(cs, "min_distance");
                                double max_distance = IntegerArgumentType.getInteger(cs, "max_distance");
                                double max_delta = Math.abs(max_distance - min_distance);

                                boolean success = false;
                                while(!success)
                                {
                                    /*
                                    double deltaX = player.getX() - max_delta;
                                    double deltaZ = player.getZ() - max_delta;

                                    deltaX += level.random.nextDouble(max_delta * 2);
                                    if(deltaX < player.getX()) deltaX -= min_distance;
                                    else deltaX += min_distance;

                                    deltaZ += level.random.nextDouble(max_delta * 2);
                                    if(deltaZ < player.getZ()) deltaZ -= min_distance;
                                    else deltaZ += min_distance;
                                    */

                                    double r = min_distance + max_delta * Math.sqrt(level.random.nextDouble());
                                    double theta = level.random.nextDouble() * 2 * Math.PI;

                                    double deltaX = player.getX() + r * Math.cos(theta);
                                    double deltaZ = player.getZ() + r * Math.sin(theta);

                                    //Find empty spot +/- 10*10
                                    double y_adder = player.getY();
                                    double y_delta = 1;
                                    int max_build_height = level.dimensionType().logicalHeight() - 2;
                                    int min_build_height = level.getMinBuildHeight() + 2;
                                    if(y_adder > max_build_height) y_adder = max_build_height - 3;
                                    if(y_adder < min_build_height) y_adder = min_build_height + 1;
                                    double x_adder = -5;
                                    double z_adder = -5;
                                    while(!success)
                                    {
                                        //XtraCommands.LOGGER.info("Checking [" + (deltaX + x_adder) + ", " + y_adder + ", " + (deltaZ + z_adder) + "]...");
                                        if(y_adder + 1 <= max_build_height && y_adder > min_build_height)
                                        {
                                            BlockPos check_below = new BlockPos(deltaX + x_adder, y_adder, deltaZ + z_adder);
                                            if(level.getBlockState(check_below).getBlock() == Blocks.AIR)
                                            {
                                                BlockPos check_above = new BlockPos(deltaX + x_adder, y_adder + 1, deltaZ + z_adder);
                                                if(level.getBlockState(check_above).getBlock() == Blocks.AIR)
                                                {
                                                    for(double deltaY = y_adder; deltaY > min_build_height; deltaY--)
                                                    {
                                                        BlockPos check_non_air = new BlockPos(deltaX + x_adder, deltaY, deltaZ + z_adder);
                                                        if(level.getBlockState(check_non_air).getBlock() != Blocks.AIR)
                                                        {
                                                            y_adder = deltaY + 1;
                                                            success = true;
                                                            break;
                                                        }
                                                    }
                                                    if(success) break;
                                                }
                                            }
                                            x_adder++;
                                            if(x_adder > 4)
                                            {
                                                x_adder = -5;
                                                z_adder++;
                                                if(z_adder > 4)
                                                {
                                                    z_adder = -5;
                                                    y_adder += y_delta;
                                                }
                                            }
                                        } else
                                        {
                                            if(y_delta == -1)
                                            {
                                                break;
                                            }
                                            y_delta = -y_delta;
                                            y_adder = player.getY() - 1;
                                            if(y_adder > max_build_height) y_adder = max_build_height - 3;
                                            if(y_adder < min_build_height) y_adder = min_build_height + 1;
                                        }
                                    }
                                    if(success)
                                    {
                                        player.teleportTo(deltaX + x_adder, y_adder, deltaZ + z_adder);
                                    }
                                }
                                return 0;
                            })));
    }
}
