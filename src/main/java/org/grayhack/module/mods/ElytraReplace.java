/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import org.grayhack.event.events.EventTick;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;

import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class ElytraReplace extends Module {

	private boolean jump = false;

	public ElytraReplace() {
		super("ElytraReplace", KEY_UNBOUND, ModuleCategory.PLAYER, "Automatically replaces your elytra when its broken and continues flying.");
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		if (mc.player.playerScreenHandler != mc.player.currentScreenHandler)
			return;

		int chestSlot = 38;
		ItemStack chest = mc.player.getInventory().getStack(chestSlot);
		if (chest.getItem() instanceof ElytraItem && chest.getDamage() == (Items.ELYTRA.getMaxDamage() - 1)) {
			// search inventory for elytra

			Integer elytraSlot = null;
			for (int slot = 0; slot < 36; slot++) {
				ItemStack stack = mc.player.getInventory().getStack(slot);
				if (stack.getItem() instanceof ElytraItem && stack.getDamage() != (Items.ELYTRA.getMaxDamage() - 1)) {
					elytraSlot = slot;
					break;
				}
			}

			if (elytraSlot == null) {
				return;
			}

			mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 6, 0, SlotActionType.PICKUP, mc.player);
			mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, elytraSlot < 9 ? (elytraSlot + 36) : (elytraSlot), 0, SlotActionType.PICKUP,
					mc.player);
			mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 6, 0, SlotActionType.PICKUP, mc.player);

			mc.options.jumpKey.setPressed(true); // Make them fly again
			jump = true;
		} else if (jump) {
			mc.options.jumpKey.setPressed(false); // Make them fly again
			jump = false;
		}
	}
}
