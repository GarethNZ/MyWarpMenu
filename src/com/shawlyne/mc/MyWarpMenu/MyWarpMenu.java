package com.shawlyne.mc.MyWarpMenu;

import java.util.ArrayList;

import me.taylorkelly.mywarp.MyWarp;
import me.taylorkelly.mywarp.WarpList;
import  me.taylorkelly.mywarp.Warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import com.shawlyne.mc.MenuMetaMod.MenuMetaMod;
import com.shawlyne.mc.MenuMetaMod.MetaModMenu;

/**
 * MyWarpMenu for Bukkit
 *
 * @author GarethNZ
 */
public class MyWarpMenu extends JavaPlugin {
	private final MyWarpMenuPluginListener pluginListener = new MyWarpMenuPluginListener(this);
	
	private MyWarp mywarp;
	private WarpList warpList;
	
    public void onEnable() {
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLUGIN_DISABLE, pluginListener, Priority.Monitor, this);
		
        Plugin p = pm.getPlugin("MyWarp");        
        if( p == null )
        	setMyWarp( null );
        else
        	setMyWarp( (MyWarp)p );
        	
        
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " loaded.");
    }
    
    public void onDisable() {
        // NOTE: All registered events are automatically unregistered when a plugin is disabled

    	PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled." );
    }
    
    public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
    		
    	if( sender instanceof Player )
    	{
    		Player player = (Player) sender;
    		if (command.getName().equalsIgnoreCase("warpmenu") || command.getName().equalsIgnoreCase("wm")) {
    			if( mywarp == null ){ 
    				System.out.println("mywarp == null :(");
    				return false; // unable to handle
    			}
    			// Make menu
    			ArrayList<String> options = new ArrayList<String>();
    			ArrayList<String> commands = new ArrayList<String>();

    			ArrayList<Warp> sortedWarps = warpList.getSortedWarps(player, 0, 99);
    			
    			for(Warp warp: sortedWarps) {
    				String name = warp.name;
    				String creator = (warp.creator.equalsIgnoreCase(player.getName()))?"you":warp.creator;
    				String color;
    				if(warp.playerIsCreator(player.getName())) {
    					color = ChatColor.AQUA.toString();
    				} else if(warp.publicAll) {
    					color = ChatColor.GREEN.toString();
    				} else {
    					color = ChatColor.RED.toString();
    				}

    				String creatorString = (warp.publicAll?"(+)":"(-)") + " by " + creator;

    				name = "'" + name + "'" + ChatColor.WHITE + creatorString;
    				options.add(color + name);
    				commands.add("warp "+warp.name);
    				
    			}
    			String[] opts = new String[1];
    			opts = options.toArray(opts);
    			String[] comms = new String[1];
    			comms = commands.toArray(comms);
    			MenuMetaMod.sendMenu(player, new MetaModMenu("My Warp", opts, comms) );
    			return true;
    		}
    	}
    	return false;
    	
    }

	public void setMyWarp(MyWarp plugin) {
		if( plugin == null )
		{
			mywarp = null;
			warpList = null;
			System.out.println("MyWarpMenu: Detached from MyWarp. Disabled.");
		}
		else
		{
			mywarp = plugin;
			warpList = new WarpList(getServer());
			System.out.println("MyWarpMenu: Attached to MyWarp. Enabled.");
		}
	}
    
}

