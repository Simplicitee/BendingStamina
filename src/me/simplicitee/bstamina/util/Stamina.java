package me.simplicitee.bstamina.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

import me.simplicitee.bstamina.BendingStamina;
import me.simplicitee.bstamina.ability.StaminaAbility;
import me.simplicitee.bstamina.ability.StaminaAbility.StaminaEffect;

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
	
	public boolean activateEffect(StaminaAbility abil, CoreAbility ability) {
		BukkitRunnable run = null;
		long timer = 1;

		if (abil.getEffect() == StaminaEffect.USE) {
			int diff = getStamina() - abil.getValue();

			if (diff >= 0) {
				setStamina(diff);
			} else {
				return false;
			}
		} else if (abil.getEffect() == StaminaEffect.DECREASE_RECHARGE) {
			final int diff = getRecharge() - abil.getValue();

			if (diff >= 0) {
				setRecharge(diff);
				run = new BukkitRunnable() {

					@Override
					public void run() {
						if (ability.isRemoved()) {
							setRecharge(getRecharge() + abil.getValue());
							cancel();
						}
					}

				};
			} else {
				return false;
			}
		} else if (abil.getEffect() == StaminaEffect.DECREASE_MAX) {
			final int diff = getMaxStamina() - abil.getValue();

			if (diff >= 0) {
				setMaxStamina(diff);
				run = new BukkitRunnable() {

					@Override
					public void run() {
						if (ability.isRemoved()) {
							setMaxStamina(getMaxStamina() + abil.getValue());
							cancel();
						}
					}

				};
			} else {
				return false;
			}
		} else if (abil.getEffect() == StaminaEffect.CONTINUOUS_USE) {
			run = new BukkitRunnable() {

				@Override
				public void run() {
					if (ability.isRemoved()) {
						cancel();
					}
					int diff = getStamina() - abil.getValue();

					if (diff >= 0) {
						setStamina(diff);
					} else {
						ability.remove();
						cancel();
					}
				}

			};
			timer = 20;
		} else if (abil.getEffect() == StaminaEffect.INCREASE_MAX) {
			setMaxStamina(getMaxStamina() + abil.getValue());

			run = new BukkitRunnable() {

				@Override
				public void run() {
					if (ability.isRemoved()) {
						setMaxStamina(getMaxStamina() - abil.getValue());
						cancel();
					}
				}

			};
		} else if (abil.getEffect() == StaminaEffect.INCREASE_RECHARGE) {
			setRecharge(getRecharge() + abil.getValue());
			run = new BukkitRunnable() {

				@Override
				public void run() {
					if (ability.isRemoved()) {
						setRecharge(getRecharge() - abil.getValue());
						cancel();
					}
				}

			};
		}

		if (run != null) {
			run.runTaskTimer(BendingStamina.get(), 0, timer);
		}

		return true;
	}

	public void unload() {
		PLAYERS.remove(bPlayer);
		recharger.cancel();
		bar.getBar().removePlayer(bPlayer.getPlayer());
	}

	public static void unloadAll() {
		for (Stamina stamina : PLAYERS.values()) {
			stamina.unload();
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
