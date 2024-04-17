package com.yakura.poenahakaret.filters;

import com.yakura.poenahakaret.PoenaHakaret;
import com.yakura.poenahakaret.managers.MessageHelper;
import com.yakura.poenahakaret.managers.WebhookSender;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFilter implements Listener {
    private final PoenaHakaret plugin;
    private final Pattern hexPattern;

    public MessageFilter(PoenaHakaret plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.hexPattern = Pattern.compile("#[a-fA-F\\d]{6}");
    }



    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() || player.hasPermission(plugin.getConfig().getString("administrator.permissions"))) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        String message = event.getMessage();

        ConfigurationSection kufurCategories = plugin.getConfig().getConfigurationSection("catagories");
        if (kufurCategories != null) {
            message = message.toLowerCase();

            for (String category : kufurCategories.getKeys(false)) {
                List<String> kufurler = kufurCategories.getStringList(category + ".wordlist");
                String cezaKomut = kufurCategories.getString(category + ".punishment");

                for (String kufur : kufurler) {
                    Matcher matcher = hexPattern.matcher(message);
                    while (matcher.find()) {
                        String color = message.substring(matcher.start(), matcher.end());
                        message = message.replace(color, "");
                    }

                    if (isFoulLanguage(message, kufur)) {
                        player.sendMessage(MessageHelper.colored("&d&lPOENASAGA &cMesajınız kaldırıldı, &e" + category + " &ckategorisinde küfürlü dil kullanmak yasaktır."));
                        player.sendMessage(MessageHelper.colored("&7Lütfen saygılı bir dil kullanın."));
                        sendLogMessage(player, category, kufur, cezaKomut);
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cezaKomut.replace("%player%", player.getName()));
                        });
                        if (plugin.getConfig().getBoolean("webhook.enabled")){
                            WebhookSender.sendWebhook(plugin.getConfig().getString("webhook.url"), player.getName(), event.getMessage(), kufur, cezaKomut, category);
                        }

                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

        event.setMessage(message);
    }

    private boolean isFoulLanguage(String message, String foulWord) {
        String[] words = message.split("\\s+");

        for (String word : words) {
            Player targetPlayer = Bukkit.getPlayer(word);
            if (targetPlayer != null && targetPlayer.getName().equalsIgnoreCase(word)) {
                continue;
            }

            if (plugin.getConfig().getStringList("whitelist-words").contains(word.toLowerCase())) {
                continue;
            }

            if (foulWord.endsWith("*")) {
                String exactWord = foulWord.substring(0, foulWord.length() - 1);
                if (word.equalsIgnoreCase(exactWord)) {
                    return true;
                }
            } else {
                if (word.length() >= foulWord.length()) {
                    for (int i = 0; i <= word.length() - foulWord.length(); i++) {
                        boolean match = true;
                        for (int j = 0; j < foulWord.length(); j++) {
                            if (word.charAt(i + j) != foulWord.charAt(j)) {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            int minAccuracy = (int) Math.ceil(foulWord.length() * plugin.getConfig().getDouble("censor.chance"));
                            int correctCount = foulWord.length();
                            if (correctCount >= minAccuracy) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private void sendLogMessage(Player player, String category, String foulWord, String cezaKomut) {
        String yetkiliPerm = plugin.getConfig().getString("administrator.permissions");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.isOp() || onlinePlayer.hasPermission(yetkiliPerm)) {
                String logMessage = String.format("&d&lLOG > &e%s &badlı oyuncu, &e%s &bkategorisinde &e'%s' &bkelimesini kullanarak hakaret etti.", player.getName(), category, foulWord);
                onlinePlayer.sendMessage(MessageHelper.colored(logMessage));
            }
        }
    }
}
