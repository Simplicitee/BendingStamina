package me.simplicitee.bstamina;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.AbilityStartEvent;

import me.simplicitee.bstamina.ability.StaminaAbility;
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
		
		stamina.unload();
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
		
		stamina.unload();
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
			
		boolean worked = stamina.activateEffect(abil, ability);
		
		if (!worked) {
			event.setCancelled(true);
		}
	}
}
