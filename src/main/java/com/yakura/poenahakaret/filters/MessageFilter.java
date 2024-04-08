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
        if (Bukkit.getPlayer(foulWord) != null) {
            return false;
        }

        List<String> allowedWords = plugin.getConfig().getStringList("whitelist-words");
        if (allowedWords.contains(message.toLowerCase())) {
            return false;
        }

        int minAccuracy = (int) Math.ceil(foulWord.length() * plugin.getConfig().getDouble("censor.chance"));
        int correctCount = 0;
        for (int i = 0; i < foulWord.length(); i++) {
            if (i < message.length() && foulWord.charAt(i) == message.charAt(i)) {
                correctCount++;
            }
        }
        return correctCount >= minAccuracy;
    }

    private void sendLogMessage(Player player, String category, String foulWord, String cezaKomut) {
        String yetkiliPerm = plugin.getConfig().getString("administrator.permissions");
        plugin.getLogger().info(MessageHelper.LogConsoleLogger(player + ", " + category + ", '" + foulWord + "'"));
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.isOp() || onlinePlayer.hasPermission(yetkiliPerm)) {
                String logMessage = String.format("&d&lLOG > &e%s &badlı oyuncu, &e%s &bkategorisinde &e'%s' &bkelimesini kullanarak hakaret etti.", player.getName(), category, foulWord);
                onlinePlayer.sendMessage(MessageHelper.colored(logMessage));
            }
        }
    }
}
