package com.mzimu.economics.data;

import com.mzimu.economics.util.SqlUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayPay {
    private static Map<String,PlayPay> dataMap = new HashMap<>();

    public static Map<String, PlayPay> getDataMap() {
        return dataMap;
    }

    public static PlayPay getPayData(Player p){
        return dataMap.get(p.getName());
    }

    /**
     * 必须进行异步处理
     * @param p
     * @return
     */
    public static PlayPay initData(Player p){
        Integer i = SqlUtil.getPlayPayNum(p);
        PlayPay pp = new PlayPay(p,i);
        dataMap.put(p.getName(),pp);
        return pp;
    }

    private Player player;
    //基准值的Money
    private int money;

    public PlayPay(Player player, int money) {
        this.player = player;
        this.money = money;
    }


    public Player getPlayer() {
        return player;
    }

    public int getMoney() {
        return money;
    }
}
