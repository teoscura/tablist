package me.teoscura;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.Bukkit.*;

public class ListOnlinePlayers implements CommandExecutor {

    public tabList plugin;
    public ListOnlinePlayers(tabList plug){
        plugin = plug;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        Player player = (Player) commandSender;
        Player[] onplayers = getOnlinePlayers();
        OfflinePlayer[] ofplayers = getWhitelistedPlayers().toArray(new OfflinePlayer[0]);
        player.sendMessage(ChatColor.DARK_GREEN+"Players online: "+onplayers.length+"/"+ofplayers.length);
        for(Player of: onplayers){
            player.sendMessage(ChatColor.GREEN+"[> " + of.getDisplayName());
        }
        return true;
    }
}
