/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import org.grayhack.event.events.EventPacket;
import org.grayhack.event.events.EventSwingHand;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingToggle;

import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;

public class NoSwing extends Module {

	public NoSwing() {
		super("NoSwing", KEY_UNBOUND, ModuleCategory.MISC, "Makes you not swing your hand.",
				new SettingToggle("Client", true).withDesc("Makes you not swing your hand clientside."),
				new SettingToggle("Server", true).withDesc("Makes you not send hand swing packets."));
	}

	@GraySubscribe
	public void onSwingHand(EventSwingHand event) {
		if (getSetting(0).asToggle().getState()) {
			event.setCancelled(true);
		}
	}

	@GraySubscribe
	public void onSendPacket(EventPacket.Send event) {
		if (event.getPacket() instanceof HandSwingC2SPacket && getSetting(1).asToggle().getState()) {
			event.setCancelled(true);
		}
	}
}
