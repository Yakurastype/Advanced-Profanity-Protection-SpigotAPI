package com.yakura.poenahakaret.managers;

import org.bukkit.ChatColor;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MessageHelper {

    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F\\d]{6}");

    @Nonnull
    public static String colored(@Nonnull String message) {
        Objects.requireNonNull(message, "message cannot be null!");


        Matcher matcher = HEX_PATTERN.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
            matcher = HEX_PATTERN.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
