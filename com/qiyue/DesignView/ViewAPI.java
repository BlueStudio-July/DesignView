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
	  * ��ʼ��
	  */
	public static void init() {
		v = new ViewAPI();
	}
	
	/**
	 * ��ȡAPI
	 * @return
	 */
	public static ViewAPI getAPI() {
		return v;
	}
	
	/**
	 * ��ȡGui�Ƿ����
	 * @param Gui�ļ���
	 * @return 
	 */
	public boolean haveGui(String guiName) {
		return Main.NetGui.containsKey(guiName);
	}
	

	/**
	 * 
	 * @param Gui�ļ���
	 * @param ���
	 * @param �Ƿ��жϴ�Ҫ��
	 */
    public void openGui(String guiName,Player p,boolean per) {
    	if(Main.instance.getConfig().getBoolean("noreload")) { Data.upAllGui(false);}
    		
    	Data.openGui(p,getGui(guiName),per);
    }
   
    
    /**
     * ��ȡGui��ID
     * @param Gui�ļ���
     * @return
     */
    public int getGuID(String guiName) {
    	return getGui(guiName).guiID;
    }
    
    
    /**
     * ��ȡGUI  
     * @param Gui�ļ���
     * @return
     */
    public NetWorkGui getGui(String guiName) {
    	return Main.NetGui.get(guiName);
    }
    
    /**
     * 
     * @return Gui�ļ��б�
     */
    public String[] getGuiList(){
    	return Main.guiFolder.list();
    }
    
    /**
     * ��ȡ������ڴ򿪵�gui��
     * û����null
     * @param p
     * @return null
     */
    public String getNowGui(Player p) {
    	return Main.playerOpen.get(p.getName());
    }
    
    /**
     * ��ȡ��ť��ȴʱ��
     * @param ���
     * @param gui��
     * @param ��ť��
     * @return ��ȴʱ�� ��λs
     
    public int getButtonCooldown(Player p, String guiName, String button) {
    	return Utils.getButtonCooldown(p, guiName, button);
    } /*
    
    /**
     * ��Ӱ�ť��ȴʱ��
     * @param ���
     * @param gui��
     * @param ��ť��
     * @param ���ӵ���ȴʱ�� ��λ:s
    
    public void addButtonCooldown(Player p, String guiName, String button, int cooldown) {
    	Utils.addButtonCooldown(p, guiName, button, cooldown);
    } /*
    
    /**
     * 
     * @param ���
     * @param ��ť�ڵ���
     * @return ��ť�����´���
     
    public int getClickButton(Player p , String guiName, String button) {
		return Utils.getButtonClick(p, guiName , button);
    }
    */
}
