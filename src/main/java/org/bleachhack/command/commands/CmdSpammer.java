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
import org.grayhack.util.io.GrayFileMang;

import net.minecraft.util.Util;

public class CmdSpammer extends Command {

	public CmdSpammer() {
		super("spammer", "Opens the spammer file.", "spammer", CommandCategory.MODULES,
				"editspammer");
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		GrayFileMang.createFile("spammer.txt");
		Util.getOperatingSystem().open(GrayFileMang.getDir().resolve("spammer.txt").toUri());

		GrayLogger.info("Opened spammer file.");
	}

}
