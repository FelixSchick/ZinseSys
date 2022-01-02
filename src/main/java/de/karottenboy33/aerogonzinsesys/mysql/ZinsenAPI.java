package de.karottenboy33.aerogonzinsesys.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ZinsenAPI {
    public static boolean userPlayedBefore(UUID uuid) {
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `zinsen` WHERE `uuid` = ?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(uuid));
            }
        });
        try {
            if(set.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void createUser(UUID uuid) {
        if(!userPlayedBefore(uuid)) {
            ResultSet set = MySQL.getInstance().executeQuery("INSERT INTO `zinsen`(`uuid`, `schulden`) VALUES (?,?)", new HashMap<Integer, String>(){
                {
                    put(1, String.valueOf(uuid));
                    put(2, String.valueOf(0));

                }
            });
        }
    }

    public static int getSchulden(UUID uuid){
        ResultSet set = MySQL.getInstance().executeQuery("SELECT * FROM `zinsen` WHERE uuid=?", new HashMap<Integer, String>(){
            {
                put(1, uuid.toString());
            }
        });
        try {
            while (set.next()){
                return set.getInt("schulden");
            }
            return 0;
        } catch (SQLException e){
            e.getErrorCode();
            return 0;
        }
    }


    public static void setSchulden(UUID uuid, int amount){
        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `zinsen` SET `schulden`=? WHERE uuid=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(amount));
                put(2, uuid.toString());
            }
        });
    }


    public static void addSchulden(UUID uuid, int amount){
        int have = getSchulden(uuid);

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `zinsen` SET `schulden`=? WHERE uuid=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(have + amount));
                put(2, uuid.toString());
            }
        });
    }


    public static void removeSchulden(UUID uuid, int amount){
        int have = getSchulden(uuid);

        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `zinsen` SET `schulden`=? WHERE uuid=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(have - amount));
                put(2, uuid.toString());
            }
        });
    }


    public static void resetSchulden(UUID uuid){
        ResultSet set = MySQL.getInstance().executeQuery("UPDATE `zinsen` SET `schulden`=? WHERE uuid=?", new HashMap<Integer, String>(){
            {
                put(1, String.valueOf(0));
                put(2, uuid.toString());
            }
        });
    }
}
