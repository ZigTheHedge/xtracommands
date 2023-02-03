package com.cwelth.xtracommands.events;

import com.cwelth.xtracommands.XtraCommands;
import com.cwelth.xtracommands.effects.MobEffectReverse;
import com.cwelth.xtracommands.effects.MobEffectsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XtraCommands.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
    private static boolean isKeyDownDown = false;
    private static boolean isKeyUpDown = false;
    private static boolean isKeyLeftDown = false;
    private static boolean isKeyRightDown = false;

    @SubscribeEvent
    public static void KeyPress(InputEvent.KeyInputEvent event)
    {
        if(Minecraft.getInstance().player.hasEffect(MobEffectsRegistry.MOB_EFFECT_REVERSE.get()))
        {
            if(!isKeyDownDown && Minecraft.getInstance().options.keyUp.isDown())
            {
                isKeyUpDown = true;
                Minecraft.getInstance().options.keyUp.setDown(false);
                Minecraft.getInstance().options.keyDown.setDown(true);
            } else if(!isKeyUpDown && Minecraft.getInstance().options.keyDown.isDown())
            {
                isKeyDownDown = true;
                Minecraft.getInstance().options.keyDown.setDown(false);
                Minecraft.getInstance().options.keyUp.setDown(true);
            } else if(isKeyUpDown && !Minecraft.getInstance().options.keyUp.isDown())
            {
                isKeyUpDown = false;
                Minecraft.getInstance().options.keyDown.setDown(false);
            } else if(isKeyDownDown && !Minecraft.getInstance().options.keyDown.isDown())
            {
                isKeyDownDown = false;
                Minecraft.getInstance().options.keyUp.setDown(false);
            }

            if(!isKeyRightDown && Minecraft.getInstance().options.keyLeft.isDown())
            {
                isKeyLeftDown = true;
                Minecraft.getInstance().options.keyLeft.setDown(false);
                Minecraft.getInstance().options.keyRight.setDown(true);
            } else if(!isKeyLeftDown && Minecraft.getInstance().options.keyRight.isDown())
            {
                isKeyRightDown = true;
                Minecraft.getInstance().options.keyRight.setDown(false);
                Minecraft.getInstance().options.keyLeft.setDown(true);
            } else if(isKeyLeftDown && !Minecraft.getInstance().options.keyLeft.isDown())
            {
                isKeyLeftDown = false;
                Minecraft.getInstance().options.keyRight.setDown(false);
            } else if(isKeyRightDown && !Minecraft.getInstance().options.keyRight.isDown())
            {
                isKeyRightDown = false;
                Minecraft.getInstance().options.keyLeft.setDown(false);
            }
        } else
        {
            if(isKeyDownDown)
            {
                isKeyDownDown = false;
                Minecraft.getInstance().options.keyUp.setDown(false);
            }
            if(isKeyUpDown)
            {
                isKeyUpDown = false;
                Minecraft.getInstance().options.keyDown.setDown(false);
            }
            if(isKeyLeftDown)
            {
                isKeyLeftDown = false;
                Minecraft.getInstance().options.keyRight.setDown(false);
            }
            if(isKeyRightDown)
            {
                isKeyRightDown = false;
                Minecraft.getInstance().options.keyLeft.setDown(false);
            }
        }
    }
}
