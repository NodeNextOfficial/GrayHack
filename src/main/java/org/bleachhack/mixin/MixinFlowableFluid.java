/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.mixin;

import java.util.Iterator;

import org.grayhack.module.ModuleManager;
import org.grayhack.module.mods.NoVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.Direction;

@Mixin(FlowableFluid.class)
public class MixinFlowableFluid {

	/** Yeet the first iterator which handles the horizontal fluid movement **/
	@Redirect(method = "getVelocity", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", ordinal = 0))
	private boolean getVelocity_hasNext(Iterator<Direction> var9) {
		if (ModuleManager.getModule(NoVelocity.class).isEnabled()
				&& ModuleManager.getModule(NoVelocity.class).getSetting(3).asToggle().getState()) {
			return false;
		}

		return var9.hasNext();
	}

}
