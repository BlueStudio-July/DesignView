package com.qiyue.DesignView;

import java.util.List;

import org.bukkit.entity.Player;

import com.qiyue.DesignView.Analysis.Data;
import com.qiyue.DesignView.Analysis.Utils;

import custom.gui.api.API;
import custom.gui.networkgui.NetWorkGui;


public class ViewAPI {

	static ViewAPI v = null;
	
	
	 /**
	  * 初始化
	  */
	public static void init() {
		v = new ViewAPI();
	}
	
	/**
	 * 获取API
	 * @return
	 */
	public static ViewAPI getAPI() {
		return v;
	}
	
	/**
	 * 获取Gui是否存在
	 * @param Gui文件名
	 * @return 
	 */
	public boolean haveGui(String guiName) {
		return Main.NetGui.containsKey(guiName);
	}
	

	/**
	 * 
	 * @param Gui文件名
	 * @param 玩家
	 * @param 是否判断打开要求
	 */
    public void openGui(String guiName,Player p,boolean per) {
    	if(Main.instance.getConfig().getBoolean("noreload")) { Data.upAllGui(false);}
    		
    	Data.openGui(p,getGui(guiName),per);
    }
   
    
    /**
     * 获取Gui的ID
     * @param Gui文件名
     * @return
     */
    public int getGuID(String guiName) {
    	return getGui(guiName).guiID;
    }
    
    
    /**
     * 获取GUI  
     * @param Gui文件名
     * @return
     */
    public NetWorkGui getGui(String guiName) {
    	return Main.NetGui.get(guiName);
    }
    
    /**
     * 
     * @return Gui文件列表
     */
    public String[] getGuiList(){
    	return Main.guiFolder.list();
    }
    
    /**
     * 获取玩家正在打开的gui名
     * 没有则null
     * @param p
     * @return null
     */
    public String getNowGui(Player p) {
    	return Main.playerOpen.get(p.getName());
    }
    
    /**
     * 获取按钮冷却时间
     * @param 玩家
     * @param gui名
     * @param 按钮名
     * @return 冷却时间 单位s
     
    public int getButtonCooldown(Player p, String guiName, String button) {
    	return Utils.getButtonCooldown(p, guiName, button);
    } /*
    
    /**
     * 添加按钮冷却时间
     * @param 玩家
     * @param gui名
     * @param 按钮名
     * @param 增加的冷却时间 单位:s
    
    public void addButtonCooldown(Player p, String guiName, String button, int cooldown) {
    	Utils.addButtonCooldown(p, guiName, button, cooldown);
    } /*
    
    /**
     * 
     * @param 玩家
     * @param 按钮节点名
     * @return 按钮被按下次数
     
    public int getClickButton(Player p , String guiName, String button) {
		return Utils.getButtonClick(p, guiName , button);
    }
    */
}
