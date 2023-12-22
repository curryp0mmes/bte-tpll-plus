package com.github.curryp0mmes.tpll;

import com.github.curryp0mmes.tpll.config.ModConfigs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;

public class BtetpllplusClient implements ClientModInitializer {

	public static CoordinateQueue tpllThread = new CoordinateQueue();
	public static ArrayList<String> tpllqueue = new ArrayList<>();

	private static KeyBinding toggleTpllKeybinding;

	ExternalListener externalListener;

	@Override
	public void onInitializeClient() {
		ModConfigs.registerConfigs();

		externalListener = new ExternalListener();

		toggleTpllKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.btetpllplus.toggle", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_J, // The keycode of the key
				"category.btetpllplus" // The translation key of the keybinding's category.
		));


		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleTpllKeybinding.wasPressed()) {
				ModConfigs.AUTOTPLLACTIVATED = !ModConfigs.AUTOTPLLACTIVATED;
				client.inGameHud.setOverlayMessage(Text.of("AutoTpll = " + (ModConfigs.AUTOTPLLACTIVATED ? "ON" : "OFF")), false);
			}
		});
	}

	public static void printStatusBar(String text) {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.inGameHud.setOverlayMessage(Text.of(text), false);
	}
}