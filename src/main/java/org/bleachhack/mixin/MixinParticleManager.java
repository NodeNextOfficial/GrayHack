/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.mixin;

import org.grayhack.GrayHack;
import org.grayhack.event.events.EventParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;

@Mixin(ParticleManager.class)
public class MixinParticleManager {

	@Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
	private void addParticle(Particle particle, CallbackInfo callback) {
		EventParticle.Normal event = new EventParticle.Normal(particle);
		GrayHack.eventBus.post(event);

		if (event.isCancelled()) {
			callback.cancel();
		}
	}

	@Inject(method = "addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;)V", at = @At("HEAD"), cancellable = true)
	private void addEmitter(Entity entity, ParticleEffect particleEffect, CallbackInfo callback) {
		EventParticle.Emitter event = new EventParticle.Emitter(particleEffect);
		GrayHack.eventBus.post(event);

		if (event.isCancelled()) {
			callback.cancel();
		}
	}

	@Inject(method = "addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V", at = @At("HEAD"), cancellable = true)
	private void addEmitter_(Entity entity, ParticleEffect particleEffect, int maxAge, CallbackInfo callback) {
		EventParticle.Emitter event = new EventParticle.Emitter(particleEffect);
		GrayHack.eventBus.post(event);

		if (event.isCancelled()) {
			callback.cancel();
		}
	}
}