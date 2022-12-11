/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import org.grayhack.event.events.EventClientMove;
import org.grayhack.event.events.EventParticle;
import org.grayhack.event.events.EventSoundPlay;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingToggle;
import org.grayhack.util.world.WorldUtils;

import net.minecraft.block.Blocks;
import net.minecraft.client.particle.PortalParticle;

public class BetterPortal extends Module {

	public BetterPortal() {
		super("BetterPortal", KEY_UNBOUND, ModuleCategory.MISC, "Removes some of the effects of nether portals.",
				new SettingToggle("Gui", true).withDesc("Allows you to open guis when in a nether portal."),
				new SettingToggle("Overlay", true).withDesc("Removes the portal overlay."),
				new SettingToggle("Particles", false).withDesc("Removes the portal particles that fly out of the portal."),
				new SettingToggle("Sound", false).withDesc("Removes the portal sound when going through a nether portal.").withChildren(
						new SettingToggle("Ambience", true).withDesc("Disables the portal ambience sound that plays when you get close to a portal.")));
	}

	@GraySubscribe
	public void onClientMove(EventClientMove event) {
		if (getSetting(1).asToggle().getState()) {
			if (WorldUtils.doesBoxTouchBlock(mc.player.getBoundingBox(), Blocks.NETHER_PORTAL)) {
				mc.player.lastNauseaStrength = -1f;
				mc.player.nextNauseaStrength = -1f;
			}
		}
	}

	@GraySubscribe
	public void onParticle(EventParticle.Normal event) {
		if (getSetting(2).asToggle().getState() && event.getParticle() instanceof PortalParticle) {
			event.setCancelled(true);
		}
	}

	@GraySubscribe
	public void onSoundPlay(EventSoundPlay.Normal event) {
		if (getSetting(3).asToggle().getState()) {
			String path = event.getInstance().getId().getPath();
			if (path.equals("block.portal.trigger") || (getSetting(3).asToggle().getChild(0).asToggle().getState() && path.equals("block.portal.ambient"))) {
				event.setCancelled(true);
			}
		}
	}
}
