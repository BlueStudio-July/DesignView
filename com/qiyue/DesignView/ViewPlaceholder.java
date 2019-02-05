package com.qiyue.DesignView;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.qiyue.DesignView.Analysis.Data;
import com.qiyue.DesignView.Analysis.Utils;

import custom.gui.api.API;
import me.clip.placeholderapi.external.EZPlaceholderHook;

public class ViewPlaceholder extends EZPlaceholderHook{

	@SuppressWarnings("deprecation")
	public ViewPlaceholder(Plugin plugin, String identifier) {
		super(plugin, identifier);
	}

	@Override
	public String onPlaceholderRequest(Player p, String suffix) { 
	  if(suffix.contains("field_") && suffix.split("_").length == 3) {
		   return API.getField(p,Data.getItemsID(suffix.split("_")[1],suffix.split("_")[2]));
	  }
	 /**  if(suffix.contains("click"))
		   return String.valueOf(Utils.getButtonClick(p,suffix.split("_")[0],suffix.split("_")[1]));
	   if(suffix.contains("open"))
		   return String.valueOf(Utils.getGuiOpenNumber(p,suffix.split("&")[1]));
	   //else
	       return new String();*/
		return new String();
		}
}
