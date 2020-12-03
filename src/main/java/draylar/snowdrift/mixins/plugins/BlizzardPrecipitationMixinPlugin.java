package draylar.snowdrift.mixins.plugins;

import draylar.snowdrift.Snowdrift;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class BlizzardPrecipitationMixinPlugin extends BlizzardMixinPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return Snowdrift.CONFIG.enableBlizzard && Snowdrift.CONFIG.constantSnow;
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        Snowdrift.LOGGER.info("[Snowdrift] \"Blizzard: Eternal Snowfall\" functionality enabled. Let It Snow!");
    }
}
