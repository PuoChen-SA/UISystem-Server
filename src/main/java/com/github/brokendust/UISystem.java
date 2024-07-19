package com.github.brokendust;

import com.github.brokendust.netty.NettyServer;
import org.bukkit.plugin.java.JavaPlugin;

public final class UISystem extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        NettyServer.init();
    }

    @Override
    public void onDisable() {

    }
}
