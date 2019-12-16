package me.simplicitee.bstamina.ability;

import java.util.HashMap;
import java.util.Map;

public class StaminaAbility {
	
	private static final Map<String, StaminaAbility> ABILS = new HashMap<>();
	public static final StaminaEffect[] NEGATIVE = StaminaEffect.NEGATIVE;
	public static final StaminaEffect[] POSITIVE = StaminaEffect.POSITIVE;

	public static enum StaminaEffect {
		USE, CONTINUOUS_USE, DECREASE_MAX, DECREASE_RECHARGE, NONE, INCREASE_MAX, INCREASE_RECHARGE;
		
		private static final StaminaEffect[] NEGATIVE = new StaminaEffect[] {USE, CONTINUOUS_USE, DECREASE_MAX, DECREASE_RECHARGE};
		private static final StaminaEffect[] POSITIVE = new StaminaEffect[] {INCREASE_MAX, INCREASE_RECHARGE};
	}
	
	private String name;
	private StaminaEffect effect;
	private int value;
	
	public StaminaAbility(String name, StaminaEffect effect, int value) {
		this.name = name;
		this.effect = effect;
		this.value = value;
		ABILS.put(name, this);
	}
	
	public String getName() {
		return name;
	}
	
	public StaminaEffect getEffect() {
		return effect;
	}
	
	public int getValue() {
		return value;
	}
	
	public static StaminaAbility get(String name) {
		if (ABILS.containsKey(name)) {
			return ABILS.get(name);
		} else {
			return null;
		}
	}
}
