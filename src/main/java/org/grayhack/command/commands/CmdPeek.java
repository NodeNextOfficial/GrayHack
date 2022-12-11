/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import net.minecraft.block.*;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.util.GrayLogger;
import org.grayhack.util.GrayQueue;
import org.grayhack.util.ItemContentUtils;

import java.util.List;

public class CmdPeek extends Command {

	public CmdPeek() {
		super("peek", "Shows whats inside the container you're holder.", "peek", CommandCategory.MISC);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		ItemStack item = mc.player.getInventory().getMainHandStack();

		if (item.getItem() instanceof BlockItem) {
			Block block = ((BlockItem) item.getItem()).getBlock();
			if (!(block instanceof ShulkerBoxBlock
					|| block instanceof ChestBlock
					|| block instanceof DispenserBlock
					|| block instanceof HopperBlock)) {
				GrayLogger.error("Must be holding a containter to peek.");
				return;
			}
		} else if (item.getItem() != Items.BUNDLE) {
			GrayLogger.error("Must be holding a containter to peek.");
			return;
		}

		List<ItemStack> items = ItemContentUtils.getItemsInContainer(item);

		SimpleInventory inv = new SimpleInventory(items.toArray(new ItemStack[27]));

		GrayQueue.add(() ->
				mc.setScreen(new PeekShulkerScreen(
						new ShulkerBoxScreenHandler(420, mc.player.getInventory(), inv),
						mc.player.getInventory(),
						item.getName())));
	}

	static class PeekShulkerScreen extends ShulkerBoxScreen {

		public PeekShulkerScreen(ShulkerBoxScreenHandler handler, PlayerInventory inventory, Text title) {
			super(handler, inventory, title);
		}

		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			return false;
		}
	}

}
