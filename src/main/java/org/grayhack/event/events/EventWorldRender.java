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

public class EventWorldRender extends Event {

	protected float partialTicks;
	protected MatrixStack matrices;
	
	public static class Pre extends EventWorldRender {

		public Pre(float partialTicks, MatrixStack matrices) {
			this.partialTicks = partialTicks;
			this.matrices = matrices;
		}
		
	}
	
	public static class Post extends EventWorldRender {

		public Post(float partialTicks, MatrixStack matrices) {
			this.partialTicks = partialTicks;
			this.matrices = matrices;
		}
		
	}

	public float getPartialTicks() {
		return partialTicks;
	}
	
	public MatrixStack getMatrices() {
		return matrices;
	}
}
