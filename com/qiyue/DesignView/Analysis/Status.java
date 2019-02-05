package com.qiyue.DesignView.Analysis;


import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Status {

	public static boolean isOK(String node,Player p) {
		if(node == null) return false;
		try {
			return (boolean) new ScriptEngineManager().getEngineByName("js").eval(PlaceholderAPI.setPlaceholders(p,node));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<String> getButtonStatus(int buttonID, FileConfiguration fc) {
		String node = Data.buttonData.get(buttonID);
		if(!fc.contains("items."+node+".clickstatus")) return new ArrayList<>();
		List<Integer> list = new ArrayList<>();
		List<String> lists = new ArrayList<>();
		for(String item : fc.getConfigurationSection("items."+node+".clickstatus.customstatus").getKeys(false)) 
			list.add(fc.getInt("items."+node+".clickstatus.customstatus."+item+".priority"));
	 	   for(int i = 0 ; i < list.size() ; i++) {
    		   if(i+1 == list.size()) break;
    		   for(int a = 0 ; a < list.size() ; a++) {
        		   if(a+1 == list.size()) break;
    		     if(list.get(a) <  list.get(a+1)) {
    			   int t = list.get(a+1);
    			   list.set(a+1,list.get(a));
    			   list.set(a,t);
    		     }
    		   }
	 	   }
	 	   
	 	   for(int i = 0 ; i < list.size() ; i++) {
	 			for(String item : fc.getConfigurationSection("items."+node+".clickstatus.customstatus").getKeys(false))  {
			   if(list.get(i) == fc.getInt("items."+node+".clickstatus.customstatus."+item+".priority"))
                  lists.add(item+"|"+list.get(i));
			   }
		   }
	 	   
	 	   
    		return lists;   
	}
	
	public static List<String> getGuiStatus(FileConfiguration fc) {
		if(!fc.contains("openstatus")) 
			return new ArrayList<>();
		List<Integer> list = new ArrayList<>();
		List<String> lists = new ArrayList<>();
		for(String item : fc.getConfigurationSection("openstatus").getKeys(false)) 
			list.add(fc.getInt("openstatus."+item+".priority"));

	 	   for(int i = 0 ; i < list.size() ; i++) {
    		   if(i+1 == list.size()) break;
    		   for(int a = 0 ; a < list.size() ; a++) {
        		   if(a+1 == list.size()) break;
    		     if(list.get(a) <  list.get(a+1)) {
    			   int t = list.get(a+1);
    			   list.set(a+1,list.get(a));
    			   list.set(a,t);
    		     }
    		   }
	 	   }
	 	   
	 	   for(int i = 0 ; i < list.size() ; i++) {
			   for(String item : fc.getConfigurationSection("openstatus").getKeys(false)) {
			   if(list.get(i) == fc.getInt("openstatus."+item+".priority"))
                  lists.add(item+"|"+list.get(i));
			   }
		   }
	 	   
	 	   
    		return lists;   
	}
	
	
}
