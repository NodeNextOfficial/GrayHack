/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import org.apache.commons.lang3.StringUtils;
import org.grayhack.GrayHack;
import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.command.exception.CmdSyntaxException;
import org.grayhack.util.GrayLogger;
import org.grayhack.util.io.GrayFileHelper;

import java.util.Locale;

public class CmdFriends extends Command {

	public CmdFriends() {
		super("friends", "Manage friends.", "friends add <user> | friends remove <user> | friends list | friends clear", CommandCategory.MISC,
				"friend");
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (args.length == 0 || args.length > 2) {
			throw new CmdSyntaxException();
		}

		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 2) {
				throw new CmdSyntaxException("No username selected");
			}

			GrayHack.friendMang.add(args[1]);
			GrayLogger.info("Added \"" + args[1] + "\" to the friend list");
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (args.length < 2) {
				throw new CmdSyntaxException("No username selected");
			}

			GrayHack.friendMang.remove(args[1].toLowerCase(Locale.ENGLISH));
			GrayLogger.info("Removed \"" + args[1] + "\" from the friend list");
		} else if (args[0].equalsIgnoreCase("list")) {
			if (GrayHack.friendMang.getFriends().isEmpty()) {
				GrayLogger.info("You don't have any friends :(");
			} else {
				int len = GrayHack.friendMang.getFriends().stream()
						.min((f1, f2) -> f2.length() - f1.length())
						.get().length() + 3;

				MutableText text = Text.literal("Friends:");

				for (String f : GrayHack.friendMang.getFriends()) {
					String spaces = StringUtils.repeat(' ', len - f.length());

					text
					.append(Text.literal("\n> " + f + spaces)
							.styled(style -> style
									.withColor(GrayLogger.INFO_COLOR)))
					.append(Text.literal("\u00a7c[Del]")
							.styled(style -> style
									.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Remove " + f + " from your friendlist")))
									.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, getPrefix() + "friends remove " + f))))
					.append("   ")
					.append(Text.literal("\u00a73[NameMC]")
							.styled(style -> style
									.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Open NameMC page of " + f)))
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://namemc.com/profile/" + f))));
				}

				GrayLogger.info(text);
			}
		} else if (args[0].equalsIgnoreCase("clear")) {
			GrayHack.friendMang.getFriends().clear();

			GrayLogger.info("Cleared Friend list");
		} else {
			throw new CmdSyntaxException();
		}

		GrayFileHelper.SCHEDULE_SAVE_FRIENDS.set(true);
	}

}
