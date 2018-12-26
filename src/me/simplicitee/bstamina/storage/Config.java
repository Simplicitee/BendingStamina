package me.simplicitee.bstamina.storage;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.simplicitee.bstamina.BendingStamina;

public class Config {

	private final BendingStamina plugin;

	private final File file;
	private final FileConfiguration config;

	public Config(final File file) {
		this.plugin = BendingStamina.get();
		this.file = new File(this.plugin.getDataFolder() + File.separator + file);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		this.reload();
	}

	public void create() {
		if (!this.file.getParentFile().exists()) {
			try {
				this.file.getParentFile().mkdir();
				this.plugin.getLogger().info("Generating new directory for " + this.file.getName() + "!");
			}
			catch (final Exception e) {
				this.plugin.getLogger().info("Failed to generate directory!");
				e.printStackTrace();
			}
		}

		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
				this.plugin.getLogger().info("Generating new " + this.file.getName() + "!");
			}
			catch (final Exception e) {
				this.plugin.getLogger().info("Failed to generate " + this.file.getName() + "!");
				e.printStackTrace();
			}
		}
	}

	public FileConfiguration get() {
		return this.config;
	}

	public void reload() {
		this.create();
		try {
			this.config.load(this.file);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			this.config.options().copyDefaults(true);
			this.config.save(this.file);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
