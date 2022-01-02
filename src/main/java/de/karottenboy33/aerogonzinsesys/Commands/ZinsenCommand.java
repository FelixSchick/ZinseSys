package de.karottenboy33.aerogonzinsesys.Commands;

import at.paxfu.aerogonmaster.AerogonMaster;
import at.paxfu.aerogonmaster.api.Money_API;
import de.karottenboy33.aerogonzinsesys.AerogonZinseSys;
import de.karottenboy33.aerogonzinsesys.mysql.ZinsenAPI;
import de.karottenboy33.aerogonzinsesys.utils.itemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZinsenCommand implements CommandExecutor, TabCompleter {



    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 0){
                createMainInventory(player);
                player.openInventory(inventory);
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("lend")){
                    if (ZinsenAPI.getSchulden(player.getUniqueId()) <= 0) {
                            try {
                                double input = Double.parseDouble(args[1]);
                                if (input <= 100000) {
                                    double finalInput = input + (input * 0.375);
                                    int roundedFinalInput = (int) Math.round(finalInput);
                                        ZinsenAPI.addSchulden(player.getUniqueId(), roundedFinalInput);
                                        Money_API.addMoney(player.getUniqueId(), Money_API.getMoney(player.getUniqueId()), (int) input);
                                        AerogonMaster.getInstance().sideboard.updateScoreboard(player);
                                        player.sendMessage(AerogonMaster.PREFIX + " §7Du hast dir §b" + input + "$ §7geliehen.");

                                } else {
                                    player.sendMessage(AerogonMaster.PREFIX + " §4Du kannst dir nur beträge unter 100.000 leihen!");
                                }
                            } catch (NumberFormatException ex) {
                                player.sendMessage(AerogonMaster.PREFIX + " §4Du kannst nur zahlen angeben!");
                            }
                    } else {
                        player.sendMessage(AerogonMaster.PREFIX + " §4Du hast schon einen Kredit!");
                    }
                }
                if (args[0].equalsIgnoreCase("payback")){
                        try {
                            int input = Integer.parseInt(args[1]);
                                if (ZinsenAPI.getSchulden(player.getUniqueId()) >= 0){
                                    if (input <= Money_API.getMoney(player.getUniqueId())){
                                        if (input <= 138000){
                                            if (ZinsenAPI.getSchulden(player.getUniqueId()) != 0 && ZinsenAPI.getSchulden(player.getUniqueId()) + input >= 0){
                                                if (input >= ZinsenAPI.getSchulden(player.getUniqueId())){
                                                    ZinsenAPI.removeSchulden(player.getUniqueId(), input);
                                                    Money_API.removeMoney(player.getUniqueId(), Money_API.getMoney(player.getUniqueId()), input);
                                                    AerogonMaster.getInstance().sideboard.updateScoreboard(player);
                                                    player.playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                                                    player.sendMessage(AerogonMaster.PREFIX+ "§7Du hast §b" + input + "$ §7zurückgezahlt.");
                                                }
                                            } else {
                                                player.sendMessage(AerogonMaster.PREFIX + "§7 Du hast keine Schulden mehr!");
                                            }
                                        } else {
                                            player.sendMessage(AerogonMaster.PREFIX + " §4Du kannst dir nur beträge unter 138.000 zurückzahlen!");
                                        }
                                    } else {
                                        player.sendMessage(AerogonMaster.PREFIX + " §4Du hast nicht genug!");
                                    }
                                } else {
                                    player.sendMessage(AerogonMaster.PREFIX + " §4Du hast keine schulden mehr.");
                                }
                        } catch (NumberFormatException ex){
                            player.sendMessage(AerogonMaster.PREFIX + " §4Du kannst nur zahlen angeben!");
                        }
                    }
                }
            }
        return false;
    }


    public static Inventory inventory;

    public static void createMainInventory(Player player){
        inventory = Bukkit.createInventory(player, 3*9, "§7» §bZinsen");
        for (int i =0; i<3*9; i++){
            inventory.setItem(i, new itemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname(" ").build());
        }
        //inventory.setItem(11, new itemBuilder(Material.LIME_DYE).displayname("§7» §2lend").build());
        inventory.setItem(11, new itemBuilder(Material.BARRIER).displayname("§7» §4Comming soon").lore("§7use the command /zinsen lend <Amount>").build());
        inventory.setItem(4, new itemBuilder(Material.BOOK).displayname("§7» Du hast noch §b"+ ZinsenAPI.getSchulden(player.getUniqueId())+ "$ §7Schulden").build());
        inventory.setItem(15, new itemBuilder(Material.RED_DYE).displayname("§7» §cpayback").build());
    }

    public static Inventory inventoryLend;

    public static void createLendInventory(Player player){
        inventoryLend = Bukkit.createInventory(player, 3*9, "§7» §bZinsen §7lend");

        for (int i =0; i<3*9; i++){
            inventoryLend.setItem(i, new itemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname(" ").build());
        }
        inventoryLend.setItem(13, new itemBuilder(Material.PAPER).displayname("§7» §bLend").build());
    }

    public static Inventory inventoryBack;

    public static void createBackInventory(Player player){
        inventoryBack = Bukkit.createInventory(player, 3*9, "§7» §bZinsen §7Pay back");
        for (int i =0; i<3*9; i++){
            inventoryBack.setItem(i, new itemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname(" ").build());
        }
        inventoryBack.setItem(4, new itemBuilder(Material.BOOK).displayname("§7» §bBeachte das du 0.375% zinseb zahlst.").lore("§710").amount(8).build());
        inventoryBack.setItem(10, new itemBuilder(Material.PAPER).displayname("§7» §bPay back").lore("§710").amount(8).build());
        inventoryBack.setItem(12, new itemBuilder(Material.PAPER).displayname("§7» §bPay back").lore("§7100").amount(16).build());
        inventoryBack.setItem(14, new itemBuilder(Material.PAPER).displayname("§7» §bPay back").lore("§71000").amount(32).build());
        inventoryBack.setItem(16, new itemBuilder(Material.PAPER).displayname("§7» §bPay back").lore("§710000").amount(64).build());

    }

    @Override
    public List< String > onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            List<String> complete = new ArrayList<>();
            complete.add("lend");
            complete.add("payback");
            return  complete;
        }  else if(args.length == 2) {
            return Collections.singletonList("<0-100000>");
        } else if(args.length >= 3) {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}