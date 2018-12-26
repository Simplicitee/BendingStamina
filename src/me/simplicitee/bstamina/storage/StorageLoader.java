package me.simplicitee.bstamina.storage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import com.projectkorra.projectkorra.ability.CoreAbility;

import me.simplicitee.bstamina.ability.StaminaAbility;
import me.simplicitee.bstamina.ability.StaminaAbility.StaminaEffect;

public class StorageLoader {
	
	private Config config;
	private Map<String, StaminaEffect> effects;
	private Map<String, Integer> values;
	
	public StorageLoader() {
		config = new Config(new File("abilities.yml"));
		effects = new HashMap<>();
		values = new HashMap<>();
		
		loadDefaults();
		loadAbilities();
	}
	
	public void loadAbility(CoreAbility ability) {
		StaminaEffect effect = StaminaEffect.NONE;
		int value = 0;
		
		if (effects.containsKey(ability.getName())) {
			effect = effects.get(ability.getName());
		}
		
		if (values.containsKey(ability.getName())) {
			value = values.get(ability.getName());
		}
		
		FileConfiguration c = config.get();
		
		c.addDefault("Abilities." + ability.getElement().getName() + "." + ability.getName() + ".Effect", effect.toString());
		c.addDefault("Abilities." + ability.getElement().getName() + "." + ability.getName() + ".Value", value);
		
		config.save();
		
		effect = StaminaEffect.valueOf(c.getString("Abilities." + ability.getElement().getName() + "." + ability.getName() + ".Effect").toUpperCase());
		value = Math.abs(c.getInt("Abilities." + ability.getElement().getName() + "." + ability.getName() + ".Value"));
		
		new StaminaAbility(ability.getName(), effect, value);
	}
	
	public void loadAbilities() {
		for (CoreAbility ability : CoreAbility.getAbilities()) {
			loadAbility(ability);
		}
	}
	
	public Config getConfig() {
		return config;
	}
	
	public void loadDefaults() {
		effects.put("AirBlast", StaminaEffect.USE);
		values.put("AirBlast", 10);
		
		effects.put("AirBurst", StaminaEffect.USE);
		values.put("AirBurst", 20);
		
		effects.put("AirScooter", StaminaEffect.DECREASE_MAX);
		values.put("AirScooter", 150);
		
		effects.put("AirShield", StaminaEffect.CONTINUOUS_USE);
		values.put("AirScooter", 10);
		
		effects.put("AirSpout", StaminaEffect.DECREASE_RECHARGE);
		values.put("AirSpout", 15);
		
		effects.put("AirSuction", StaminaEffect.USE);
		values.put("AirSuction", 10);
		
		effects.put("AirSwipe", StaminaEffect.USE);
		values.put("AirSwipe", 25);
		
		effects.put("Suffocate", StaminaEffect.CONTINUOUS_USE);
		values.put("Suffocate", 5);
		
		effects.put("Tornado", StaminaEffect.CONTINUOUS_USE);
		values.put("Tornado", 15);
		
		effects.put("AirStream", StaminaEffect.CONTINUOUS_USE);
		values.put("AirStream", 15);
		
		effects.put("AirSweep", StaminaEffect.USE);
		values.put("AirSweep", 50);
		
		effects.put("Twister", StaminaEffect.DECREASE_MAX);
		values.put("Twister", 200);
		
		effects.put("Flight", StaminaEffect.CONTINUOUS_USE);
		values.put("Flight", 10);
		
		effects.put("AcrobatStance", StaminaEffect.DECREASE_RECHARGE);
		values.put("AcrobatStance", 15);
		
		effects.put("HighJump", StaminaEffect.USE);
		values.put("HighJump", 15);
		
		effects.put("Paralyze", StaminaEffect.USE);
		values.put("Paralyze", 20);
		
		effects.put("QuickStrike", StaminaEffect.USE);
		values.put("QuickStrike", 15);
		
		effects.put("RapidPunch", StaminaEffect.USE);
		values.put("RapidPunch", 40);
		
		effects.put("SmokeScreen", StaminaEffect.USE);
		values.put("Smokescreen", 20);
		
		effects.put("SwiftKick", StaminaEffect.USE);
		values.put("SwiftKick", 30);
		
		effects.put("WarriorStance", StaminaEffect.DECREASE_RECHARGE);
		values.put("WarriorStance", 15);
		
		effects.put("Immobilize", StaminaEffect.USE);
		values.put("Immobilize", 70);
		
		effects.put("Catapult", StaminaEffect.USE);
		values.put("Catapult", 50);
		
		effects.put("Collapse", StaminaEffect.USE);
		values.put("Collapse", 15);
		
		effects.put("EarthArmor", StaminaEffect.DECREASE_RECHARGE);
		values.put("EarthArmor", 15);
		
		effects.put("EarthBlast", StaminaEffect.USE);
		values.put("EarthBlast", 20);
		
		effects.put("EarthDome", StaminaEffect.USE);
		values.put("EarthDome", 40);
		
		effects.put("EarthGrab", StaminaEffect.CONTINUOUS_USE);
		values.put("EarthGrab", 10);
		
		effects.put("EarthSmash", StaminaEffect.USE);
		values.put("EarthSmash", 60);
		
		effects.put("EarthTunnel", StaminaEffect.CONTINUOUS_USE);
		values.put("EarthTunnel", 10);
		
		effects.put("RaiseEarth", StaminaEffect.USE);
		values.put("RaiseEarth", 20);
		
		effects.put("Shockwave", StaminaEffect.USE);
		values.put("Shockwave", 75);
		
		effects.put("Tremorsense", StaminaEffect.USE);
		values.put("Tremorsense", 10);
		
		effects.put("EarthPillars", StaminaEffect.USE);
		values.put("EarthPillars", 45);
		
		effects.put("LavaFlow", StaminaEffect.DECREASE_RECHARGE);
		values.put("LavaFlow", 15);
		
		effects.put("Extraction", StaminaEffect.USE);
		values.put("Extraction", 30);
		
		effects.put("MetalClips", StaminaEffect.CONTINUOUS_USE);
		values.put("MetalClips", 15);
		
		effects.put("FerroControl", StaminaEffect.USE);
		values.put("FerroControl", 5);
		
		effects.put("Blaze", StaminaEffect.USE);
		values.put("Blaze", 15);
		
		effects.put("FireBlast", StaminaEffect.USE);
		values.put("FireBlast", 10);
		
		effects.put("FireBurst", StaminaEffect.USE);
		values.put("FireBurst", 30);
		
		effects.put("FireJet", StaminaEffect.CONTINUOUS_USE);
		values.put("FireJet", 20);
		
		effects.put("FireShield", StaminaEffect.USE);
		values.put("FireShield", 30);
		
		effects.put("HeatControl", StaminaEffect.CONTINUOUS_USE);
		values.put("HeatControl", 5);
		
		effects.put("WallOfFire", StaminaEffect.DECREASE_RECHARGE);
		values.put("WallOfFire", 15);
		
		effects.put("FireKick", StaminaEffect.USE);
		values.put("FireKick", 50);
		
		effects.put("FireSpin", StaminaEffect.USE);
		values.put("FireSpin", 50);
		
		effects.put("FireWheel", StaminaEffect.USE);
		values.put("FireWheel", 50);
		
		effects.put("JetBlast", StaminaEffect.DECREASE_RECHARGE);
		values.put("JetBlast", 20);
		
		effects.put("JetBlaze", StaminaEffect.DECREASE_RECHARGE);
		values.put("JetBlaze", 20);
		
		effects.put("Combustion", StaminaEffect.USE);
		values.put("Combustion", 125);
		
		effects.put("Lightning", StaminaEffect.USE);
		values.put("Lightning", 75);
		
		effects.put("OctopusForm", StaminaEffect.CONTINUOUS_USE);
		values.put("OctopusForm", 20);
		
		effects.put("Surge", StaminaEffect.CONTINUOUS_USE);
		values.put("Surge", 30);
		
		effects.put("Torrent", StaminaEffect.CONTINUOUS_USE);
		values.put("Torrent", 20);
		
		effects.put("WaterBubble", StaminaEffect.CONTINUOUS_USE);
		values.put("WaterBubble", 5);
		
		effects.put("WaterManipulation", StaminaEffect.USE);
		values.put("WaterManipulation", 15);
		
		effects.put("WaterSpout", StaminaEffect.DECREASE_RECHARGE);
		values.put("WaterSpout", 15);
		
		effects.put("Bloodbending", StaminaEffect.CONTINUOUS_USE);
		values.put("Bloodbending", 20);
		
		effects.put("IceBullet", StaminaEffect.CONTINUOUS_USE);
		values.put("IceBullet", 10);
		
		effects.put("IceWave", StaminaEffect.DECREASE_RECHARGE);
		values.put("IceWave", 20);
		
		effects.put("HealingWaters", StaminaEffect.CONTINUOUS_USE);
		values.put("HealingWaters", 10);
		
		effects.put("IceBlast", StaminaEffect.USE);
		values.put("IceBlast", 50);
		
		effects.put("IceSpike", StaminaEffect.USE);
		values.put("IceSpike", 20);
		
		effects.put("PhaseChange", StaminaEffect.CONTINUOUS_USE);
		values.put("PhaseChange", 10);
		
		effects.put("WaterArms", StaminaEffect.CONTINUOUS_USE);
		values.put("WaterArms", 10);
		
		effects.put("FastSwim", StaminaEffect.CONTINUOUS_USE);
		values.put("FastSwim", 5);
		
		effects.put("AvatarState", StaminaEffect.INCREASE_RECHARGE);
		values.put("AvatarState", 100);
	}
}
