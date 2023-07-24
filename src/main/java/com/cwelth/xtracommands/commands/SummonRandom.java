package com.cwelth.xtracommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SummonRandom {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("summonrandom")
                .then(Commands.argument("entityList", StringArgumentType.string())
                        .executes( cs -> {
                            ExecuteSummon(cs.getSource(), StringArgumentType.getString(cs, "entityList"), 1);
                            return 0;
                        })
                .then(Commands.argument("count", IntegerArgumentType.integer(1))
                    .executes( cs -> {
                        ExecuteSummon(cs.getSource(), StringArgumentType.getString(cs, "entityList"), IntegerArgumentType.getInteger(cs, "count"));
                        return 0;
                    })
                ));
    }

    public static void ExecuteSummon(CommandSourceStack cs, String entityList, int count) throws CommandSyntaxException {
        ArrayList<String> variants = new ArrayList<>(Arrays.asList(entityList.split("\\|")));
        int rndLimit = variants.size();
        for (int i = 0; i < count; i++)
        {
            String summonCommand = "/summon " + variants.get((int)(Math.random() * rndLimit));
            ServerPlayer player = cs.getPlayerOrException();
            player.getServer().getCommands().performCommand(cs, summonCommand);
        }
    }
}
