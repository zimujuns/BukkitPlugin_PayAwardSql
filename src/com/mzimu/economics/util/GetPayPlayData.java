package com.mzimu.economics.util;

import com.mcrmb.PayApi;
import com.mzimu.economics.ZMPayAward;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GetPayPlayData {
    private static Map<String,Integer> payData = new ConcurrentHashMap<>();
    private static List<Boolean> islist = new ArrayList<>();

    public static void init(){
        System.out.println("多线程初始化");
        List<OfflinePlayer> list = Arrays.asList(ZMPayAward.getPlugin().getServer().getOfflinePlayers());
        List<List<String>> arrayList = new ArrayList<>();
        List<bate> thread = new ArrayList<>();
        for(int i=0,j=200;i<list.size();i+=j){
            List<String> playNames = new ArrayList<>();
            if(i+j>list.size()){
                for(OfflinePlayer offp :list.subList(i,list.size())){
                    playNames.add(offp.getName());
                }
            }else{
                for(OfflinePlayer offp :list.subList(i,i+j)){
                    playNames.add(offp.getName());
                }
            }
            arrayList.add(playNames);
            i++;
        }

        for(int i=0;i<arrayList.size();i++){
            islist.add(false);
            thread.add(new bate(arrayList.get(i),i));
        }
        System.out.println("多线程初始化完成 一共有" + thread.size() +"准备运行");

        System.out.println("开始获取");
        for(Runnable r : thread){
            ZMPayAward.getPlugin().getServer().getScheduler().runTaskAsynchronously(
                    ZMPayAward.getPlugin(),
                    r
            );
        }


        ZMPayAward.getPlugin().getServer().getScheduler().runTaskAsynchronously(ZMPayAward.getPlugin(),
            () -> {
                boolean a = true;
                while(a) {
                    for(Boolean b : islist){
                        a = b;
                        if(b){
                            break;
                        }
                    }
                    try {
                        Thread.sleep(1000);
                        System.out.println("正在获取");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    PlayDataMysqlInit.init();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        );

    }

    public static Map<String, Integer> getData() {
        return payData;
    }

    public static class bate implements Runnable{

        private int i;
        private List<String> nameList;

        public bate(List<String> nameList,int i) {
            this.nameList = nameList;
            this.i = i;
        }

        @Override
        public void run() {
            islist.set(i,true);
            for(String name : nameList){
                Integer v = PayApi.allcharge(name);
                payData.put(name,v);
            }
            islist.set(i,false);
            System.out.println(i+" |号线程获取完毕");
        }
    }

}
