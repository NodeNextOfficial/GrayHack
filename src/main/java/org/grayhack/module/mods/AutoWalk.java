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

public class AutoWalk extends Module {

	public AutoWalk() {
		super("AutoWalk", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Automatically holds your walk key down.");
	}

	@Override
	public void onDisable(boolean inWorld) {
		mc.options.forwardKey.setPressed(false);

		super.onDisable(inWorld);
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		mc.options.forwardKey.setPressed(true);
	}
}
