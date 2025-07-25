package org.blazepxly.blazeCore.command;

import org.blazepxly.blazeCore.BlazeCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mainCommand implements CommandExecutor {

    private final BlazeCore plugin;

    public mainCommand(BlazeCore plugin){
        this.plugin = plugin;
    }

    public class Utils {
        public static String color (String message){
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        String NoPermissionMessage = plugin.getConfig().getString("messages.no-permission");
        String ReloadMessage = plugin.getConfig().getString("messages.reloaded-config");
        String PlayersCheckMessage = plugin.getConfig().getString("messages.players-check");
        String HealSelfMessage = plugin.getConfig().getString("messages.healed-self");
        String TargetNotOnline = plugin.getConfig().getString("messages.target-not-online").replace("%target%", args[1]);

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color(PlayersCheckMessage));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0){
            sender.sendMessage(Utils.color("&e----------------------------------"));
            sender.sendMessage(Utils.color("&e/blazecore reload &7- Reload config"));
            sender.sendMessage(Utils.color("&e/blazecore heal [player] &7- Heal self or others"));
            sender.sendMessage(Utils.color("&e/blazecore ping &7- Check ping"));
            sender.sendMessage(Utils.color("&e/blazecore playtime &7- Check playtime"));
            sender.sendMessage(Utils.color("&e----------------------------------"));
            return true;
        }

        String SubCommand = args[0].toLowerCase();
        switch (SubCommand) {
            case "reload":
                if (!sender.hasPermission("blazecore.admin")) {
                    sender.sendMessage(Utils.color(NoPermissionMessage));
                    return true;
                }
                plugin.reloadConfig();
                sender.sendMessage(Utils.color(ReloadMessage));
                return true;

            case "heal":
                if (!sender.hasPermission("blazecore.admin")) {
                    sender.sendMessage(Utils.color(NoPermissionMessage));
                    return true;
                }

                if (args.length == 1) {

                    double MaxHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
                    player.setHealth(MaxHealth);
                    player.setFoodLevel(20);
                    player.sendMessage(Utils.color(HealSelfMessage));
                }

                if (args.length == 2) {
                    Player playerheal = Bukkit.getPlayer(args[1]);
                    Player senderPlayer = (Player) sender;
                    String HealedbyOtherMessage = plugin.getConfig().getString("messages.healedbyother-player").replace("%healer%", senderPlayer.getName());

                    if (playerheal != null) {
                        double MaxHealth = playerheal.getAttribute(Attribute.MAX_HEALTH).getValue();
                        playerheal.setHealth(MaxHealth);
                        playerheal.setFoodLevel(20);
                        playerheal.sendMessage(Utils.color(HealedbyOtherMessage));
                    } else {
                        sender.sendMessage(Utils.color(TargetNotOnline));
                    }
                    return true;
                }

            case "ping":
                int ping = player.getPing();

                if (args.length == 1){
                    String LowPingMessage = plugin.getConfig().getString("messages.ping.low-ping").replace("%ping%", String.valueOf(ping))
                            .replace("%player%", player.getName());
                    String MediumPingMessage = plugin.getConfig().getString("messages.ping.medium-ping").replace("%ping%", String.valueOf(ping))
                            .replace("%player%", player.getName());;
                    String HighPingMessage = plugin.getConfig().getString("messages.ping.high-ping").replace("%ping%", String.valueOf(ping))
                            .replace("%player%", player.getName());;

                    if (ping >= 0 && ping <= 30){
                        player.sendMessage(Utils.color(LowPingMessage));
                    } else if (ping >= 31 && ping <= 120){
                        player.sendMessage(Utils.color(MediumPingMessage));
                    } else if (ping >= 121){
                        player.sendMessage(Utils.color(HighPingMessage));
                    }
                    return true;
                } else if (args.length == 2){
                    Player PlayerPing = Bukkit.getPlayer(args[1]);

                    if (PlayerPing != null){
                        int OtherPlayerPing = PlayerPing.getPing();

                        String LowPingMessage = plugin.getConfig().getString("messages.ping.low-ping").replace("%ping%", String.valueOf(OtherPlayerPing))
                                .replace("%player%", player.getName());
                        String MediumPingMessage = plugin.getConfig().getString("messages.ping.medium-ping").replace("%ping%", String.valueOf(OtherPlayerPing))
                                .replace("%player%", player.getName());;
                        String HighPingMessage = plugin.getConfig().getString("messages.ping.high-ping").replace("%ping%", String.valueOf(OtherPlayerPing))
                                .replace("%player%", player.getName());;

                        if (OtherPlayerPing >= 0 && OtherPlayerPing <= 30){
                            player.sendMessage(Utils.color(LowPingMessage));
                        } else if (OtherPlayerPing >= 31 && OtherPlayerPing <= 120){
                            player.sendMessage(Utils.color(MediumPingMessage));
                        } else if (OtherPlayerPing >= 121){
                            player.sendMessage(Utils.color(HighPingMessage));
                        }
                    } else {
                        sender.sendMessage(Utils.color(TargetNotOnline));
                    }
                }

            case "playtime":
                int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
                int days = ticks / 1728000;
                int hours = (ticks % 1728000) / 72000;
                int minutes = (ticks % 72000) / 1200;

                String PlayTimeMessages = plugin.getConfig().getString("messages.playtime")
                        .replace("%days%", String.valueOf(days))
                                .replace("%hours%", String.valueOf(hours))
                                        .replace("%minutes%", String.valueOf(minutes));

                player.sendMessage(Utils.color(PlayTimeMessages));
                return true;
        }
        return true;
    }
}
