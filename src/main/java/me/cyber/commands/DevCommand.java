package me.cyber.commands;

import me.cyber.gui.tictactoe.Board;
import me.cyber.model.TMode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class DevCommand {

    public static void init() {
        reg();
    }

    private static void reg() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("foo_client")
                .executes(context -> {

                    //context.getSource().sendFeedback(Text.literal("Opening"));

                    MinecraftClient client = context.getSource().getClient();
                    client.send(() ->{
                        client.setScreen(new Board(TMode.IMPOSSIBLE));
                    });
                    return 1;


                })));
    }

}
