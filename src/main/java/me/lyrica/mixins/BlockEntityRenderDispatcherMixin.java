package me.lyrica.mixins;

import me.lyrica.Lyrica;
import me.lyrica.modules.impl.visuals.NoRenderModule;
import me.lyrica.utils.IMinecraft;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin implements IMinecraft {
    @Inject(method = "render(Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At("HEAD"), cancellable = true)
    private static <T extends BlockEntity> void render(BlockEntityRenderer<T> renderer, T blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo info) {
        if (Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).isToggled() && !Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileEntities.getValue().equals("None")) {
            if (Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileEntities.getValue().equals("Always") || (Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileEntities.getValue().equals("Distance") && Math.sqrt(mc.player.squaredDistanceTo(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ())) > Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileDistance.getValue().floatValue())) {
                info.cancel();
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity> void render(E blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo info) {
        if (Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).isToggled() && !Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileEntities.getValue().equals("None")) {
            if (Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileEntities.getValue().equals("Always") || (Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileEntities.getValue().equals("Distance") && Math.sqrt(mc.player.squaredDistanceTo(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ())) > Lyrica.MODULE_MANAGER.getModule(NoRenderModule.class).tileDistance.getValue().floatValue())) {
                info.cancel();
            }
        }
    }
}
