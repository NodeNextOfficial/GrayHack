/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.command.exception.CmdSyntaxException;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleManager;
import org.grayhack.util.GrayLogger;
import org.grayhack.util.GrayQueue;

public class CmdToggle extends Command {

	public CmdToggle() {
		super("toggle", "Toggles a mod with a command.", "toggle <module>", CommandCategory.MODULES);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (args.length != 1) {
			throw new CmdSyntaxException();
		}

		for (Module m : ModuleManager.getModules()) {
			if (args[0].equalsIgnoreCase(m.getName())) {
				GrayQueue.add(m::toggle);
				GrayLogger.info(m.getName() + " Toggled");
				return;
			}
		}

		GrayLogger.error("Module \"" + args[0] + "\" Not Found!");
	}

}
