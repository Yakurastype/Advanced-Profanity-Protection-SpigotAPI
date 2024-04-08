package com.yakura.poenahakaret.managers;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

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

    @Nonnull
    public static String CommandConsoleLogger(@Nonnull String message) {
        Objects.requireNonNull(message, "message cannot be null!");
        return "\033[35mCOMMAND: \u001B[0m" + message;
    }

    @Nonnull
    public static String SuccessConsoleLogger(@Nonnull String message) {
        Objects.requireNonNull(message, "message cannot be null!");
        return "\u001B[32mSUCCESS: \u001B[0m" + message +" \u001B[31m♥";
    }

    @Nonnull
    public static String HataConsoleLogger(@Nonnull String message) {
        Objects.requireNonNull(message, "message cannot be null!");
        return "\033[31mERROR: \u001B[0m" + message;
    }

    @Nonnull
    public static String LogConsoleLogger(@Nonnull String message) {
        Objects.requireNonNull(message, "message cannot be null!");
        return "\033[33mLOG: \u001B[0m" + message;
    }
}
