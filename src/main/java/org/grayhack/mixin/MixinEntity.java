/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.grayhack.GrayHack;
import org.grayhack.event.events.EventPlayerPushed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public class MixinEntity {

	@ModifyArgs(method = "pushAwayFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
	private void pushAwayFrom_addVelocity(Args args) {
		if ((Object) this == MinecraftClient.getInstance().player) {
			EventPlayerPushed event = new EventPlayerPushed(args.get(0), args.get(1), args.get(2));
			GrayHack.eventBus.post(event);

			args.set(0, event.getPushX());
			args.set(1, event.getPushY());
			args.set(2, event.getPushZ());
		}
	}
}
