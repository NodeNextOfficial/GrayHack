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
import org.grayhack.setting.module.SettingMode;

import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Nofall extends Module {

	public Nofall() {
		super("Nofall", KEY_UNBOUND, ModuleCategory.PLAYER, "Prevents you from taking fall damage.",
				new SettingMode("Mode", "Simple", "Packet").withDesc("What Nofall mode to use."));
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		if (mc.player.fallDistance > 2.5f && getSetting(0).asMode().getMode() == 0) {
			if (mc.player.isFallFlying())
				return;
			mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
		}

		if (mc.player.fallDistance > 2.5f && getSetting(0).asMode().getMode() == 1 &&
				mc.world.getBlockState(mc.player.getBlockPos().add(
						0, -1.5 + (mc.player.getVelocity().y * 0.1), 0)).getBlock() != Blocks.AIR) {
			mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(false));
			mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
					mc.player.getX(), mc.player.getY() - 420.69, mc.player.getZ(), true));
			mc.player.fallDistance = 0;
		}
	}
}
