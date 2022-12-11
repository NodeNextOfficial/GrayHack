/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import org.apache.commons.lang3.math.NumberUtils;
import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.command.exception.CmdSyntaxException;
import org.grayhack.gui.clickgui.ModuleClickGuiScreen;
import org.grayhack.gui.clickgui.window.ClickGuiWindow;
import org.grayhack.gui.window.Window;
import org.grayhack.module.ModuleManager;
import org.grayhack.module.mods.ClickGui;
import org.grayhack.util.GrayLogger;
import org.grayhack.util.io.GrayFileHelper;

public class CmdClickGui extends Command {

	public CmdClickGui() {
		super("clickgui", "Modify the clickgui windows.", "clickgui reset [open/closed] | clickgui length <length>", CommandCategory.MODULES);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (args.length != 1 && args.length != 2) {
			throw new CmdSyntaxException();
		}

		if (args[0].equalsIgnoreCase("reset")) {
			if (args.length == 1 || args[1].equalsIgnoreCase("closed")) {
				int y = 50;

				for (Window m : ModuleClickGuiScreen.INSTANCE.getWindows()) {
					if (m instanceof ClickGuiWindow) {
						((ClickGuiWindow) m).hiding = true;
						m.x1 = 30;
						m.y1 = y;
						y += 16;
					}
				}
			} else if (args[1].equalsIgnoreCase("open")) {
				int x = 10;

				for (Window m : ModuleClickGuiScreen.INSTANCE.getWindows()) {
					if (m instanceof ClickGuiWindow) {
						((ClickGuiWindow) m).hiding = false;
						m.x1 = x;
						m.y1 = 35;
						x += ModuleManager.getModule(ClickGui.class).getSetting(0).asSlider().getValueInt() + 5;
					}
				}
			} else {
				throw new CmdSyntaxException("Invalid reset mode!");
			}

			GrayFileHelper.SCHEDULE_SAVE_CLICKGUI.set(true);
			GrayLogger.info("Reset the clickgui!");
		} else if (args[0].equalsIgnoreCase("length")) {
			if (!NumberUtils.isCreatable(args[1])) {
				throw new CmdSyntaxException("Invalid clickgui length: " + args[1]);
			}

			ModuleManager.getModule(ClickGui.class).getSetting(0).asSlider().setValue(NumberUtils.createNumber(args[1]).doubleValue());
			GrayFileHelper.SCHEDULE_SAVE_MODULES.set(true);

			GrayLogger.info("Set the clickgui length to: " + args[1]);
		} else {
			throw new CmdSyntaxException();
		}
	}

}
