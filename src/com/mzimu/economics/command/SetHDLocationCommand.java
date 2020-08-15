package com.mzimu.economics.command;

import com.mzimu.economics.ZMPayAward;
import com.mzimu.economics.util.ZMConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetHDLocationCommand {

    public static void build(Player p){
        Location loc = p.getLocation();
        ZMConfig.setHDLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getWorld());
//        HolographicRankingDisplays.refreshAll();
        p.sendMessage("["+ ZMPayAward.getPlugin().getName()+"]设置记分板成功");
    }

}
