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
import org.grayhack.command.CommandManager;

public class CmdSay extends Command {

	public CmdSay() {
		super("say", "Says a message in chat.", "say <message>", CommandCategory.MISC);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		CommandManager.allowNextMsg = true;
		mc.player.sendChatMessage(String.join(" ", args), null);
	}

}
