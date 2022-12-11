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
import org.grayhack.event.events.EventPacket;
import org.grayhack.event.events.EventTick;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.setting.module.SettingMode;
import org.grayhack.setting.module.SettingToggle;
import org.grayhack.util.world.WorldUtils;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class AntiVoid extends Module {

	public AntiVoid() {
		super("AntiVoid", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Prevents you from falling in the void.",
				new SettingMode("Mode", "Jump", "Floor", "Vanilla").withDesc("What mode to use when you're in the void."),
				new SettingToggle("AntiTP", true).withDesc("Prevents you from accidentally tping in to the void (i.e., using PacketFly)."));
	}

	@GraySubscribe
	public void onTick(EventTick event) {
		if (mc.player.getY() < mc.world.getBottomY()) {
			switch (getSetting(0).asMode().getMode()) {
				case 0:
					mc.player.jump();
					break;
				case 1:
					mc.player.setOnGround(true);
					break;
				case 2:
					for (int i = mc.world.getBottomY() + 3; i < mc.world.getTopY() + 1; i++) {
						if (!WorldUtils.doesBoxCollide(mc.player.getBoundingBox().offset(0, -mc.player.getY() + i, 0))) {
							mc.player.updatePosition(mc.player.getX(), i, mc.player.getZ());
							break;
						}
					}

					break;
			}
		}
	}

	@GraySubscribe
	public void onSendPacket(EventPacket.Send event) {
		if (event.getPacket() instanceof PlayerMoveC2SPacket) {
			PlayerMoveC2SPacket packet = (PlayerMoveC2SPacket) event.getPacket();

			if (getSetting(1).asToggle().getState()
					&& mc.player.getY() >= mc.world.getBottomY() && packet.getY(mc.player.getY()) < mc.world.getBottomY()) {
				event.setCancelled(true);
				return;
			}
			
			if (getSetting(0).asMode().getMode() == 1 && mc.player.getY() < mc.world.getBottomY() && packet.getY(mc.player.getY()) < mc.player.getY()) {
				packet.y = mc.player.getY();
			}
		}
	}

	@GraySubscribe
	public void onClientMove(EventClientMove event) {
		if (getSetting(1).asToggle().getState() && mc.player.getY() >= mc.world.getBottomY() && mc.player.getY() - event.getVec().y < mc.world.getBottomY()) {
			event.setCancelled(true);
			return;
		}
		
		if (getSetting(0).asMode().getMode() == 1 && mc.player.getY() < mc.world.getBottomY() && event.getVec().y < 0) {
			event.setVec(new Vec3d(event.getVec().x, 0, event.getVec().z));
			mc.player.addVelocity(0, -mc.player.getVelocity().y, 0);
		}
	}

}
