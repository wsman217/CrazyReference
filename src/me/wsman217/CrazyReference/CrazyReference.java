package me.wsman217.CrazyReference;

import org.bukkit.plugin.java.JavaPlugin;

import me.wsman217.CrazyReference.commands.ReferAdminCommand;
import me.wsman217.CrazyReference.commands.ReferCommand;
import me.wsman217.CrazyReference.configTools.configManager;
import me.wsman217.CrazyReference.data.DataBase;
import me.wsman217.CrazyReference.data.DataHandler;
import me.wsman217.CrazyReference.listeners.ListenerPlayerJoin;
import me.wsman217.CrazyReference.listeners.OnMeJoin;
import me.wsman217.CrazyReference.tools.FileManager;
import me.wsman217.CrazyReference.tools.GiveRewards;
import me.wsman217.CrazyReference.tools.Metrics;

public class CrazyReference extends JavaPlugin {

	//This is the main class
	
	public configManager cMan;
	public GiveRewards giveRewards;
	private static CrazyReference instance;
	public Metrics bStats;
	private FileManager fileManager;
	
	private DataBase db;
	private DataHandler dh;

	@Override
	public void onEnable() {
		instance = this;
		bStats = new Metrics(this);
		
		db = new DataBase();
		dh = new DataHandler(db);
		
		saveDefaultConfig();
		db.openDatabaseConnection();
		dh.generateTables();
		
		fileManager = FileManager.getInstance().logInfo(true).setup(this);

		//Initialize some of my classes
		//Yes I know these shouldn't be public but I honestly didn't care to much
		cMan = new configManager(this);
		giveRewards = new GiveRewards(this);

		//Register events
		getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(this), this);
		getServer().getPluginManager().registerEvents(new OnMeJoin(this), this);
		
		//Register the command /refer
		getCommand("refer").setExecutor(new ReferCommand(this));
		getCommand("referadmin").setExecutor(new ReferAdminCommand(this));
	}

	@Override
	public void onDisable() {}
	
	public static CrazyReference getInstance() {
		return instance;
	}
	
	public FileManager getFileManager() {
		if (fileManager == null)
			throw new NullPointerException("File manger for plugin CrazyReference was null");
		return fileManager;
	}
	
	public DataHandler getDataHandler() {
		return dh;
	}
}