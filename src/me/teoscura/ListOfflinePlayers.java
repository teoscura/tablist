package me.teoscura;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getWhitelistedPlayers;

public class ListOfflinePlayers implements CommandExecutor {
    public tabList plugin;
    public ListOfflinePlayers(tabList tabList) {
        plugin = tabList;
    }
    public String dateFormat = "HH:mm dd/MM/yyyy z";
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        Player player = (Player) commandSender;
        Player[] onplayers = getOnlinePlayers();
        boolean found = false;

        OfflinePlayer[] ofplayers = getWhitelistedPlayers().toArray(new OfflinePlayer[0]);

        player.sendMessage(ChatColor.GOLD+"Players offline: "+ChatColor.YELLOW+(ofplayers.length-onplayers.length)+"/"+ofplayers.length);
        for(OfflinePlayer of: ofplayers) {
            for(Player p : onplayers){
                if(Objects.equals(p.getDisplayName(), of.getName())){
                    found = true;
                }
            }
            if(!found){
                try {
                    player.sendMessage(ChatColor.GRAY + "() " + of.getName() + " - " + getTimeDifference(of.getName()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            found = false;
        }
        return true;
    }

    public String getTimeDifference(String playerName) throws ParseException {
        String string;
        if (plugin.lastOnline.containsKey(playerName)) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            long time = Math.abs(date.getTime() - (sdf.parse(plugin.lastOnline.get(playerName)).getTime()));
            long d = TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS);
            long h = TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS) - d * 24;
            long m = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS) - h * 60 - d * 24 * 60;
            if(d!=0){
                string = d+"d "+h+"h "+m+"m ago.";
            }
            else{
                if(h!=0){
                    string = h+"h "+m+"m ago.";
                }
                else{
                    string = m+"m ago.";
                }
            }
            return string;
        }
        else{
            return "null, no record of logout";
        }
    }
}
