package me.cyber;

import me.cyber.commands.DevCommand;
import me.cyber.model.TPlayer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Limbo implements ModInitializer {
	public static final String MOD_ID = "limbo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftClient mc;
	public static TPlayer currentGameMover;

	@Override
	public void onInitialize() {
		currentGameMover = TPlayer.NONE;
		mc = MinecraftClient.getInstance();
		LOGGER.info("Hello Fabric world!");
		DevCommand.init();
	}
}