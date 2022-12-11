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
import org.grayhack.setting.option.Option;
import org.grayhack.util.GrayLogger;
import org.grayhack.util.io.GrayFileHelper;

public class CmdPrefix extends Command {

	public CmdPrefix() {
		super("prefix", "Sets the GrayHack command prefix.", "prefix <prefix>", CommandCategory.MISC);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (args[0].isEmpty()) {
			throw new CmdSyntaxException("Prefix Cannot Be Empty");
		}

		Option.CHAT_COMMAND_PREFIX.setValue(args[0]);
		GrayFileHelper.SCHEDULE_SAVE_OPTIONS.set(true);
		GrayLogger.info("Set Prefix To: \"" + getPrefix() + "\"");
	}

}
