package com.qiyue.DesignView.Analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.qiyue.DesignView.Main;
import com.qiyue.DesignView.ViewAPI;
import custom.gui.api.API;
import custom.gui.api.API.GuiType;
import custom.gui.networkgui.NetWorkGui;
import custom.gui.networkgui.NetWorkGuiButton;
import custom.gui.networkgui.NetWorkGuiField;
import custom.gui.networkgui.NetWorkGuiImage;
import custom.gui.networkgui.NetWorkGuiManager;
import custom.gui.networkgui.NetWorkGuiText;
import custom.gui.networkgui.NetWorkGuiUrlGif;

public class Data {

	public static HashMap<Integer,String> buttonData = new HashMap<Integer,String>();
	public static HashMap<Integer,String> fieldData = new HashMap<Integer,String>();
	public static HashMap<String,String> guiAllItems = new HashMap<String,String>();
	private static int contime = 0;
    
    public static void openGui(Player p,NetWorkGui gui,boolean per) {
    	if(per) {
    	File f = new File("plugins\\DesignView\\GuiFolder\\" + Data.getGuiName(gui.guiID)+".yml");
		FileConfiguration fc = f.exists() ? YamlConfiguration.loadConfiguration(f) : new YamlConfiguration();
    	List<String> list = Status.getGuiStatus(fc);
    	for(int i = 0; i < list.size(); i++) {
    		String node = fc.getString("openstatus."+list.get(i).split("\\|")[0]+".status");
    		if(!Status.isOK(node, p)) {
    			Data.scitCommands(fc.getString("openstatus."+list.get(i).split("\\|")[0]+".nobadCommands"),p);
    			return;
    		}
    	}
    	}
    	if(Data.getGuiType(gui) == null) API.openGui(p,gui); else { API.implantationGUI(p,gui,Data.getGuiType(gui));}
    	Main.playerOpen.put(p.getName(),Data.getGuiName(gui.guiID));
    	//Utils.addGuiOpenNumber(p,Data.getGuiName(gui.guiID),1);
    }
    
    public static GuiType getGuiType(NetWorkGui gui) {
		File f = new File("plugins\\DesignView\\GuiFolder\\" + Data.getGuiName(gui.guiID)+".yml");
		FileConfiguration fc = f.exists() ? YamlConfiguration.loadConfiguration(f) : new YamlConfiguration();
		if(fc.getString("type") == null) return null;
		if(GuiType.valueOf(fc.getString("type")) == null) return null;
		return GuiType.valueOf(fc.getString("type"));
    }
    
    public static void upAllGui(boolean fa) {
		Main.NetGui.clear();
		buttonData.clear();
		guiAllItems.clear();
		Main.instance.say("&b----------------------",fa);
		for(File f : Main.guiFolder.listFiles()) {
			if(!f.getName().substring(f.getName().lastIndexOf(".") + 1).equals("yml")) continue;
			Main.NetGui.put(f.getName().substring(0,f.getName().lastIndexOf(".")),upGui(f,f.getName().substring(0,f.getName().lastIndexOf("."))));
			Main.instance.say(f.getName()+" &b成功加载!",fa);
		}
		if(fa)
		Main.instance.say("&b----------------------",fa);
	}
	
	public static NetWorkGui upGui(File f,String guiName) {
		  FileConfiguration fc = f.exists() ? YamlConfiguration.loadConfiguration(f) : new YamlConfiguration();
		  int NetWorkGuiID = NetWorkGuiManager.distributeID();
		  NetWorkGui Gui = new NetWorkGui(NetWorkGuiID);
		  List<String> PriorityList = getPriorityList(fc);
		  contime = 0;
		  if(fc.getBoolean("default")) Gui.setUseDefaultBackground(true);
		  BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance,new Runnable() {
			@Override
			public void run() 
			{
				contime++;
			}
		  },20,20);
		  while(!PriorityList.isEmpty()) {
		     if(contime == 5) {
				  Main.instance.say("&c插件加载超时,已自动卸载! &a请重启服务器重新加载",true);
				  Bukkit.getPluginManager().disablePlugin(Main.instance);
				  break;
			  }
		  for(int i = 0 ; i < PriorityList.size() ; i++) {
			  String item = PriorityList.get(0).split("\\|")[0];
			  String priority = PriorityList.get(0).split("\\|")[1];
			  if(fc.getString("items."+item+".type").equals("button") && Integer.parseInt(priority) == (fc.getInt("items."+item+".priority"))) {
				  List<String> list = fc.getStringList("items."+item+".options");
				  String text = getSetOptions(list,"text").replace("&","§");
				  int x = Integer.parseInt(getSetOptions(list,"x"));
				  int y = Integer.parseInt(getSetOptions(list,"y"));
				  int width = Integer.parseInt(getSetOptions(list,"width"));
				  int NetWorkGuiButtonID = NetWorkGuiManager.distributeID();
				  NetWorkGuiButton button = new NetWorkGuiButton(text,NetWorkGuiButtonID,x,y,width,20);
				  buttonData.put(NetWorkGuiButtonID,item);
				  if(guiAllItems.containsKey(guiName)) 
					  guiAllItems.put(guiName,guiAllItems.get(guiName) + item+","+NetWorkGuiButtonID + "&");  
				  else
					  guiAllItems.put(guiName,item+","+NetWorkGuiButtonID + "&");  
				  Gui.objList.add(button);
				  Main.instance.debug("&a组件 &f" + item + " &a成功加载! &d优先级: &f" + priority);
				  PriorityList.remove(0);
				  continue;
			  }
			  if(fc.getString("items."+item+".type").equals("field") && Integer.parseInt(priority) == (fc.getInt("items."+item+".priority"))) {
				  List<String> list = fc.getStringList("items."+item+".options");
				  int x = Integer.parseInt(getSetOptions(list,"x"));
				  int y = Integer.parseInt(getSetOptions(list,"y"));
				  int width = Integer.parseInt(getSetOptions(list,"width"));
				  int height = Integer.parseInt(getSetOptions(list,"height"));
				  int max = Integer.parseInt(getSetOptions(list,"max"));
				  int NetWorkGuiFleldID = NetWorkGuiManager.distributeID();
				  NetWorkGuiField field = new NetWorkGuiField(NetWorkGuiFleldID,x,y,width,height,max);
				  if(guiAllItems.containsKey(guiName)) 
					  guiAllItems.put(guiName,guiAllItems.get(guiName) + item+","+NetWorkGuiFleldID + "&");  
				  else
					  guiAllItems.put(guiName,item+","+NetWorkGuiFleldID + "&");  
				  Gui.objList.add(field);
				  Main.instance.debug("&a组件 &f" + item + " &a成功加载! &d优先级: &f" + priority);
				  PriorityList.remove(0);
				  continue;
			  }
			  if(fc.getString("items."+item+".type").equals("image") && Integer.parseInt(priority) == (fc.getInt("items."+item+".priority"))) {
				  List<String> list = fc.getStringList("items."+item+".options");
				  String path = getSetOptions(list,"path");
				  int x = Integer.parseInt(getSetOptions(list,"x"));
				  int y = Integer.parseInt(getSetOptions(list,"y"));
				  int width = Integer.parseInt(getSetOptions(list,"width"));
				  int height = Integer.parseInt(getSetOptions(list,"height"));
				  int textureX = Integer.parseInt(getSetOptions(list,"textureX"));
				  int textureY = Integer.parseInt(getSetOptions(list,"textureY"));
				  float textureWidth = Integer.parseInt(getSetOptions(list,"textureWidth"));
				  float textureHeight = Integer.parseInt(getSetOptions(list,"textureHeight"));
				  int NetWorkGuiImageID = NetWorkGuiManager.distributeID();
				  NetWorkGuiImage image = new NetWorkGuiImage(path,NetWorkGuiImageID,x,y,textureX,textureY,width,height,textureWidth,textureHeight);
				  if(guiAllItems.containsKey(guiName)) 
					  guiAllItems.put(guiName,guiAllItems.get(guiName) + item+","+NetWorkGuiImageID + "&");  
				  else
					  guiAllItems.put(guiName,item+","+NetWorkGuiImageID + "&");  
				  Gui.objList.add(image);
				  Main.instance.debug("&a组件 &f" + item + " &a成功加载! &d优先级: &f" + priority);
				  PriorityList.remove(0);
				  continue;
			  }
			  if(fc.getString("items."+item+".type").equals("gif") && Integer.parseInt(priority) == (fc.getInt("items."+item+".priority"))) {
				  List<String> list = fc.getStringList("items."+item+".options");
				  String path = getSetOptions(list,"path");
				  int x = Integer.parseInt(getSetOptions(list,"x"));
				  int y = Integer.parseInt(getSetOptions(list,"y"));
				  int width = Integer.parseInt(getSetOptions(list,"width"));
				  int height = Integer.parseInt(getSetOptions(list,"height"));
				  int textureX = Integer.parseInt(getSetOptions(list,"textureX"));
				  int textureY = Integer.parseInt(getSetOptions(list,"textureY"));
				  int speed = Integer.parseInt(getSetOptions(list,"speed"));
				  float textureWidth = Integer.parseInt(getSetOptions(list,"textureWidth"));
				  float textureHeight = Integer.parseInt(getSetOptions(list,"textureHeight"));
				  int NetWorkGuiGifID = NetWorkGuiManager.distributeID();
				  NetWorkGuiUrlGif gif = new NetWorkGuiUrlGif(path,NetWorkGuiGifID,speed,x,y,textureX,textureY,width,height,textureWidth,textureHeight);
				  if(guiAllItems.containsKey(guiName)) 
					  guiAllItems.put(guiName,guiAllItems.get(guiName) + item+","+NetWorkGuiGifID + "&");  
				  else
					  guiAllItems.put(guiName,item+","+NetWorkGuiGifID + "&");  
				  Gui.objList.add(gif);
				  Main.instance.debug("&a组件 &f" + item + " &a成功加载! &d优先级: &f" + priority);
				  PriorityList.remove(0);
				  continue;
			  }
			  if(fc.getString("items."+item+".type").equals("text") && Integer.parseInt(priority) == (fc.getInt("items."+item+".priority"))) {
				  List<String> list = fc.getStringList("items."+item+".options");
				  String str = getSetOptions(list,"str").replace("&","§");
				  int x = Integer.parseInt(getSetOptions(list,"x"));
				  int y = Integer.parseInt(getSetOptions(list,"y"));
				  int NetWorkGuiTextID = NetWorkGuiManager.distributeID();
				  NetWorkGuiText text = new NetWorkGuiText(str,NetWorkGuiTextID,x,y,0xFFFFFF);
				  if(guiAllItems.containsKey(guiName)) 
					  guiAllItems.put(guiName,guiAllItems.get(guiName) + item+","+NetWorkGuiTextID + "&");  
				  else
					  guiAllItems.put(guiName,item+","+NetWorkGuiTextID + "&");  
				  Gui.objList.add(text);
				  Main.instance.debug("&a组件 &f" + item + " &a成功加载! &d优先级: &f" + priority);
				  PriorityList.remove(0);
				  continue;
			  }
			  task.cancel();
		 }
	}
		  return Gui;
    }
	
	public static String getSetOptions(List<String> l,String n) {
		for(String options : l) {
			if(!options.contains("=")) continue;
			String[] str = options.split("=");
			if(str[0].equals(n)) 
			return options.substring(n.length()+1,options.length());
		}
		return null;
	}
	
	
	public static String getGuiName(int id) {
		for(File f : Main.guiFolder.listFiles()) {
			if(!f.getName().substring(f.getName().lastIndexOf(".") + 1).equals("yml")) continue;
			if(id == ViewAPI.getAPI().getGuID(f.getName().substring(0,f.getName().lastIndexOf(".")))){
				return f.getName().substring(0,f.getName().lastIndexOf("."));
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static void scitCommands(String commands,Player p) {
		if(commands == null) return;
		new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 0 ; i < Utils.getStringSame(commands,"[") ; i++) {
					  String command = commands.substring(Utils.getStringByte(commands,"[",i+1)+1,Utils.getStringByte(commands,"]",i+1));
					  command = command.split(":")[0] + ":" + Utils.relpace(p,command.split(":")[1]);
					  String[] str = command.split(":");
					  if(str[0].equals("plugin") && str[1].equals("close")) {
						  API.closeNowGui(p);
					      continue;
					  }
					  if(str[0].equals("plugin") && str[1].length() >= 5 && str[1].substring(0,5).equals("send ") && str[1].length() > 5) {
						  p.sendMessage(str[1].substring(5,str[1].length()));
				          continue;
					}
					  if(str[0].equals("op") || str[0].equals("console") || str[0].equals("player")) {
						 Bukkit.getScheduler().runTask(Main.instance,new Runnable() {

							@Override
							public void run() {
								 Utils.disictCommands(str[0],p,str[1]);
							}
							 
						 });
					  }
					  if(str[0].equals("plugin") || str[1].length() >= 5 && str[1].substring(0,5).equals("time@") && Utils.isNumber(str[1].substring(5,str[1].length()))){
						  Main.instance.debug("&f"+p.getName()+" &a进入延时指令. &c" + str[1].substring(5,str[1].length()) +"s");
						  try {
							Thread.sleep(Integer.parseInt(str[1].substring(5,str[1].length())) * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		                  Main.instance.debug("&f"+p.getName()+" &a延时执行指令完毕.");
						  continue;
					  }
					}
			}
			
		}).start();

		
	}
	
	   public static int getItemsID(String guiName, String itemName) {
		     String s = guiAllItems.get(guiName);
		     for(String a : s.split("&")) {
		    	 if(a.split(",")[0].equals(itemName))
		    		 return Integer.parseInt(a.split(",")[1]);
		     }
		     return -1;
	   }
	
       public static List<String> getPriorityList(FileConfiguration fc){
    	   List<Integer> list = new ArrayList<>();
       	   List<String> lists = new ArrayList<>();
    	   for(String item : fc.getConfigurationSection("items").getKeys(false)) 
    		list.add(fc.getInt("items." + item + ".priority"));
    	
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
    			   for(String item : fc.getConfigurationSection("items").getKeys(false)) {
    			   if(list.get(i) == fc.getInt("items."+item+".priority"))
                      lists.add(item+"|"+list.get(i));
    			   }
    		   }
    		  
    	   return lists;
       }
	
}
