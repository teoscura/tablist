package me.teoscura;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class LogoutListener extends PlayerListener {
    public static tabList plugin;
    public String dateFormat = "HH:mm dd/MM/yyyy z";
    public LogoutListener(tabList wholist) {
        plugin = wholist;
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String moment = simpleDateFormat.format(new Date());
        plugin.setPlayerLastDate(player.getDisplayName().toLowerCase(), moment);
    }

}
