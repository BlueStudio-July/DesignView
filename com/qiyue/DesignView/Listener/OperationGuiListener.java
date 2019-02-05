package com.qiyue.DesignView.Listener;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.qiyue.DesignView.Main;
import com.qiyue.DesignView.ViewAPI;
import com.qiyue.DesignView.Analysis.Data;
import com.qiyue.DesignView.Analysis.Status;
import com.qiyue.DesignView.Analysis.Utils;

import custom.gui.api.API;
import custom.gui.event.CustomGuiButtonClickEvent;
import custom.gui.event.CustomGuiCloseEvent;
import custom.gui.networkgui.NetWorkGui;
import custom.gui.networkgui.NetWorkGuiObject;

public class OperationGuiListener implements Listener {

	@EventHandler
	public void onGuiButtonClick(CustomGuiButtonClickEvent event) {
		if(Data.getGuiName(event.getCustomGuiID()) == null) return;
		File f = new File("plugins\\DesignView\\GuiFolder\\" + Data.getGuiName(event.getCustomGuiID())+".yml");
		FileConfiguration fc = f.exists() ? YamlConfiguration.loadConfiguration(f) : new YamlConfiguration();
	  	List<String> list = Status.getButtonStatus(event.getCustomGuiButtonID(),fc);
		/*if(Utils.getButtonCooldown(event.getPlayer(),Data.getGuiName(event.getCustomGuiID()),Data.data.get(event.getCustomGuiButtonID())) != 0) {
		   API.closeNowGui(event.getPlayer());
		   Utils.sendbuttonColldownMessage(event.getPlayer(),String.valueOf(Utils.getButtonCooldown(event.getPlayer(),Data.getGuiName(event.getCustomGuiID()),Data.data.get(event.getCustomGuiButtonID()))));
	       return; 
		}*/		
    	for(int i = 0; i < list.size(); i++) {
    		String node = fc.getString("items." + Data.buttonData.get(event.getCustomGuiButtonID()) + ".clickstatus.customstatus."+list.get(i).split("\\|")[0]+".status");
    		if(!Status.isOK(node,event.getPlayer())) {
    			Data.scitCommands(fc.getString("items." + Data.buttonData.get(event.getCustomGuiButtonID()) + ".clickstatus.customstatus." + list.get(i).split("\\|")[0] + ".nobadCommands"),event.getPlayer());
    			return;
    		}
    	}
    	//Utils.addButtonClick(event.getPlayer(),Data.getGuiName(event.getCustomGuiID()),Data.data.get(event.getCustomGuiButtonID()));
    	Data.scitCommands(fc.getString("items."+Data.buttonData.get(event.getCustomGuiButtonID())+".clickstatus.commands"),event.getPlayer());
    	//if(fc.contains("items." + Data.data.get(event.getCustomGuiButtonID()) + ".clickstatus.cooldown"))
    	//Utils.addButtonCooldown(event.getPlayer(),Data.getGuiName(event.getCustomGuiID()),Data.data.get(event.getCustomGuiButtonID()),fc.getInt("items." + Data.data.get(event.getCustomGuiButtonID()) + ".clickstatus.cooldown"));
	}
	
   @EventHandler
   public void onCloseGui(CustomGuiCloseEvent event) {
	   if(Main.playerOpen.containsKey(event.getPlayer().getName()))
	   Main.playerOpen.put(event.getPlayer().getName(),null);
	   NetWorkGui gui = ViewAPI.getAPI().getGui(Data.getGuiName(event.getCustomGuiID()));
	   
   }
	
}
