package com.qiyue.DesignView;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.qiyue.DesignView.Analysis.Utils;

import custom.gui.api.API;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(lable.equalsIgnoreCase("dv") && sender instanceof Player) {
			 Player p = (Player) sender;
			 if(args.length == 0) {
				 sender.sendMessage("��d/dv open <guiname> ��Gui");
				 sender.sendMessage("��d/dv close <guiname> �رյ�ǰGui");
				 if(sender.isOp()) {
					 sender.sendMessage("��d/dv open <guiname> <player> <boolean> Ϊ��Ҵ�Gui");
					 sender.sendMessage("��d/dv close <player> Ϊ��ҹر�Gui");
					 sender.sendMessage("��d/dv updata         ˢ��ȫ����ҵ�Gui");
					 sender.sendMessage("��d/dv list            �г����п���Gui");
					 sender.sendMessage("��d/dv reload �����ļ�");
				 }
				 sender.sendMessage("��fthe plugin version is 1.0.0-beta");
	             return true;
			 }
			 if(args[0].equalsIgnoreCase("open") && args.length == 2 && p != null) {
				 ViewAPI api = ViewAPI.getAPI();
				 if(api.haveGui(args[1])) api.openGui(args[1],p,true);
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("reload") && args.length == 1 && p != null && p.isOp()) {
				 Main.instance.init();
				 if(Main.config.getBoolean("upServerPlayer"))
					 Utils.upServerPlayer();
				 sender.sendMessage("��a�����سɹ�");
				 return true;
			 }
			 
			 if(args[0].equalsIgnoreCase("open") && args.length == 4 && p != null && p.isOp()) {
				 ViewAPI api = ViewAPI.getAPI();
				 Player p1 = Bukkit.getPlayer(args[2]);
				 if(p1 == null) {
					 p.sendMessage("��c����Ҳ����߻�ѹ��������!");
					 return true;
				 }
				 if(api.haveGui(args[1])) api.openGui(args[1],p1,Boolean.parseBoolean(args[3]));
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("list") && args.length == 1 && p != null && p.isOp()) {
				 p.sendMessage("��a����Gui: ");
				 if(Main.NetGui.size() == 0) {
					 p.sendMessage("- ��");
				 }
				 for(String menu : Main.NetGui.keySet()) {
					 p.sendMessage("- "+menu);
				 }
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("close") && args.length == 2 && p != null && p.isOp()) {
				 Player p1 = Bukkit.getPlayer(args[1]);
				 if(p1 == null || !Main.playerOpen.containsKey(p1.getName()) || Main.playerOpen.get(p1.getName()) == null) {
					 p.sendMessage("��c����Ҳ����߻�ѹ��������! &6�����Ҳ�û�д�Gui");
					 return true;
				 }
				 API.closeNowGui(p1);
				 p.sendMessage("��c��Ϊ��ҹر�");
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("close") && args.length == 1 && p != null && p.isOp()) {
				 if(!Main.playerOpen.containsKey(p.getName()) || Main.playerOpen.get(p.getName()) == null) {
					 p.sendMessage("��c�㲢û�д�Gui");
					 return true;
				 }
				 API.closeNowGui(p);
				 p.sendMessage("��c�ѹر�");
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("updata") && args.length == 1 && p != null && p.isOp()) {
				 Utils.upServerPlayer();
				 p.sendMessage("��aˢ�³ɹ�!");
				 return true;
			 }
		}
		
		return false;
	}

}
