/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import org.grayhack.event.events.EventTick;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingToggle;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Makes the player automatically sprint.",
				new SettingToggle("HungerCheck", false).withDesc("Checks that you actually have enough hunger to sprint."));
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		if (getSetting(0).asToggle().getState() && mc.player.getHungerManager().getFoodLevel() <= 6)
			return;

		mc.player.setSprinting(
				mc.player.input.movementForward > 0 && 
				(mc.player.input.movementSideways != 0 ||mc.player.input.movementForward > 0) &&
				!mc.player.isSneaking());
	}
}
