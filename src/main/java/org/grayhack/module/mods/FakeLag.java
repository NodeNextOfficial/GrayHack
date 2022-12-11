/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import java.util.ArrayList;
import java.util.List;

import org.grayhack.event.events.EventPacket;
import org.grayhack.event.events.EventTick;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingMode;
import org.grayhack.setting.module.SettingSlider;
import org.grayhack.setting.module.SettingToggle;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class FakeLag extends Module {

	public List<PlayerMoveC2SPacket> queue = new ArrayList<>();
	public long startTime = 0;

	public FakeLag() {
		super("FakeLag", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Stores up movement packets and makes the server think you're lagging.",
				new SettingMode("Mode", "Always", "Pulse").withDesc("Lag mode."),
				new SettingToggle("Limit", false).withDesc("Disable FakeLag after x seconds.").withChildren(
						new SettingSlider("Limit", 0, 15, 5, 1).withDesc("How many seconds before disabling.")),
				new SettingSlider("Pulse", 0, 5, 1, 1).withDesc("Pulse interval."));
	}

	@Override
	public void onEnable(boolean inWorld) {
		super.onEnable(inWorld);

		startTime = System.currentTimeMillis();
		queue.clear();
	}

	@Override
	public void onDisable(boolean inWorld) {
		if (inWorld)
			sendPackets();

		super.onDisable(inWorld);
	}

	@GraySubscribe
	public void sendPacket(EventPacket.Send event) {
		if (event.getPacket() instanceof PlayerMoveC2SPacket) {
			queue.add((PlayerMoveC2SPacket) event.getPacket());
			event.setCancelled(true);
		}
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		if (getSetting(0).asMode().getMode() == 0) {
			if (getSetting(1).asToggle().getState() &&
					System.currentTimeMillis() - startTime > getSetting(1).asToggle().getChild(0).asSlider().getValue() * 1000)
				setEnabled(false);
		} else if (getSetting(0).asMode().getMode() == 1) {
			if (System.currentTimeMillis() - startTime > getSetting(2).asSlider().getValue() * 1000) {
				setEnabled(false);
				setEnabled(true);
			}
		}
	}

	public void sendPackets() {
		for (PlayerMoveC2SPacket p : new ArrayList<>(queue)) {
			if (!(p instanceof PlayerMoveC2SPacket.LookAndOnGround)) {
				mc.player.networkHandler.sendPacket(p);
			}
		}

		queue.clear();
	}
}
