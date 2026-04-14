package com.example.donutsmp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ClientMod implements ClientModInitializer {

    private static boolean enabled = false;
    private static KeyBinding key;

    @Override
    public void onInitializeClient() {

        key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.clear.toggle",
                GLFW.GLFW_KEY_K,
                "category.clear"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.wasPressed()) {
                enabled = !enabled;

                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal(enabled ? "CLEAR MODE ON" : "CLEAR MODE OFF"),
                            false
                    );
                }
            }

            if (enabled && client.world != null) {
                client.world.setTimeOfDay(6000);
                client.world.setRainGradient(0f);
                client.world.setThunderGradient(0f);
            }
        });
    }
}