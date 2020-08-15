package com.mzimu.economics.util;

import com.mcrmb.PayApi;
import com.mzimu.economics.ZMPayAward;
import org.bukkit.entity.Player;

public class McrmbUtil {

    public static void upPlayAllCharge(Player player){
        ZMPayAward.getPlugin().getServer().getScheduler().runTaskAsynchronously(ZMPayAward.getPlugin(),
                () -> {
                    int money = PayApi.allcharge(player.getName());

                }
        );

    }

}
