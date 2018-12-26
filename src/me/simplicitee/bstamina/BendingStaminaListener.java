package me.simplicitee.bstamina;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.AbilityStartEvent;

import me.simplicitee.bstamina.ability.StaminaAbility;
import me.simplicitee.bstamina.ability.StaminaAbility.StaminaEffect;
import me.simplicitee.bstamina.util.Stamina;

public class BendingStaminaListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Stamina.createNew(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(event.getPlayer());
		if (bPlayer == null) {
			return;
		}
		
		Stamina stamina = Stamina.get(bPlayer);
		if (stamina == null) {
			return;
		}
		
		stamina.save();
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(event.getPlayer());
		if (bPlayer == null) {
			return;
		}
		
		Stamina stamina = Stamina.get(bPlayer);
		if (stamina == null) {
			return;
		}
		
		stamina.save();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAbilityUse(AbilityStartEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		final CoreAbility ability = (CoreAbility) event.getAbility();
		
		StaminaAbility abil = StaminaAbility.get(ability.getName());
		if (abil == null) {
			return;
		}
		
		Stamina stamina = Stamina.get(BendingPlayer.getBendingPlayer(ability.getPlayer()));
		if (stamina == null) {
			return;
		}
			
		if (abil.getEffect() == StaminaEffect.USE) {
			int diff = stamina.getStamina() - abil.getValue();
			
			if (diff >= 0) {
				stamina.setStamina(diff);
			} else {
				event.setCancelled(true);
			}
		} else if (abil.getEffect() == StaminaEffect.DECREASE_RECHARGE) {
			final int recharge = stamina.getRecharge();
			final int diff = stamina.getRecharge() - abil.getValue();
			
			if (diff >= 0) {
				stamina.setRecharge(diff);
				new BukkitRunnable() {
	
					@Override
					public void run() {
						if (ability.isRemoved()) {
							if (stamina.getRecharge() < recharge) {
								stamina.setRecharge(recharge);
							}
							cancel();
						}
					}
					
				}.runTaskTimer(BendingStamina.get(), 0, 1);
			} else {
				event.setCancelled(true);
			}
		} else if (abil.getEffect() == StaminaEffect.DECREASE_MAX) {
			final int max = stamina.getMaxStamina();
			stamina.setMaxStamina(max - abil.getValue());
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if (ability.isRemoved()) {
						if (stamina.getMaxStamina() < max) {
							stamina.setRecharge(max);
						}
						cancel();
					}
				}
				
			}.runTaskTimer(BendingStamina.get(), 0, 1);
		} else if (abil.getEffect() == StaminaEffect.CONTINUOUS_USE) {
			final int value = abil.getValue();
			
			new BukkitRunnable() {

				@Override
				public void run() {
					if (ability.isRemoved()) {
						cancel();
					}
					int diff = stamina.getStamina() - value;
					
					if (diff >= 0) {
						stamina.setStamina(diff);
					} else {
						ability.remove();
						cancel();
					}
					
				}
				
			}.runTaskTimer(BendingStamina.get(), 0, 20);
		} else if (abil.getEffect() == StaminaEffect.INCREASE_MAX) {
			final int max = stamina.getMaxStamina();
			stamina.setMaxStamina(max + abil.getValue());
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if (ability.isRemoved()) {
						if (stamina.getMaxStamina() > max) {
							stamina.setRecharge(max);
						}
						cancel();
					}
				}
				
			}.runTaskTimer(BendingStamina.get(), 0, 1);
		} else if (abil.getEffect() == StaminaEffect.INCREASE_RECHARGE) {
			final int recharge = stamina.getRecharge();
			final int diff = stamina.getRecharge() + abil.getValue();
			
			stamina.setRecharge(diff);
			new BukkitRunnable() {

				@Override
				public void run() {
					if (ability.isRemoved()) {
						if (stamina.getRecharge() > recharge) {
							stamina.setRecharge(recharge);
						}
						cancel();
					}
				}
				
			}.runTaskTimer(BendingStamina.get(), 0, 1);
		}
	}
}
