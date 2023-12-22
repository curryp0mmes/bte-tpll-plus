package com.github.curryp0mmes.tpll;

import com.github.curryp0mmes.tpll.config.ModConfigs;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class CoordinateQueue extends Thread {

    @Override
    public void run() {
        while (!BtetpllplusClient.tpllqueue.isEmpty()) {
            String coords = BtetpllplusClient.tpllqueue.remove(0);
            autoTpll(coords);
        }
    }

    private void autoTpll(String coords) {
        MinecraftClient mc = MinecraftClient.getInstance();
        try {


            PlayerEntity player = mc.player;

            if (player == null) return;

            BlockPos oldPos = player.getBlockPos();

            new CommandDispatcher<PlayerEntity>().execute("/tpll " + coords, player);
            sleep(ModConfigs.TPLLDELAY);

            //retry if position doesn't change
            if (player.getBlockPos().equals(oldPos)) {
                new CommandDispatcher<PlayerEntity>().execute("/tpll " + coords + " " + (player.getBlockPos().getY() + 5), player);
                sleep(ModConfigs.TPLLDELAY);
            }
            //if it still doesn't change ABORT
            if (player.getBlockPos().equals(oldPos)) {
                BtetpllplusClient.printStatusBar("§4AutoTPLL failed");
                return;
            }

            player.sendMessage(Text.of("//paste"));

            sleep(500);

        } catch (Exception ignored) {
            BtetpllplusClient.printStatusBar("§4AutoTPLL failed");
        }
    }
}
