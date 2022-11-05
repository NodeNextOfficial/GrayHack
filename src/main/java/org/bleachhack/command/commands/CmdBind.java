/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import net.minecraft.client.util.InputUtil;
import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.command.exception.CmdSyntaxException;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleManager;
import org.grayhack.util.GrayLogger;

import java.util.Locale;

public class CmdBind extends Command {

	public CmdBind() {
		super("bind", "Binds a module.", "bind set <module> <key> | bind del <module> | bind clear", CommandCategory.MODULES);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (args.length == 0) {
			throw new CmdSyntaxException();
		}

		if (args[0].equalsIgnoreCase("clear")) {
			int c = 0;
			for (Module m : ModuleManager.getModules()) {
				if (m.getKey() != Module.KEY_UNBOUND) {
					m.setKey(Module.KEY_UNBOUND);
					c++;
				}
			}

			GrayLogger.info("Cleared " + c + " Binds");
		} else if (args.length >= 2 && (args.length >= 3 || !args[1].equalsIgnoreCase("set"))) {
			for (Module m : ModuleManager.getModules()) {
				if (m.getName().equalsIgnoreCase(args[1])) {
					if (args[0].equalsIgnoreCase("set")) {
						int key = Module.KEY_UNBOUND;

						// Special cases for rshift/rcontrol and that shit
						try {
							key = InputUtil.fromTranslationKey("key.keyboard." + args[2].toLowerCase(Locale.ENGLISH)).getCode();
						} catch (IllegalArgumentException e) {
							if (args[2].toLowerCase(Locale.ENGLISH).startsWith("right")) {
								try {
									key = InputUtil.fromTranslationKey("key.keyboard." + args[2].toLowerCase(Locale.ENGLISH).replaceFirst("right", "right.")).getCode();
								} catch (IllegalArgumentException e1) {
									throw new CmdSyntaxException("Unknown key: " + args[2] + " / " + args[2].toLowerCase(Locale.ENGLISH).replaceFirst("right", "right."));
								}
							} else if (args[2].toLowerCase(Locale.ENGLISH).startsWith("r")) {
								try {
									key = InputUtil.fromTranslationKey("key.keyboard." + args[2].toLowerCase(Locale.ENGLISH).replaceFirst("r", "right.")).getCode();
								} catch (IllegalArgumentException e1) {
									throw new CmdSyntaxException("Unknown key: " + args[2] + " / " + args[2].toLowerCase(Locale.ENGLISH).replaceFirst("r", "right."));
								}
							} else {
								throw new CmdSyntaxException("Unknown key: " + args[2]);
							}
						}

						m.setKey(key);
						GrayLogger.info("Bound " + m.getName() + " To " + args[2] + " (KEY" + key + ")");
					} else if (args[0].equalsIgnoreCase("del")) {
						m.setKey(Module.KEY_UNBOUND);
						GrayLogger.info("Removed Bind For " + m.getName());
					}

					return;
				}
			}

			throw new CmdSyntaxException("Could Not Find Module \"" + args[1] + "\"");
		} else {
			throw new CmdSyntaxException();
		}
	}

}
