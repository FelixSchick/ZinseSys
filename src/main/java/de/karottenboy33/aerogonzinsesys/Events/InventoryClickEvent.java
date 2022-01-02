package de.karottenboy33.aerogonzinsesys.Events;

import at.paxfu.aerogonmaster.AerogonMaster;
import at.paxfu.aerogonmaster.api.Money_API;
import de.karottenboy33.aerogonzinsesys.Commands.ZinsenCommand;
import de.karottenboy33.aerogonzinsesys.mysql.ZinsenAPI;
import de.karottenboy33.aerogonzinsesys.utils.itemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class InventoryClickEvent implements Listener {
    @EventHandler
    public void onPrepairAnvil(PrepareAnvilEvent event){
        if (event.getView().getTitle().equalsIgnoreCase("§4Enter Reason")){
            event.getInventory().setItem(0, new itemBuilder(Material.BEDROCK).build());
        }
    }
    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("§7» §bZinsen")){
            event.setCancelled(true);
            if (event.getSlot() == 11 && event.getCurrentItem().getType() == Material.LIME_DYE){
                ZinsenCommand.createLendInventory(player);
                player.openInventory(ZinsenCommand.inventoryLend);
            }
            if (event.getSlot() == 15){
                ZinsenCommand.createBackInventory(player);
                player.openInventory(ZinsenCommand.inventoryBack);
            }
        }
        if (event.getView().getTitle().equals("§7» §bZinsen §7lend")){
            event.setCancelled(true);
            if (event.getSlot() == 13){

            }
        }
        if (event.getView().getTitle().equals("§7» §bZinsen §7Pay back")){
            event.setCancelled(true);
            if (event.getSlot() == 10){
                int input = 10;

                if (input <= Money_API.getMoney(player.getUniqueId())){
                    if (!(ZinsenAPI.getSchulden(player.getUniqueId()) == 0) && ZinsenAPI.getSchulden(player.getUniqueId()) + input >= -1){
                        Money_API.removeMoney(player.getUniqueId(), Money_API.getMoney(player.getUniqueId()), (int) (10));
                        ZinsenAPI.removeSchulden(player.getUniqueId(), 10);
                        AerogonMaster.getInstance().sideboard.updateScoreboard(player);
                        player.playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                    }else {
                        player.sendMessage(AerogonMaster.PREFIX + " §4Du hast keine schulden mehr");
                    }

                }
            }
            if (event.getSlot() == 12){
                double input = 100;
                if (input <=Money_API.getMoney(player.getUniqueId())){
                    if (!(ZinsenAPI.getSchulden(player.getUniqueId()) == 0) && ZinsenAPI.getSchulden(player.getUniqueId()) + input >= -1){
                        Money_API.removeMoney(player.getUniqueId(), Money_API.getMoney(player.getUniqueId()), (int) (100));
                        ZinsenAPI.removeSchulden(player.getUniqueId(), 100);
                        AerogonMaster.getInstance().sideboard.updateScoreboard(player);
                        player.playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                    }else {
                        player.sendMessage(AerogonMaster.PREFIX + " §4Du hast keine schulden mehr");
                    }

                }
            }
            if (event.getSlot() == 14){
                int input = 1000;

                if (input <=Money_API.getMoney(player.getUniqueId())){
                    if (!(ZinsenAPI.getSchulden(player.getUniqueId()) == 0) && ZinsenAPI.getSchulden(player.getUniqueId()) + input >= -1){
                        Money_API.removeMoney(player.getUniqueId(), Money_API.getMoney(player.getUniqueId()), (int) (input));
                        ZinsenAPI.removeSchulden(player.getUniqueId(),1000);
                        AerogonMaster.getInstance().sideboard.updateScoreboard(player);
                        player.playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                    } else {
                        player.sendMessage(AerogonMaster.PREFIX + " §4Du hast keine schulden mehr");
                    }

                }
            }
            if (event.getSlot() == 16){
                int input = 10000;

                if (input<=Money_API.getMoney(player.getUniqueId())){
                    if (!(ZinsenAPI.getSchulden(player.getUniqueId()) == 0) && ZinsenAPI.getSchulden(player.getUniqueId()) + input >= -1){
                        ZinsenAPI.removeSchulden(player.getUniqueId(),10000);
                        Money_API.removeMoney(player.getUniqueId(), Money_API.getMoney(player.getUniqueId()), (int) input);
                        AerogonMaster.getInstance().sideboard.updateScoreboard(player);
                        player.playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                    }else {
                        player.sendMessage(AerogonMaster.PREFIX + " §4Du hast keine schulden mehr");
                    }

                }
            }
        }
    }
}
