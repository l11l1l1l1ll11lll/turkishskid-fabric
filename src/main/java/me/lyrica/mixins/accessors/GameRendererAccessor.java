package me.lyrica.mixins.accessors;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Pool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor
    Pool getPool();
}
