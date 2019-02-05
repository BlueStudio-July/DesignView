package com.qiyue.DesignView.Analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.qiyue.DesignView.Main;
import com.qiyue.DesignView.ViewAPI;

import custom.gui.api.API;
import me.clip.placeholderapi.PlaceholderAPI;

public class Utils {

	public static void upServerPlayer() {
		for(OfflinePlayer p : Bukkit.getOfflinePlayers())
		{
			Player ps = (Player) p;
			if(Main.playerOpen.get(ps.getName()) != null) {
				API.closeNowGui(ps);
				ViewAPI.getAPI().openGui(Main.playerOpen.get(ps.getName()),ps,false);
			}
		}
	}
	
	public static int getStringByte(String n,String s,int ia) {
		int a = 0;
		for(int i = 0 ; i < n.length() ; i++) {
			if(n.substring(i,i+1).equals(s)) {
				a++;
				if(a == ia) {
					return i;
				}
			}
		}
		return a;
	}
	
	public static int getStringSame(String n,String s) {
		int a = 0;
		for(int i = 0 ; i < n.length() ; i++) {
			if(n.substring(i,i+1).equals(s)) {
				a++;
			}
		}
		return a;
	}
	
	public static boolean isNumber(String str){
          Pattern pattern = Pattern.compile("[0-9]*");
          Matcher isNum = pattern.matcher(str);
          return isNum.matches();
   }
	
	public static void disictCommands(String type,Player p,String commands) {
		if(type.equals("op")) {
			boolean a = p.isOp();
			if(!a) p.setOp(true);
			p.chat("/"+commands);
			p.setOp(a);
		}
		if(type.equals("console"))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),commands);
		if(type.equals("player"))
			p.chat("/"+commands);
	}
	
	public static String relpace(Player p , String str) {
		str = PlaceholderAPI.setPlaceholders(p,str);
		str = str.replace("{zk*1}","\\[").replace("{zk*2}","\\]").replace("{xk*1}","\\{").replace("{xk*2}","\\}").replace("{ws*0}",":").replace("{player}",p.getName()).replace("&","¡ì");
		return str;
	}
	
	public static void sendbuttonColldownMessage(Player p,String s) {
		p.sendMessage("¡ìc¸Ã°´Å¥ÀäÈ´ÖÐ  Ê£Óà: ¡ìf"+s+"s");
	}
	
	public static int getButtonCooldown(Player p, String guiName, String button) {
		if(!Main.cooldown.containsKey(p.getName())) 
			return 0;
		else {
			if(getStringList(Main.cooldown.get(p.getName()),guiName) == -1) {
				return 0;
			} else 
				return Integer.parseInt(Main.cooldown.get(p.getName()).get(getStringList(Main.cooldown.get(p.getName()),guiName+":"+button)).split(":")[2]);
		}
	}
	
	public static void addButtonCooldown(Player p, String guiName, String button, int cooldown) {
		if(!Main.cooldown.containsKey(p.getName())) 
			Main.cooldown.put(p.getName(),addList(new ArrayList<>(),guiName+":"+button+":"+cooldown));
		else {
			if(getStringList(Main.cooldown.get(p.getName()),guiName) == -1) {
				Main.cooldown.put(p.getName(),addList(Main.cooldown.get(p.getName()),guiName+":"+button+":"+cooldown));
			} else {
				List<String> list = Main.cooldown.get(p.getName());
				System.out.print(list);
			}
		}
			
	} 
	
	public static List<String> addList(List<String> list,String tj){
		boolean b = false;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).split(":")[0].equals(tj.split(":")[0])) {
				list.set(i,tj);
				b = true;
			}
		}
		if(!b) {
			list.add(tj);
		}
		    return list;
	}
	/*
	
	public static int getGuiOpenNumber(Player p, String guiName) {
		 
	}
	
	
	public static void addGuiOpenNumber(Player p,String guiName,int n) {
       
	}
	
	public static int getButtonClick(Player p, String guiName, String button) {
		
	}
	
	public static void addButtonClick(Player p,String guiName,String button) {
		
	}
	*/
	public static int getStringList(List<String> list,String node) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(node))
				return i;
		}
		return -1;
	}
	
	
}
