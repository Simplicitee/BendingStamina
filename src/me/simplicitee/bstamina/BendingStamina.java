package me.simplicitee.bstamina;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.simplicitee.bstamina.storage.Config;
import me.simplicitee.bstamina.storage.StorageLoader;
import me.simplicitee.bstamina.util.Stamina;

public class BendingStamina extends JavaPlugin{
	
	private static BendingStamina plugin;
	private Config config;
	private StorageLoader storage;

	@Override
	public void onEnable() {
		plugin = this;
		config = new Config(new File("stamina_config.yml"));
		
		FileConfiguration c = config.get();
		c.addDefault("Properties.MaxStamina", 5000);
		c.addDefault("Properties.BaseMaxStamina", 1000);
		c.addDefault("Properties.BaseRecharge", 50);
		
		config.save();
		storage = new StorageLoader();
		
		getServer().getPluginManager().registerEvents(new BendingStaminaListener(), this);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			Stamina.createNew(player);
		}
		plugin.getLogger().info("Fully loaded!");
	}
	
	@Override
	public void onDisable() {
		Stamina.saveAll();
	}
	
	public static BendingStamina get() {
		return plugin;
	}
	
	public Config config() {
		return config;
	}
	
	@Override
	public FileConfiguration getConfig() {
		return config.get();
	}
	
	public StorageLoader getStorage() {
		return storage;
	}
}
