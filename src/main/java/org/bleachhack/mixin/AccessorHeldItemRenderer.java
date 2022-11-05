/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;

@Mixin(HeldItemRenderer.class)
public interface AccessorHeldItemRenderer {

	@Accessor
	void setMainHand(ItemStack value);

	@Accessor
	void setOffHand(ItemStack value);

	@Accessor
	float getEquipProgressMainHand();

	@Accessor
	void setEquipProgressMainHand(float value);

	@Accessor
	float getEquipProgressOffHand();

	@Accessor
	void setEquipProgressOffHand(float value);
}
