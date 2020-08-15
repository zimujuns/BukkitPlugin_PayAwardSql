package com.mzimu.economics.data;

import com.mcrmb.PayApi;
import com.mzimu.economics.ZMPayAward;
import com.mzimu.economics.util.SqlUtil;
import com.mzimu.economics.util.ZMConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoxData {
    private static Map<String,Box> boxMap = new HashMap<>();

    public static void init(){
        boxMap.clear();
        String standardValue = "金额限制",reward = "礼包内容",listName = "礼包详情";
        FileConfiguration fc = ZMPayAward.getPlugin().getConfig();
        for(String key : fc.getConfigurationSection(listName).getKeys(false)){
            boxMap.put(key,new Box(key,fc.getDouble( listName+"."+key+"."+standardValue),fc.getStringList(listName+"."+key+"."+reward)));
        }
    }

    public static Map<String, Box> getBoxMap() {
        return boxMap;
    }

    public static Box getBox(String id) {
        return boxMap.get(id);
    }

    public static Set<String> getKey() {
        return boxMap.keySet();
    }

    public static class Box {
        private String id;
        private double standardValue;
        private List<String> reward;

        public Box(String id, double standardValue, List<String> reward) {
            this.id = id;
            this.standardValue = standardValue;
            this.reward = reward;
        }

        /**
         * 获取箱子里面的奖励
         */
        public void giveReward(Player p){
            if(SqlUtil.isSqlMap(p)){
                p.sendMessage("领取失败:有可能是服务器网络异常或金额不足");
            }
            Bukkit.getServer().getScheduler().runTaskAsynchronously(ZMPayAward.getPlugin(),
                () -> {
                    int a = PayApi.allcharge(p.getName());
                    int b = PlayPay.getPayData(p).getMoney();
                    int v = (a-b);
                    if(a-b<standardValue){
                        p.sendMessage(ZMConfig.getNo_Give_Money().replace("[2]", v + "").replace("[3]", standardValue - v + ""));
                    }else{
                        try {
                            if(!SqlUtil.getPlayBoxData(id,p)){
                                try {
                                    SqlUtil.setPlayBoxData(id,p);
                                    kid(p);
                                    p.sendMessage(ZMConfig.getYes_Give().replace("[2]", standardValue-v + ""));
                                } catch (SQLException throwables) {
                                    p.sendMessage("数据库异常,请通知管理员");
                                    throwables.printStackTrace();
                                    return;
                                }
                            }else{
                                p.sendMessage(ZMConfig.getNo_Give().replace("[2]", v + "").replace("[3]", standardValue - v + ""));
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }

                }
            );

        }

        public void kid(Player p){
            for(String command : reward){
                BukkitCommand.broadcastCommandMessage(p,command);
            }
        }
    }
}
