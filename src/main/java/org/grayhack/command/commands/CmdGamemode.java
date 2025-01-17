/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import java.util.Locale;

import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.command.exception.CmdSyntaxException;
import org.grayhack.util.GrayLogger;

import net.minecraft.world.GameMode;

public class CmdGamemode extends Command {

	public CmdGamemode() {
		super("gamemode", "Sets your clientside gamemode.", "gamemode [survival/creative/adventure/spectator]| gamemode <0-3>", CommandCategory.MISC,
				"gm");
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (args.length == 0) {
			throw new CmdSyntaxException();
		}
		
		String lower = args[0].toLowerCase(Locale.ENGLISH);

		if (lower.equals("0") || lower.startsWith("su")) {
			mc.interactionManager.setGameMode(GameMode.SURVIVAL);
			GrayLogger.info("\u00a7l\u00a7nClientside\u00a7r gamemode has been set to survival.");
		} else if (lower.equals("1") || lower.startsWith("c")) {
			mc.interactionManager.setGameMode(GameMode.CREATIVE);
			GrayLogger.info("\u00a7l\u00a7nClientside\u00a7r gamemode has been set to creative.");
		} else if (lower.equals("2") || lower.startsWith("a")) {
			mc.interactionManager.setGameMode(GameMode.ADVENTURE);
			GrayLogger.info("\u00a7l\u00a7nClientside\u00a7r gamemode has been set to adventure.");
		} else if (lower.equals("3") || lower.startsWith("sp")) {
			mc.interactionManager.setGameMode(GameMode.SPECTATOR);
			GrayLogger.info("\u00a7l\u00a7nClientside\u00a7r gamemode has been set to spectator.");
		} else {
			throw new CmdSyntaxException("Unknown Gamemode!");
		}
	}

}
