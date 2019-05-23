package me.wsman217.CrazyReference;

import org.bukkit.plugin.java.JavaPlugin;

import me.wsman217.CrazyReference.commands.ReferCommand;
import me.wsman217.CrazyReference.configTools.configManager;
import me.wsman217.CrazyReference.listeners.ListenerPlayerJoin;
import me.wsman217.CrazyReference.listeners.OnMeJoin;
import me.wsman217.CrazyReference.tools.GiveRewards;
import me.wsman217.CrazyReference.tools.Metrics;

public class CrazyReference extends JavaPlugin {

	//This is the main class
	
	public configManager cMan;
	public GiveRewards giveRewards;
	private static CrazyReference instance;
	public Metrics bStats;

	@Override
	public void onEnable() {
		instance = this;
		bStats = new Metrics(this);
		
		saveDefaultConfig();

		//Initialize some of my classes
		//Yes I know these shouldn't be public but I honestly didn't care to much
		cMan = new configManager(this);
		giveRewards = new GiveRewards(this);

		//Register events
		getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(this), this);
		getServer().getPluginManager().registerEvents(new OnMeJoin(this), this);
		
		//Register the command /refer
		getCommand("refer").setExecutor(new ReferCommand(this));
	}

	@Override
	public void onDisable() {}
	
	public static CrazyReference getInstance() {
		return instance;
	}
}