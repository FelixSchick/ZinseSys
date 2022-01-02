package de.karottenboy33.aerogonzinsesys.Events;

import de.karottenboy33.aerogonzinsesys.mysql.MySQL;
import de.karottenboy33.aerogonzinsesys.mysql.MySQLCreate;
import de.karottenboy33.aerogonzinsesys.mysql.ZinsenAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ZinsenAPI.createUser(player.getUniqueId());
    }
}
