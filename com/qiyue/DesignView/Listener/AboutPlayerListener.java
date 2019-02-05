package com.qiyue.DesignView.Listener;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.qiyue.DesignView.Main;
import com.qiyue.DesignView.ViewAPI;

public class AboutPlayerListener implements Listener {

	@EventHandler
	public void onplayerCommands(PlayerCommandPreprocessEvent event) {
		for(String cmd : Main.commandsList) {
			if(event.getMessage().equals("/"+cmd.split("\\|")[1])){
				ViewAPI.getAPI().openGui(cmd.split("\\|")[0],event.getPlayer(),true);
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onplayerJoin(PlayerJoinEvent event) {
	     Main.playerOpen.put(event.getPlayer().getName(),null);
	     //Main.clickButton.put(event.getPlayer().getName(),new ArrayList<>());
	     //Main.openGuiNumber.put(event.getPlayer().getName(),new ArrayList<>());
	}
	
    @SuppressWarnings("unlikely-arg-type")
	@EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Main.playerOpen.remove(event.getPlayer());
        //Main.clickButton.remove(event.getPlayer());
       // Main.openGuiNumber.remove(event.getPlayer());
         
    }
	
    @SuppressWarnings("unlikely-arg-type")
	public void onKickPlayer(PlayerKickEvent event) {
        Main.playerOpen.remove(event.getPlayer());
        //Main.clickButton.remove(event.getPlayer());
        //Main.openGuiNumber.remove(event.getPlayer());
    }
}
