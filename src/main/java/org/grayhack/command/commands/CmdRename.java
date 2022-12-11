/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import org.apache.commons.lang3.StringUtils;
import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.util.GrayLogger;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;


public class CmdRename extends Command {

	public CmdRename() {
		super("rename", "Renames an item, use \"&\" for color.", "rename <name>", CommandCategory.CREATIVE);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (!mc.interactionManager.getCurrentGameMode().isCreative()) {
			GrayLogger.error("Not In Creative Mode!");
			return;
		}

		ItemStack i = mc.player.getInventory().getMainHandStack();

		i.setCustomName(Text.literal(StringUtils.join(args, ' ').replace("&", "\u00a7").replace("\u00a7\u00a7", "&")));
		GrayLogger.info("Renamed Item");
	}

}
