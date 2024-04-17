package com.yakura.poenahakaret;

import com.yakura.poenahakaret.commands.ReloadCommand;
import com.yakura.poenahakaret.filters.MessageFilter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PoenaHakaret extends JavaPlugin {
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("pp").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new MessageFilter(this), this);
    }
}
