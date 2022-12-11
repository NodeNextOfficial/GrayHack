package org.grayhack.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;

import org.grayhack.GrayHack;
import org.grayhack.event.events.EventRenderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayers.class)
public class MixinRenderLayers {

	@Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
	private static void getBlockLayer(BlockState state, CallbackInfoReturnable<RenderLayer> callback) {
		EventRenderBlock.Layer event = new EventRenderBlock.Layer(state);
		GrayHack.eventBus.post(event);

		if (event.getLayer() != null)
			callback.setReturnValue(event.getLayer());
	}
}
