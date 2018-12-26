package me.simplicitee.bstamina.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import com.projectkorra.projectkorra.Element;

import me.simplicitee.bstamina.BendingStamina;

public class StaminaBar {
	
	private static final Map<Element, BarColor> COLORS = new HashMap<>();
	
	static {
		COLORS.put(Element.AIR, BarColor.WHITE);
		COLORS.put(Element.CHI, BarColor.YELLOW);
		COLORS.put(Element.EARTH, BarColor.GREEN);
		COLORS.put(Element.FIRE, BarColor.RED);
		COLORS.put(Element.WATER, BarColor.BLUE);
	}

	private Stamina stamina;
	private BossBar bar;
	
	public StaminaBar(Stamina stamina) {
		this.stamina = stamina;
		this.bar = BendingStamina.get().getServer().createBossBar("Bending Stamina - [Loading]", getColor(), BarStyle.SEGMENTED_20);
		
		bar.addPlayer(stamina.getBendingPlayer().getPlayer());
		bar.setProgress(1.0);
	}
	
	public void update() {
		bar.setVisible(true);
		bar.setTitle("Bending Stamina - [" + stamina.getStamina() + " / " + stamina.getMaxStamina() + "]");
		bar.setColor(getColor());
		double progress = ((double) stamina.getStamina()) / stamina.getMaxStamina();
		if (progress > 1.0) {
			progress = 1.0;
		}
		bar.setProgress(progress);
	}
	
	public Stamina getStamina() {
		return stamina;
	}
	
	public BossBar getBar() {
		return bar;
	}
	
	public BarColor getColor() {
		BarColor color = BarColor.PINK;
		if (stamina.getBendingPlayer() != null && stamina.getBendingPlayer().getElements() != null) {
			if (stamina.getBendingPlayer().getElements().size() == 1) {
				color = getColor(stamina.getBendingPlayer().getElements().get(0));
			} else if (!stamina.getBendingPlayer().getElements().isEmpty()) {
				color = BarColor.PURPLE;
			}
		}
		
		return color;
	}
	
	public static BarColor getColor(Element element) {
		if (COLORS.containsKey(element)) {
			return COLORS.get(element);
		} else {
			return BarColor.PINK;
		}
	}
	
	public static void addColor(Element element, BarColor color) {
		COLORS.put(element, color);
	}
}
