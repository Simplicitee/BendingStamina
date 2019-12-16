package me.simplicitee.bstamina.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

import me.simplicitee.bstamina.ability.StaminaAbility;

public class ActiveCmd extends StaminaCommand{

	public ActiveCmd() {
		super("active", "/b stamina active [player]", "Shows active abilities and their stamina usage [for a player (self if omitted)]", new String[] {"active", "a", "using", "current", "show", "status"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!correctLength(sender, args.size(), 0, 1)) {
			return;
		} else if (!isPlayer(sender)) {
			return;
		}
		
		Player player = null;
		
		if (args.size() == 0) {
			player = (Player) sender;
		} else if (args.size() == 1) {
			player = Bukkit.getPlayer(args.get(0));
		}
		
		if (player != null) {
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			
			if (bPlayer != null) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "----" + ChatColor.YELLOW + " Active Abilities (" + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.YELLOW + ") " + ChatColor.LIGHT_PURPLE + "----");
				
				for (CoreAbility abil : CoreAbility.getAbilitiesByInstances()) {
					if (abil.getPlayer().equals(player)) {
						StaminaAbility active = StaminaAbility.get(abil.getName());
						sender.sendMessage(ChatColor.LIGHT_PURPLE + active.getName() + ": " + ChatColor.YELLOW + active.getEffect().toString() + " (" + ChatColor.LIGHT_PURPLE + active.getValue() + ChatColor.YELLOW + ")");
					}
				}
			}
		}
	}

}
