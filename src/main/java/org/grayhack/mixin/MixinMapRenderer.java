/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.mixin;

import org.grayhack.module.ModuleManager;
import org.grayhack.module.mods.NoRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.MapRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.map.MapState;

@Mixin(MapRenderer.class)
public class MixinMapRenderer {

	@Inject(method = "draw", at = @At("HEAD"), cancellable = true)
	private void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int id, MapState state, boolean hidePlayerIcons, int light, CallbackInfo ci) {
		if (ModuleManager.getModule(NoRender.class).isWorldToggled(3)) {
			ci.cancel();
		}
	}
}
