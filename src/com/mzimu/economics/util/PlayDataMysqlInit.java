package com.mzimu.economics.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class PlayDataMysqlInit {
    public static void init() throws SQLException {
        Connection c = SqlUtil.getConn();
        Statement stat = c.createStatement();
        try{
            stat.execute("select * from " + SqlUtil.getTab());
        }catch (SQLException throwables){
            try {
                SqlUtil.create(stat);
                for(Map.Entry<String,Integer> entry : GetPayPlayData.getData().entrySet()){
                    try{
                        SqlUtil.createPlay(stat,entry.getKey(),entry.getValue());
                    }catch (SQLException e){
                        if(e.getErrorCode() == 1062){
                            System.out.println("有一个玩家与另外一个玩家大小写,造成数据无法存入,请注意!   >>" + entry.getKey());
                            continue;
                        }else{
                            e.printStackTrace();
                        }
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stat.close();
            }
        }finally {
            c.close();
        }
        System.out.println("数据存入完成");
    }
}
