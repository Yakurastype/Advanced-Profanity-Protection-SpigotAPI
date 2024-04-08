package com.yakura.poenahakaret.managers;

import com.yakura.poenahakaret.PoenaHakaret;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteManager implements TabCompleter {
    private final PoenaHakaret plugin;

    public TabCompleteManager(PoenaHakaret plugin) {
        this.plugin = plugin;
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
