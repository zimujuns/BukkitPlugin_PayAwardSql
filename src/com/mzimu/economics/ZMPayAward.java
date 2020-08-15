package com.mzimu.economics;

import com.mzimu.economics.command.OnCommandMain;
import com.mzimu.economics.data.BoxData;
import com.mzimu.economics.listener.PlayOnAction;
import com.mzimu.economics.util.GetPayPlayData;
import com.mzimu.economics.util.PlayDataMysqlInit;
import com.mzimu.economics.util.ZMConfig;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class ZMPayAward extends JavaPlugin {

    private static Plugin p;


    public static Plugin getPlugin() {
        return p;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        p = this;
        ZMConfig.init();
        GetPayPlayData.init();
        BoxData.init();

        this.getCommand("zmpa").setExecutor(new OnCommandMain());
        this.getServer().getPluginManager().registerEvents(new PlayOnAction(),this);

    }

    public static void onR(){
        getPlugin().reloadConfig();
        ZMConfig.init();
        BoxData.init();
    }
}
