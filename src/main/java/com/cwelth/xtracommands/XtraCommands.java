package com.cwelth.xtracommands;

import com.cwelth.xtracommands.commands.DropHeldItems;
import com.cwelth.xtracommands.commands.UnequipArmor;
import com.cwelth.xtracommands.effects.MobEffectsRegistry;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(XtraCommands.MOD_ID)
public class XtraCommands {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "xtracommands";

    public XtraCommands(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MobEffectsRegistry.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }

    public class ForgeEventHandlers {
        @SubscribeEvent
        public void registerCommands(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            LiteralCommandNode<CommandSourceStack> cmdsXC = dispatcher.register(
                    Commands.literal("xc")
                            .then(DropHeldItems.register(dispatcher))
                            .then(UnequipArmor.register(dispatcher))
            );
            dispatcher.register(Commands.literal(XtraCommands.MOD_ID).redirect(cmdsXC));
        }
    }

}
