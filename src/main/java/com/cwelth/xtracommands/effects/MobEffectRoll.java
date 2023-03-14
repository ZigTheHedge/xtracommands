package com.cwelth.xtracommands.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MobEffectRoll  extends MobEffect {
    protected MobEffectRoll() {
        super(MobEffectCategory.HARMFUL, 0x000000);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
