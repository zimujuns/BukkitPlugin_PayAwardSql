package com.mzimu.economics.util;

import com.mcrmb.PayApi;
import com.mzimu.economics.ZMPayAward;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class SqlUtil {
    private static Map<String,Boolean> isMysql = new HashMap<>();
    private static String tab_c = "name",tab_money="money",tab="ZMPayAward";
    private static Connection conn = null;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws SQLException {
        if(conn == null || conn.isClosed()){
            StringBuffer sb = new StringBuffer();
            sb.append("jdbc:mysql:");
            sb.append(ZMConfig.getFileUrl());
            Connection con = DriverManager.getConnection(sb.toString(),ZMConfig.getMysqlUid(),ZMConfig.getPassword());
            conn = con;
        }
        return conn;
    }


    /**
     * 必须进行异步处理
     *
     * 方法若完成执行，则对应的isMysql也会变成True
     *
     * @param player
     * @return
     */
    public static Integer getPlayPayNum(Player player) {
        int i=0;
        trueSqlMap(player);
        try {
            Connection c = getConn();
            Statement stat = c.createStatement();
            try {
                ResultSet rs = stat.executeQuery(getMysqlSelet(tab_c,"'"+player.getName()+"'",tab));
                if(rs.next()){
                    i = rs.getInt(tab_money);
                }
            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                try {
                    i = PayApi.allcharge(player.getName());
                    createPlay(stat,player,i);
                }catch (SQLException e){
                    e.printStackTrace();
                    create(stat);
                }
            }
            stat.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        falseSqlMap(player);
        return i;
    }

    public static void setPlayBoxData(String id,Player player) throws SQLException {
        Connection c = getConn();
        Statement stat = c.createStatement();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("insert into ZM");
            sb.append(id);
            sb.append("(name) values('");
            sb.append(player.getName());
            sb.append("')");
            stat.executeUpdate(sb.toString());
        } catch (SQLException throwables) {
            StringBuffer sb = new StringBuffer();
            sb.append("create table ZM");
            sb.append(id);
            sb.append("(");
            sb.append(" varchar(20) Primary Key Not Null)");
            stat.executeUpdate(sb.toString());
        }finally {
            stat.close();
            c.close();
        }
    }

    public static boolean getPlayBoxData(String id, Player p) throws SQLException {
        Connection c = null;
        try{
            c = getConn();
            Statement stat = c.createStatement();
            try {
                ResultSet rs = stat.executeQuery(getMysqlSelet(tab_c,"'"+p.getName()+"'","ZM"+id));
                if(rs.next()){
                    rs.close();
                    stat.close();
                    return true;
                }else{
                    rs.close();
                    stat.close();
                    return false;
                }
            }catch (SQLException throwables){
                StringBuffer sb = new StringBuffer();
                sb.append("create table ZM");
                sb.append(id);
                sb.append("(");
                sb.append(tab_c);
                sb.append(" varchar(20) Primary Key Not Null)");
                stat.executeUpdate(sb.toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            c.close();
        }
        return false;

    }


    public static void create(Statement stat) throws SQLException {
        StringBuffer sb = new StringBuffer("create table ");
        sb.append(tab);
        sb.append(" (");
        sb.append(tab_c);
        sb.append(" varchar(20) Primary Key Not Null,");
        sb.append(tab_money);
        sb.append(" Integer Null )");
        stat.executeUpdate(sb.toString());
    }

    public static void createPlay(Statement stat,Player p,int i) throws SQLException {
        createPlay(stat,p.getName(),i);
    }
    public static void createPlay(Statement stat,String p,int i) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ");
        sb.append(tab);
        sb.append("(");
        sb.append(tab_c);
        sb.append(",");
        sb.append(tab_money);
        sb.append(") values('");
        sb.append(p);
        sb.append("',"+ i +")");
        stat.executeUpdate(sb.toString());
    }

    public static String getMysqlSelet(String coun,String attr,String tab){
        StringBuffer sb = new StringBuffer();
        sb.append("select * from ");
        sb.append(tab);
        sb.append(" where ");
        sb.append(coun);
        sb.append(" = ");
        sb.append(attr);
        sb.append(";");
        return sb.toString();
    }

    private static void falseSqlMap(Player player){
        isMysql.put(player.getName(),false);
    }
    private static void trueSqlMap(Player player){
        isMysql.put(player.getName(),true);
    }
    public static boolean isSqlMap(Player player){
        return isMysql.containsKey(player.getName())?isMysql.get(player.getName()):false;
    }

    public static String getTab() {
        return tab;
    }
}
