package me.teoscura;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class tabList extends JavaPlugin{
    public tabList() {}
    public final LogoutListener lolisten = new LogoutListener(this);
    public Map<String, String> lastOnline = new HashMap<>();
    public String dateFormat = "HH:mm dd/MM/yyyy z";
    @Override
    public void onEnable(){
        PluginManager pm = getServer().getPluginManager();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            lastOnline = load("lastdates.yml");
        } catch (IOException e) {
            for(OfflinePlayer of: getServer().getWhitelistedPlayers()){

                String moment = simpleDateFormat.format(new Date());
                lastOnline.put(of.getName(), simpleDateFormat.format(new Date()));
            }
        }
        System.out.println("Tab is enabled!");
        getCommand("tab").setExecutor(new ListOnlinePlayers(this));
        getCommand("lastonline").setExecutor(new ListOfflinePlayers(this));
        pm.registerEvent(Event.Type.PLAYER_QUIT, lolisten, Event.Priority.Normal, this);
    }
    @Override
    public void onDisable(){
        try {
            save(lastOnline, "lastdates.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("WhoList disabled");
    }
    public void save(Map<String, String> map, String path) throws IOException {
        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        FileWriter writer = new FileWriter(path);
        yaml.dump(map, writer);
    }
    public Map<String, String> load(String path) throws IOException {
        Map<String, String> data = new HashMap<String, String>();
        BufferedReader buf = new BufferedReader(new FileReader(path));
        String[] line;
        if(buf.ready()) {
            while (buf.ready()) {
                line = buf.readLine().split(": ");
                if(line.length==2) {
                    data.put(line[0], line[1]);
                }
                else{
                    throw new IOException();
                }
            }
        }
        return data;
    }
    public void setPlayerLastDate(String player, String moment) {
        lastOnline.put(player, moment);
    }
}

