package com.mzimu.economics.listener;

import com.mzimu.economics.ZMPayAward;
import com.mzimu.economics.data.PlayPay;
import com.mzimu.economics.util.SqlUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayOnAction implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ZMPayAward.getPlugin().getServer().getScheduler().runTaskAsynchronously(
                ZMPayAward.getPlugin(),
                () -> {
                    PlayPay.initData(e.getPlayer());
                }
        );

    }


}
