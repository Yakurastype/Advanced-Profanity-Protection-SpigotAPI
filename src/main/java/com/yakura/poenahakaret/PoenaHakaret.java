package com.yakura.poenahakaret;

import com.yakura.poenahakaret.commands.ReloadCommand;
import com.yakura.poenahakaret.filters.MessageFilter;

import com.yakura.poenahakaret.managers.ConfigManager;
import com.yakura.poenahakaret.managers.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PoenaHakaret extends JavaPlugin {
    private ConfigManager configManager;
    @Override
    public void onEnable() {
        this.getLogger().info(" ");
        this.getLogger().info("\033[34m\033[3mTHANKS: \033[23m\033[36mhttps://github.com/Yakurastype/Advanced-Profanity-Protection-SpigotAPI \u001B[0m");
        this.getLogger().info("\033[34m\033[3mTHANKS: \033[23m\033[36mPoenaSaga için MustafaSZ Tarafından Yapıldı!");
        this.getLogger().info(" ");

        configManager = new ConfigManager(this);
        configManager.setupConfig();

        this.getLogger().info("\u001B[32mSUCCESS: \u001B[0mSohbet Listener Başlatıldı \u001B[31m♥ \u001B[0m");
        this.getLogger().info(MessageHelper.CommandConsoleLogger("'PoenaHakaret Reload' Komutu Yüklendi \u001B[0m"));
        this.getLogger().info(" ");

        getCommand("poenahakaret").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new MessageFilter(this), this);

    }


}
