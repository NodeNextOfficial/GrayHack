/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.event.events;

import org.grayhack.event.Event;

import net.minecraft.client.util.math.MatrixStack;

public class EventRenderScreenBackground extends Event {
	
	private MatrixStack matrices;
	private int vOffset;

	public EventRenderScreenBackground(MatrixStack matrices, int vOffset) {
		this.matrices = matrices;
		this.vOffset = vOffset;
	}

	public MatrixStack getMatrices() {
		return matrices;
	}

	public int getvOffset() {
		return vOffset;
	}
}