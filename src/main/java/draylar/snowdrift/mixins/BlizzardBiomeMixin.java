package draylar.snowdrift.mixins;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BlizzardBiomeMixin {
    @Shadow
    @Final
    private Biome.Category category;

    @Inject(method = "getTemperature()F", at = @At("HEAD"), cancellable = true)
    public void setTemperature(CallbackInfoReturnable<Float> ctx) {
        if (this.category != Biome.Category.NETHER && this.category != Biome.Category.THEEND)
            ctx.setReturnValue(0.0F);
    }

    @Inject(method = "getPrecipitation", at = @At("HEAD"), cancellable = true)
    public void setWeather(CallbackInfoReturnable<Biome.Precipitation> ctx) {
        if (this.category != Biome.Category.NETHER && this.category != Biome.Category.THEEND)
            ctx.setReturnValue(Biome.Precipitation.SNOW);
    }
}
