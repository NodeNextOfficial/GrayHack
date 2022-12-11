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
import org.grayhack.gui.EntityMenuEditScreen;
import org.grayhack.module.ModuleManager;
import org.grayhack.module.mods.EntityMenu;
import org.grayhack.util.GrayQueue;
import org.grayhack.util.collections.MutablePairList;

/**
 * @author <a href="https://github.com/lasnikprogram">Lasnik</a>
 */
public class CmdEntityMenu extends Command {

	public CmdEntityMenu() {
		super("entitymenu", "Opens the gui to manage the things which appear on the entitymenu interaction screen.", "entitymenu", CommandCategory.MODULES,
				"playermenu", "interactionmenu");
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		MutablePairList<String, String> interactions = ModuleManager.getModule(EntityMenu.class).interactions;

		GrayQueue.add(() -> mc.setScreen(new EntityMenuEditScreen(interactions)));
	}
}
