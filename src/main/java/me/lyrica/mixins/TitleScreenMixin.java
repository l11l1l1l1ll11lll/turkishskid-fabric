package me.lyrica.mixins;

import me.lyrica.Lyrica;
import me.lyrica.utils.IMinecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen implements IMinecraft {
    @Shadow private boolean doBackgroundFade;

    @Shadow private float backgroundAlpha;

    @Shadow private long backgroundFadeStart;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)I"))
    private void render$drawTextWithShadow(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {


        String primaryText = "";
        String secondaryText = "";
        Color color = Color.WHITE;

        {
            secondaryText = "An update is available for Lyrica.";
            primaryText = "Please restart the game to apply changes.";
            color = Color.ORANGE;
        }

        {
            secondaryText = "Failed to connect to Lyrica's servers.";
            primaryText = "Please make sure you have a working internet connection.";
            color = Color.RED;
        }

         {
            secondaryText = "Failed to update Lyrica.";
            primaryText = "Please make sure the auto-updater is working properly.";
            color = Color.RED;
        }

         {
            primaryText = "Lyrica is on the latest version.";
        }

        if (primaryText.isEmpty()) return;

        float f = 1.0F;
        if (doBackgroundFade) {
            float g = (float) (Util.getMeasuringTimeMs() - backgroundFadeStart) / 2000.0F;
            if (g > 1.0F) {
                doBackgroundFade = false;
                backgroundAlpha = 1.0F;
            } else {
                g = MathHelper.clamp(g, 0.0F, 1.0F);
                f = MathHelper.clampedMap(g, 0.5F, 1.0F, 0.0F, 1.0F);
                this.backgroundAlpha = MathHelper.clampedMap(g, 0.0F, 0.5F, 0.0F, 1.0F);
            }
        }

        int i = MathHelper.ceil(f * 255.0F) << 24;

        if ((i & -67108864) != 0) {
            if (!secondaryText.isEmpty()) context.drawTextWithShadow(mc.textRenderer, secondaryText, width / 2 - mc.textRenderer.getWidth(secondaryText) / 2, height - 30, color.getRGB() | i);
            context.drawTextWithShadow(mc.textRenderer, primaryText, width / 2 - mc.textRenderer.getWidth(primaryText) / 2, height - 20, color.getRGB() | i);
        }
    }
}
