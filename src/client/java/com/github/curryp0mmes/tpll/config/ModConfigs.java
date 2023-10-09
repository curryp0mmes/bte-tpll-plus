package com.github.curryp0mmes.tpll.config;

import com.github.curryp0mmes.tpll.Btetpllplus;
import com.mojang.datafixers.util.Pair;
public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static int TPLLDELAY;
    public static boolean AUTOTPLLACTIVATED;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Btetpllplus.MODID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        //configs.addKeyValuePair(new Pair<>("key.test.value1", "Just a Testing string!"), "String");
        //configs.addKeyValuePair(new Pair<>("key.test.value2", 50), "int");
        configs.addKeyValuePair(new Pair<>("tpll.tplldelay", 2000), "int");
        configs.addKeyValuePair(new Pair<>("tpll.autotpll", true), "boolean");
    }

    private static void assignConfigs() {
        TPLLDELAY = CONFIG.getOrDefault("tpll.tplldelay", 1000);
        AUTOTPLLACTIVATED = CONFIG.getOrDefault("tpll.autotpll", true);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}