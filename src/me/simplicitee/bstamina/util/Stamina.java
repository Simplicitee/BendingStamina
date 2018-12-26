package me.simplicitee.bstamina.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.BendingPlayer;

import me.simplicitee.bstamina.BendingStamina;

public class Stamina {
	
	private static final Map<BendingPlayer, Stamina> PLAYERS = new HashMap<>();
	private static final int MAX = BendingStamina.get().getConfig().getInt("Properties.MaxStamina");

	private BendingPlayer bPlayer;
	private int stamina;
	private int maxStamina;
	private int recharge;
	private StaminaBar bar;
	private BukkitRunnable recharger;
	
	private Stamina(BendingPlayer bPlayer, int stamina, int maxStamina) {
		this.bPlayer = bPlayer;
		this.stamina = stamina;
		this.maxStamina = maxStamina;
		this.bar = new StaminaBar(this);
		
		setMaxStamina(maxStamina);
		setStamina(stamina);
		
		this.recharge = BendingStamina.get().getConfig().getInt("Properties.BaseRecharge");
		this.recharger = new BukkitRunnable() {

			@Override
			public void run() {
				setStamina(getStamina() + getRecharge());
			}
			
		};
		startRecharge();
		
		PLAYERS.put(bPlayer, this);
		BendingStamina.get().getLogger().info("Created stamina for " + bPlayer.getUUIDString());
	}
	
	public BendingPlayer getBendingPlayer() {
		return bPlayer;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	public int getRecharge() {
		return recharge;
	}
	
	public StaminaBar getStaminaBar() {
		return bar;
	}
	
	public Stamina setStamina(int stamina) {
		if (stamina > maxStamina) {
			stamina = maxStamina;
		}
		
		this.stamina = stamina;
		this.bar.update();
		return this;
	}
	
	public Stamina setMaxStamina(int maxStamina) {
		if (maxStamina > MAX) {
			maxStamina = MAX;
		}
		
		this.maxStamina = maxStamina;
		
		if (this.stamina > maxStamina) {
			this.stamina = maxStamina;
		}
		
		this.bar.update();
		return this;
	}
	
	public Stamina setRecharge(int recharge) {
		this.recharge = recharge;
		this.bar.update();
		return this;
	}
	
	public void startRecharge() {
		recharger.runTaskTimer(BendingStamina.get(), 0, 20);
	}
	
	public void save() {
		PLAYERS.remove(bPlayer);
		recharger.cancel();
		bar.getBar().removePlayer(bPlayer.getPlayer());
	}
	
	public static void saveAll() {
		for (Stamina stamina : PLAYERS.values()) {
			stamina.save();
		}
	}
	
	public static Stamina get(BendingPlayer bPlayer) {
		if (bPlayer == null) {
			return null;
		}
		
		if (!PLAYERS.containsKey(bPlayer)) {
			createNew(bPlayer.getPlayer());
		}
		
		return PLAYERS.get(bPlayer);
	}
	
	public static Stamina createNew(Player player) {
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		
		if (bPlayer != null) {
			if (PLAYERS.containsKey(bPlayer)) {
				return PLAYERS.get(bPlayer);
			}
			
			return new Stamina(bPlayer, BendingStamina.get().getConfig().getInt("Properties.BaseMaxStamina"), BendingStamina.get().getConfig().getInt("Properties.BaseMaxStamina"));
		}
		
		return null;
	}
}
