/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import java.util.Set;

import org.grayhack.event.events.EventTick;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingMode;
import org.grayhack.setting.module.SettingSlider;
import org.grayhack.setting.module.SettingToggle;

import com.google.common.collect.Sets;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class FastUse extends Module {

	private static final Set<Item> THROWABLE = Sets.newHashSet(
			Items.SNOWBALL, Items.EGG, Items.EXPERIENCE_BOTTLE,
			Items.ENDER_EYE, Items.ENDER_PEARL, Items.SPLASH_POTION, Items.LINGERING_POTION);

	public FastUse() {
		super("FastUse", KEY_UNBOUND, ModuleCategory.PLAYER, "Allows you to use items faster.",
				new SettingMode("Mode", "Single", "Multi").withDesc("Whether to throw once per tick or multiple times."),
				new SettingSlider("Multi", 1, 100, 20, 0).withDesc("How many items to use per tick if on multi mode."),
				new SettingToggle("Throwables Only", true).withDesc("Only uses throwables.").withChildren(
						new SettingToggle("XP Only", false).withDesc("Only uses XP bottles.")));
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		if (getSetting(2).asToggle().getState()) {
			if (!(THROWABLE.contains(mc.player.getMainHandStack().getItem())
					&& (!getSetting(2).asToggle().getChild(0).asToggle().getState() 
							|| mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE))) {
				return;
			}
		}

		mc.itemUseCooldown = 0;
		if (getSetting(0).asMode().getMode() == 1 && mc.options.useKey.isPressed()) {
			for (int i = 0; i < getSetting(1).asSlider().getValueInt(); i++) {
				mc.doItemUse();
			}
		}
	}
}