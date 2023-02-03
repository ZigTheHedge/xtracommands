package com.cwelth.xtracommands.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MobEffectReverse extends MobEffect {
    public MobEffectReverse()
    {
        super(MobEffectCategory.HARMFUL, 0xffffff);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
