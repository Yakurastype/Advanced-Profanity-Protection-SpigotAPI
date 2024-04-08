package com.yakura.poenahakaret.commands;

import com.yakura.poenahakaret.PoenaHakaret;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    private final PoenaHakaret plugin;

    public ReloadCommand(PoenaHakaret plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            return false;
        }
        if (!(sender instanceof Player) || ((Player) sender).isOp()) {
            plugin.reloadConfig();
            sender.sendMessage("♥");
        } else {
            sender.sendMessage("x");
        }

        return true;
    }
}
