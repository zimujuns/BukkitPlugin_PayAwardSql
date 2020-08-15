package com.mzimu.economics.command;

import com.mzimu.economics.ZMPayAward;
import com.mzimu.economics.util.ZMConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnCommandMain implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(p.hasPermission("ZMPayAward.use") || p.hasPermission("ZMPayAward.admin.use")){
                try {
                    switch (strings[0]){
                        case "Give":
                            if(strings.length > 2){
                                Player sP = Bukkit.getPlayer(strings[2]);
                                if(sP==null){
                                    p.sendMessage(ZMConfig.getNo_Play());
                                    return false;
                                }
                                GiveBoxCommand.build(strings[1],sP);
                            }else{
                                GiveBoxCommand.build(strings[1],p);
                            }
                            return true;
                        case "setHD":
                            if(p.hasPermission("ZMPayAward.admin")){
                                SetHDLocationCommand.build(p);
                                return true;
                            }
                            return false;
                        case "reload":
                            if(p.hasPermission("ZMPayAward.admin")){
                                ZMPayAward.onR();
                                System.out.println("插件重载完成");
                                return true;
                            }
                            return false;
                        default:
                            playHelp(p);
                            return false;
                    }
                }catch (ArrayIndexOutOfBoundsException nE){
                    playHelp(p);
                }
            }
        }else{
            try{
                switch (strings[0]){
                    case "Give":
                        GiveBoxCommand.build(strings[1],Bukkit.getPlayer(strings[2]));
                        return true;
                    case "reload":
                        ZMPayAward.onR();
                        System.out.println("插件重载完成");
                        return true;
                }
            }catch (ArrayIndexOutOfBoundsException nE){
                cmdHelp();
            }

        }

        return false;
    }


    public void cmdHelp(){
        System.out.println("ZMPayAward Give [礼包名称] [玩家]");
    }

    public void playHelp(Player p){
        if(p.isOp()){
            p.sendMessage("ZMPayAward Give [礼包名称] [玩家] //给指定玩家礼包");
            p.sendMessage("ZMPayAward Give [礼包名称] [玩家] //给玩家礼包");
            p.sendMessage("ZMPayAward setHD //设置记分板[暂时没用]");
        }
    }

}
