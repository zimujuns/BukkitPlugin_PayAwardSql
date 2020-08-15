package com.mzimu.economics.command;

import com.mzimu.economics.data.BoxData;
import com.mzimu.economics.util.ZMConfig;
import org.bukkit.entity.Player;

public class GiveBoxCommand {

    public GiveBoxCommand() {
    }

    /**
     * 给箱子中的奖励
     * @param id
     * @param player
     */
    public static void build(String id,Player player){
        try {
            BoxData.getBox(id).giveReward(player);
        }catch (NullPointerException e){
            player.sendMessage(ZMConfig.getNo_Box());
        }

    }
}
