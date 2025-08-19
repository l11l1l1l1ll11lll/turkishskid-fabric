package me.lyrica.mixins;

import me.lyrica.Lyrica;
import me.lyrica.modules.impl.movement.FastWebModule;
import me.lyrica.utils.IMinecraft;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CobwebBlock.class)
public class CobwebBlockMixin implements IMinecraft {
    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    private void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo info) {
        if (Lyrica.MODULE_MANAGER.getModule(FastWebModule.class).isToggled()) {
            if (Lyrica.MODULE_MANAGER.getModule(FastWebModule.class).sneak.getValue() && !mc.player.isSneaking()) return;

            if (Lyrica.MODULE_MANAGER.getModule(FastWebModule.class).mode.getValue().equalsIgnoreCase("Ignore")) {
                entity.onLanding();
                info.cancel();
            }

            if (Lyrica.MODULE_MANAGER.getModule(FastWebModule.class).mode.getValue().equalsIgnoreCase("Strong")) {
                entity.slowMovement(state, new Vec3d(Lyrica.MODULE_MANAGER.getModule(FastWebModule.class).horizontal.getValue().doubleValue(), Lyrica.MODULE_MANAGER.getModule(FastWebModule.class).vertical.getValue().doubleValue(), Lyrica.MODULE_MANAGER.getModule(FastWebModule.class).horizontal.getValue().doubleValue()));
                info.cancel();
            }
        }
    }
}
