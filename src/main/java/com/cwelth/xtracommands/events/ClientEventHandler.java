package com.cwelth.xtracommands.events;

import com.cwelth.xtracommands.XtraCommands;
import com.cwelth.xtracommands.effects.MobEffectsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XtraCommands.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
    private static boolean isKeyDownDown = false;
    private static boolean isKeyUpDown = false;
    private static boolean isKeyLeftDown = false;
    private static boolean isKeyRightDown = false;
    private static int rollAngle = 0;

    @SubscribeEvent
    public static void KeyPress(InputEvent.Key event)
    {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return;
        if(player.hasEffect(MobEffectsRegistry.MOB_EFFECT_REVERSE.get()))
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

    @SubscribeEvent
    public static void cameraRoll(ViewportEvent.ComputeCameraAngles event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return;
        if(player.hasEffect(MobEffectsRegistry.MOB_EFFECT_ROLL.get()))
        {
            int level = player.getEffect(MobEffectsRegistry.MOB_EFFECT_ROLL.get()).getAmplifier();
            if(level == 0)
                event.setRoll(180);
            if(level == 1)
                event.setRoll(90);
            if(level == 2)
                event.setRoll(270);
            if(level >= 3) {
                rollAngle += (level - 2);
                if(rollAngle > 360) rollAngle = 0;
                event.setRoll(rollAngle);
            }

        } else
        {
            if(rollAngle != 0) rollAngle = 0;
        }
    }
}
