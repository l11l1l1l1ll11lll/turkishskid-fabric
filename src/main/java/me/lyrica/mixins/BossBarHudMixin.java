package me.lyrica.mixins;

import me.lyrica.Lyrica;
import me.lyrica.modules.impl.visuals.NoRenderModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossBarHudMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(DrawContext context, CallbackInfo info) {
        if (Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).isToggled() && Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).bossBar.getValue()) {
            info.cancel();
        }
    }
}
