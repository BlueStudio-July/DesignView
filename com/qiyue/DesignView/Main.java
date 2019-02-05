package com.qiyue.DesignView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.qiyue.DesignView.Analysis.Data;
import com.qiyue.DesignView.Analysis.Utils;
import com.qiyue.DesignView.Listener.AboutPlayerListener;
import com.qiyue.DesignView.Listener.OperationGuiListener;
import custom.gui.networkgui.NetWorkGui;
import custom.gui.networkgui.NetWorkGuiManager;

//author: July_Summer 
//qq: 1780265033
public class Main extends JavaPlugin{
    
	public static Main instance = null;
	public static int slot = 0;
	public static FileConfiguration config = null;
	public static int authmeID = NetWorkGuiManager.distributeID();
	public static NetWorkGui authme = new NetWorkGui(authmeID);
	public static final File guiFolder = new File("plugins\\DesignView\\GuiFolder");
	private final File configFile = new File("plugins\\DesignView\\config.yml");
	public static final HashMap<String,NetWorkGui> NetGui = new HashMap<String,NetWorkGui>();
	public static final HashMap<String,String> playerOpen = new HashMap<String,String>();
	public static final List<String> commandsList = new ArrayList<>();
	public static final HashMap<String,List<String>> cooldown = new HashMap<String,List<String>>();
	//public static final HashMap<String,Integer> fidplayer = new HashMap<String,Integer>();
	//public static final HashMap<String,List<String>> clickButton = new HashMap<String,List<String>>();
	//public static final HashMap<String,List<String>> openGuiNumber = new HashMap<String,List<String>>();
	
	public void onEnable() {
		instance = this;
		instance.init();
		ViewAPI.init();
		this.onloadCooldown();
		new ViewPlaceholder(this,"dview").hook();
		getCommand("dv").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new OperationGuiListener(),this);
		getServer().getPluginManager().registerEvents(new AboutPlayerListener(),this);
		instance.say("&aDesignView已加载完毕",true);
	}
	
	public void initCommands() {
		commandsList.clear();
		for(File f : instance.guiFolder.listFiles()) {
			FileConfiguration fc = f.exists() ? YamlConfiguration.loadConfiguration(f) : new YamlConfiguration();
			if(fc.contains("opencmd")) {
				if(fc.getString("opencmd").contains(",")) {
					for(String s : fc.getString("opencmd").split(",")) {
						commandsList.add(f.getName().substring(0,f.getName().lastIndexOf("."))+"|"+s);
					}
				}
				commandsList.add(f.getName().substring(0,f.getName().lastIndexOf("."))+"|"+fc.getString("opencmd"));
			}
		}
	}
	
	public void initUrl() {
		for(String url : getConfig().getStringList("UrlImages")) {
			if(!NetWorkGuiManager.imageUrls.contains(url))
				NetWorkGuiManager.addImageURL(url);
		}
		for(String url : getConfig().getStringList("UrlGifts")) {
			if(!NetWorkGuiManager.gifUrls.contains(url))
				NetWorkGuiManager.addGifURL(url);
		}
	}
	
	
	public void init() {
		if(!this.getDataFolder().exists()) {
			this.getDataFolder().mkdirs();
		}
		if(!configFile.exists()) {
			this.saveDefaultConfig();
			this.reloadConfig();
		}
		if(!guiFolder.exists()) {
			this.saveResource("GuiFolder\\example.yml",false);
		}
		config = getConfig();
        instance.initCommands();
        instance.initUrl();
		Data.upAllGui(true);
		/*if(!authmeFile.exists()) {
		this.saveResource("authme.yml",true);
		}
		config = getConfig();
		if(have("AuthMe") && config.getBoolean("AuthMeView")) {
			instance.say("&aAuthMe界面已加载");
			if(!instance.AuthmeLoad) { AuthmeLoad = true;
		       Bukkit.getServer().getPluginManager().registerEvents(new AuthmeLogin(this),this);
			}
			AuthmeView.init(instance);
		}*/

	}
	
	public void onloadCooldown() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(this,new Runnable() {

			@Override
			public void run() {
				for(String node : Main.cooldown.keySet()) {
					List<String> list = Main.cooldown.get(node);
					for(int i = 0; i < list.size() ; i++) {
						if(Integer.parseInt(list.get(i).split(":")[2]) == 0 || Integer.parseInt(list.get(i).split(":")[2]) - 1 == 0)
						    list.remove(i);
						else
						    list.set(i,list.get(i).split(":")[0]+":"+list.get(i).split(":")[1]+":"+ (Integer.parseInt(list.get(i).split(":")[2]) - 1));
					}
					Main.cooldown.put(node,list);
				}
			}
			
		},1,20);
	}
	
	public boolean have(String name) {
		for(Plugin n : Bukkit.getPluginManager().getPlugins()) {
		if(n.getName().equals(name)) return true; }  return false;
	}
	
	
	public void say(String message,boolean fa) {
		if(fa)
		Bukkit.getConsoleSender().sendMessage(("&f[DesignView] " + message).replace("&","§"));
	}
	
	public void debug(String message) {
		if(config.getBoolean("debug"))
	    Bukkit.getConsoleSender().sendMessage(("&c[Debug] " + message).replace("&","§"));
	}
	
}
