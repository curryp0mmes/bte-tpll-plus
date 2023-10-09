package com.github.curryp0mmes.tpll;

import com.github.curryp0mmes.tpll.config.ModConfigs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
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
	public static Clipboard sysClip;

	@Override
	public void onInitializeClient() {
		ModConfigs.registerConfigs();

		//TODO Fix Headless Exception when ran in regular MC
		System.setProperty("java.awt.headless", "false");
		sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		ClipBoardListener cbl = new ClipBoardListener();
		cbl.start();

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
		//ModInputHandler.registerKeyBinds();
		//MinecraftForge.EVENT_BUS.register(new ModInputHandler());

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}