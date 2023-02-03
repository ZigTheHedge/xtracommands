package com.cwelth.xtracommands.effects;

import com.cwelth.xtracommands.XtraCommands;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobEffectsRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, XtraCommands.MOD_ID);

    public static void init()
    {
        MOB_EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static final RegistryObject<MobEffect> MOB_EFFECT_REVERSE = MOB_EFFECTS.register("reverse", MobEffectReverse::new);

}
