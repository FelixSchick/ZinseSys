package de.karottenboy33.aerogonzinsesys;

import at.paxfu.aerogonmaster.listener.JoinListener;
import de.karottenboy33.aerogonzinsesys.Commands.ZinsenCommand;
import de.karottenboy33.aerogonzinsesys.Events.InventoryClickEvent;
import de.karottenboy33.aerogonzinsesys.Events.onJoinEvent;
import de.karottenboy33.aerogonzinsesys.mysql.MySQL;
import de.karottenboy33.aerogonzinsesys.mysql.MySQLCreate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class AerogonZinseSys extends JavaPlugin {

    public static File mysqlf;
    public static FileConfiguration mysql;
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        createFiles();
        getCommand("zinsen").setExecutor(new ZinsenCommand());
        de.karottenboy33.aerogonzinsesys.mysql.MySQLCreate.connect();
        de.karottenboy33.aerogonzinsesys.mysql.MySQLCreate.createsTable();
        Bukkit.getPluginManager().registerEvents(new InventoryClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new onJoinEvent(), this);
    }

    @Override
    public void onDisable() {
        MySQLCreate.close();
    }


    private void createFiles() {
        mysqlf = new File(getDataFolder(), "mysql.yml");
        if (!mysqlf.exists()) {
            mysqlf.getParentFile().mkdirs();
            saveResource("mysql.yml", false);
        }
        mysql = new YamlConfiguration();
        try {
            mysql.load(mysqlf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


}
