package org.blazepxly.blazeCore;

import org.blazepxly.blazeCore.command.mainCommand;
import org.blazepxly.blazeCore.command.mainCommandTab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlazeCore extends JavaPlugin {

    @Override
    public void onEnable() {
        String[] banner = new String[] {
                "__________.__                       _________                       ",
                "\\______   \\  | _____  ________ ____ \\_   ___ \\  ___________   ____  ",
                " |    |  _/  | \\__  \\ \\___   // __ \\/    \\  \\/ /  _ \\_  __ \\_/ __ \\ ",
                " |    |   \\  |__/ __ \\_/    /\\  ___/\\     \\___(  <_> )  | \\/\\  ___/ ",
                " |______  /____(____  /_____ \\\\___  >\\______  /\\____/|__|    \\___  >",
                "        \\/          \\/      \\/    \\/        \\/                   \\/ "
        };

        for (String line : banner){
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + line);
        }

        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "config.yml has been loaded.");
        this.getCommand("blazecore").setExecutor(new mainCommand(this));
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Command has been loaded.");
        this.getCommand("blazecore").setTabCompleter(new mainCommandTab(this));
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "TabCompleter has been loaded.");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("BlazeCore has been shutted down.");
    }
}
