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
import org.grayhack.util.GrayLogger;

import net.minecraft.item.ItemStack;

public class CmdCI extends Command {

	public CmdCI() {
		super("ci", "Clears your inventory.", "ci", CommandCategory.CREATIVE,
				"clear", "clearinv");
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (!mc.interactionManager.getCurrentGameMode().isCreative()) {
			GrayLogger.error("Bruh you're not in creative.");
			return;
		}

		for (int i = 0; i < mc.player.playerScreenHandler.getStacks().size(); i++) {
			mc.interactionManager.clickCreativeStack(ItemStack.EMPTY, i);
		}

		GrayLogger.info("Cleared all items");
	}

}
