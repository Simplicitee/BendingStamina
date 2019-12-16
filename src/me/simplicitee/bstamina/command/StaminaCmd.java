package me.simplicitee.bstamina.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.projectkorra.projectkorra.command.PKCommand;

public class StaminaCmd extends PKCommand {

	public StaminaCmd() {
		super("stamina", "/bending stamina", "Main BendingStamina command", new String[] {"stamina", "stam", "mana", "st"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (args.size() == 0) {
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "----" + ChatColor.YELLOW + " BendingStamina commands " + ChatColor.LIGHT_PURPLE + "----");
			for (StaminaCommand cmd : StaminaCommand.instances.values()) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + cmd.getProperUse() + " - " + ChatColor.YELLOW + cmd.getDescription());
			}
		} else {
			for (StaminaCommand cmd : StaminaCommand.instances.values()) {
				if (Arrays.asList(cmd.getAliases()).contains(args.get(0).toLowerCase())) {
					cmd.execute(sender, args.subList(1, args.size()));
					break;
				}
			}
		}
	}

	@Override
	protected List<String> getTabCompletion(CommandSender sender, List<String> args) {
		if (args.size() == 0) {
			List<String> l = new ArrayList<String>();
			for (StaminaCommand cmd : StaminaCommand.instances.values()) {
				l.add(cmd.getName());
			}
			Collections.sort(l);
			return l;
		}
		else
		for (StaminaCommand cmd : StaminaCommand.instances.values()) {
			if (Arrays.asList(cmd.getAliases()).contains(args.get(0).toLowerCase()) && sender.hasPermission("bending.command.stamina." + cmd.getName())) {
				List<String> newargs = new ArrayList<String>();
				for (int i = 1; i < args.size(); i++) {
					if (!(args.get(i).equals("") || args.get(i).equals(" ")) && args.size() >= 1)
					newargs.add(args.get(i));
				}
				return cmd.getTabCompletion(sender, newargs);
			}
		}
		return new ArrayList<String>();
	}
}
