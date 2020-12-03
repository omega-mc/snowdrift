package draylar.snowdrift.mixins;

import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public abstract class BlizzardLevelPropertiesMixin {

    @Inject(method = "isRaining", at = @At("HEAD"), cancellable = true)
    public void forcePrecipitation(CallbackInfoReturnable<Boolean> ctx) {
        ctx.setReturnValue(true);
    }
}
