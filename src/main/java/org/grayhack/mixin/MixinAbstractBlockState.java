package org.grayhack.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;

import org.grayhack.GrayHack;
import org.grayhack.event.events.EventRenderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class MixinAbstractBlockState {

	@Inject(method = "isOpaque", at = @At("HEAD"), cancellable = true)
	private void isOpaque(CallbackInfoReturnable<Boolean> callback) {
		EventRenderBlock.Opaque event = new EventRenderBlock.Opaque((BlockState) (Object) this);
		GrayHack.eventBus.post(event);

		if (event.isOpaque() != null)
			callback.setReturnValue(event.isOpaque());
	}
}
