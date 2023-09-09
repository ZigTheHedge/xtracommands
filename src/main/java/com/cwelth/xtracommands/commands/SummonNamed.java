package com.cwelth.xtracommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SummonNamed {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("summonnamed")
                .then(Commands.argument("entity", EntitySummonArgument.id()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                    .then(Commands.argument("name", StringArgumentType.string())
                        .executes( cs -> {
                            ExecuteSummon(cs.getSource(), EntitySummonArgument.getSummonableEntity(cs, "entity"), StringArgumentType.getString(cs, "name"), new CompoundTag());
                            return 0;
                        })
                        .then(Commands.argument("nbt", CompoundTagArgument.compoundTag())
                                .executes( cs -> {
                                    ExecuteSummon(cs.getSource(), EntitySummonArgument.getSummonableEntity(cs, "entity"), StringArgumentType.getString(cs, "name"), CompoundTagArgument.getCompoundTag(cs, "nbt"));
                                    return 0;
                                })
                        )));
    }

    public static void ExecuteSummon(CommandSourceStack cs, ResourceLocation entity, String name, CompoundTag nbt) throws CommandSyntaxException {
        CompoundTag compoundtag = nbt.copy();
        ServerLevel level = cs.getLevel();
        compoundtag.putString("id", entity.toString());
        Entity newEntity = EntityType.loadEntityRecursive(compoundtag, level, (ent) -> {
            ent.moveTo(cs.getPlayer().position());
            return ent;
        });
        /*
        RegistryObject<EntityType> mob = RegistryObject.create(entity, ForgeRegistries.ENTITY_TYPES);
        Entity newEntity = mob.get().create(cs.getLevel());
        newEntity.moveTo(cs.getPlayer().position());
        */
        if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn((Mob)newEntity, level, (float)newEntity.getX(), (float)newEntity.getY(), (float)newEntity.getZ(), null, MobSpawnType.COMMAND))
            ((Mob)newEntity).finalizeSpawn(level, level.getCurrentDifficultyAt(newEntity.blockPosition()), MobSpawnType.COMMAND, (SpawnGroupData)null, (CompoundTag)null);

        newEntity.setCustomName(Component.literal(name));
        newEntity.setCustomNameVisible(true);
        cs.getLevel().addFreshEntity(newEntity);
    }
}
