package com.mzimu.economics.util;

import com.mzimu.economics.ZMPayAward;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class ZMConfig {

    private static String No_Give,No_Play,No_Box,Yes_Give,No_Give_Money,world,hd_Msg,hd_Title,fileUrl,name,passworld;
    private static int Time;
    private static double x,y,z;

    /**
     * 在加载时执行
     */
    public static void init() {
        String a = "设置错误 请检查是否设置正确";
        FileConfiguration fc = ZMPayAward.getPlugin().getConfig();
        fileUrl = fc.getString("数据.地址");
        name = fc.getString("数据.用户名");
        passworld = fc.getString("数据.密码");
        No_Give = fc.getString("消息.已被领取消息",a).replace("&","§");
        No_Give_Money = fc.getString("消息.金额不足消息",a).replace("&","§");
        Yes_Give = fc.getString("消息.领取成功消息",a).replace("&","§");
        No_Box = fc.getString("消息.奖励不存在消息",a).replace("&","§");
        No_Play = fc.getString("消息.玩家不存在消息",a).replace("&","§");
        Time = fc.getInt("全息显示.排行榜刷新时间",3600);
        x = fc.getDouble("全息显示.x",0);
        y = fc.getDouble("全息显示.y",0);
        z = fc.getDouble("全息显示.z",0);
        world = fc.getString("全息显示.世界","world");
        hd_Msg = fc.getString("全息显示.排行消息",a).replace("&","§");
        hd_Title = fc.getString("全息显示.排行标题",a).replace("&","§");
    }


    public static String getNo_Give() {
        return No_Give;
    }

    public static String getYes_Give() {
        return Yes_Give;
    }

    public static String getNo_Box() {
        return No_Box;
    }

    public static String getNo_Play() {
        return No_Play;
    }

    public static String getNo_Give_Money() {
        return No_Give_Money;
    }

    public static int getTime() {
        return Time;
    }

    public static double getHDX() {
        return x;
    }

    public static double getHDY() {
        return y;
    }

    public static double getHDZ() {
        return z;
    }

    public static void setHDLocation(double x,double y,double z,World world){
        ZMConfig.x = x;
        ZMConfig.y = y;
        ZMConfig.z = z;
        FileConfiguration fc = ZMPayAward.getPlugin().getConfig();
        fc.set("全息显示.x",x);
        fc.set("全息显示.y",y);
        fc.set("全息显示.z",z);
        fc.set("全息显示.世界",world.getName());
        ZMPayAward.getPlugin().saveConfig();
    }

    public static String getWorld() {
        return world;
    }


    public static String getHDMsg() {
        return hd_Msg;
    }

    public static String getHd_Title() {
        return hd_Title;
    }

    public static String getFileUrl() {
        return fileUrl;
    }

    public static String getPassword() {
        return passworld;
    }

    public static String getMysqlUid() {
        return name;
    }
}
