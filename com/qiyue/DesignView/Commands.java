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
				 sender.sendMessage("§d/dv open <guiname> 打开Gui");
				 sender.sendMessage("§d/dv close <guiname> 关闭当前Gui");
				 if(sender.isOp()) {
					 sender.sendMessage("§d/dv open <guiname> <player> <boolean> 为玩家打开Gui");
					 sender.sendMessage("§d/dv close <player> 为玩家关闭Gui");
					 sender.sendMessage("§d/dv updata         刷新全服玩家的Gui");
					 sender.sendMessage("§d/dv list            列出所有可用Gui");
					 sender.sendMessage("§d/dv reload 重载文件");
				 }
				 sender.sendMessage("§fthe plugin version is 1.0.0-beta");
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
				 sender.sendMessage("§a已重载成功");
				 return true;
			 }
			 
			 if(args[0].equalsIgnoreCase("open") && args.length == 4 && p != null && p.isOp()) {
				 ViewAPI api = ViewAPI.getAPI();
				 Player p1 = Bukkit.getPlayer(args[2]);
				 if(p1 == null) {
					 p.sendMessage("§c该玩家不在线或压根不存在!");
					 return true;
				 }
				 if(api.haveGui(args[1])) api.openGui(args[1],p1,Boolean.parseBoolean(args[3]));
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("list") && args.length == 1 && p != null && p.isOp()) {
				 p.sendMessage("§a可用Gui: ");
				 if(Main.NetGui.size() == 0) {
					 p.sendMessage("- 无");
				 }
				 for(String menu : Main.NetGui.keySet()) {
					 p.sendMessage("- "+menu);
				 }
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("close") && args.length == 2 && p != null && p.isOp()) {
				 Player p1 = Bukkit.getPlayer(args[1]);
				 if(p1 == null || !Main.playerOpen.containsKey(p1.getName()) || Main.playerOpen.get(p1.getName()) == null) {
					 p.sendMessage("§c该玩家不在线或压根不存在! &6或该玩家并没有打开Gui");
					 return true;
				 }
				 API.closeNowGui(p1);
				 p.sendMessage("§c已为玩家关闭");
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("close") && args.length == 1 && p != null && p.isOp()) {
				 if(!Main.playerOpen.containsKey(p.getName()) || Main.playerOpen.get(p.getName()) == null) {
					 p.sendMessage("§c你并没有打开Gui");
					 return true;
				 }
				 API.closeNowGui(p);
				 p.sendMessage("§c已关闭");
				 return true;
			 }
			 if(args[0].equalsIgnoreCase("updata") && args.length == 1 && p != null && p.isOp()) {
				 Utils.upServerPlayer();
				 p.sendMessage("§a刷新成功!");
				 return true;
			 }
		}
		
		return false;
	}

}
