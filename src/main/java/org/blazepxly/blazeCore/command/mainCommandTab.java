package org.blazepxly.blazeCore.command;

import org.blazepxly.blazeCore.BlazeCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mainCommandTab implements TabCompleter {

    private final BlazeCore plugin;

    public mainCommandTab(BlazeCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> subcommands = Arrays.asList("reload", "heal", "ping", "playtime");
        List<String> suggestion = new ArrayList<>();

        if (args.length == 1) {
            for (String sub : subcommands) {
                if (sub.toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestion.add(sub);
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("heal") || args[0].equalsIgnoreCase("ping")) {
                List<Player> OnlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

                for (Player PlayerList : OnlinePlayers) {
                    if (PlayerList.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        suggestion.add(PlayerList.getName());
                    }
                }
            }
        }
        return suggestion;
    }
}



