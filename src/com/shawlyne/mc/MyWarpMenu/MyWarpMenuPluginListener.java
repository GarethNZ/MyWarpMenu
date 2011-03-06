package com.shawlyne.mc.MyWarpMenu;


import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;

import me.taylorkelly.mywarp.MyWarp;

public class MyWarpMenuPluginListener extends ServerListener {
	MyWarpMenu plugin;
	
	public MyWarpMenuPluginListener(MyWarpMenu instance) {
		plugin = instance;
	}

    @Override
    public void onPluginEnabled(PluginEvent event) {
        if(event.getPlugin().getDescription().getName().equalsIgnoreCase("MyWarp")) {
        	plugin.setMyWarp( (MyWarp)event.getPlugin() );
        	
           
        }
    }
    
    @Override
    public void onPluginDisabled(PluginEvent event) {
    	 if(event.getPlugin().getDescription().getName().equalsIgnoreCase("MyWarp")) {
    		plugin.setMyWarp( null );
           
         }
    }
}