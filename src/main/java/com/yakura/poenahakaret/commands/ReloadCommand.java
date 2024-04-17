package com.yakura.poenahakaret.commands;

import com.yakura.poenahakaret.PoenaHakaret;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor, TabCompleter {
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("pp")) {
            if (args.length == 1) {
                completions.add("reload");
            }
        }

        return completions;
    }
}
