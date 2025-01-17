/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import org.grayhack.event.events.EventBlockBreakCooldown;
import org.grayhack.event.events.EventTick;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingMode;
import org.grayhack.setting.module.SettingSlider;
import org.grayhack.setting.module.SettingToggle;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class SpeedMine extends Module {

	public SpeedMine() {
		super("SpeedMine", KEY_UNBOUND, ModuleCategory.EXPLOITS, "Allows you to break blocks faster.",
				new SettingMode("Mode", "Haste", "OG").withDesc("SpeedMine Mode."),
				new SettingSlider("HasteLvl", 1, 3, 1, 0).withDesc("Haste Level."),
				new SettingSlider("Cooldown", 0, 4, 1, 0).withDesc("Cooldown between mining blocks (in ticks)."),
				new SettingSlider("Multiplier", 1, 3, 1.3, 1).withDesc("OG Mode multiplier."),
				new SettingToggle("AntiFatigue", true).withDesc("Removes the fatigue effect."),
				new SettingToggle("AntiOffGround", true).withDesc("Removing mining slowness from being offground."));
	}

	@Override
	public void onDisable(boolean inWorld) {
		if (inWorld)
			mc.player.removeStatusEffect(StatusEffects.HASTE);

		super.onDisable(inWorld);
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		if (this.getSetting(0).asMode().getMode() == 0) {
			mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 1, getSetting(1).asSlider().getValueInt() - 1));
		}
	}

	@GraySubscribe
	public void onBlockBreakCooldown(EventBlockBreakCooldown event) {
		event.setCooldown(getSetting(2).asSlider().getValueInt());
	}
}
