/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import org.grayhack.event.events.EventOpenScreen;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.gui.clickgui.ModuleClickGuiScreen;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingSlider;
import org.grayhack.setting.module.SettingToggle;
import org.lwjgl.glfw.GLFW;

public class ClickGui extends Module {

	public ClickGui() {
		super("ClickGui", GLFW.GLFW_KEY_RIGHT_SHIFT, ModuleCategory.RENDER, "Draws the clickgui.",
				new SettingSlider("Length", 70, 85, 75, 0).withDesc("The length of each window."),
				new SettingToggle("Search bar", true).withDesc("Shows a search bar."),
				new SettingToggle("Help", true).withDesc("Shows the help text."));
	}

	@Override
	public void onEnable(boolean inWorld) {
		super.onEnable(inWorld);

		mc.setScreen(ModuleClickGuiScreen.INSTANCE);
	}

	@Override
	public void onDisable(boolean inWorld) {
		if (mc.currentScreen instanceof ModuleClickGuiScreen)
			mc.setScreen(null);

		super.onDisable(inWorld);
	}

	@GraySubscribe
	public void onOpenScreen(EventOpenScreen event) {
		if (event.getScreen() == null) {
			setEnabled(false);
		}
	}
}
