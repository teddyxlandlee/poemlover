package xland.mcmod.poemlover;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ThePlugin implements IMixinConfigPlugin {
    private String refmapName;
    static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onLoad(String mixinPackage) {
        refmapName = Platform.get().getRefmap();
    }

    @Override
    public String getRefMapperConfig() {
        return refmapName;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if ("xland.mcmod.poemlover.mixin.Stub".equals(mixinClassName)) {
            LOGGER.info("Mixin {}", mixinClassName);
            Consumer<ClassNode> c = new ASMClientPacketListener(switch (Platform.get()) {
            	case NEO -> NEO;
                case FABRIC -> FABRIC;
                case FORGE -> FORGE;
            });
            c.accept(targetClass);
        }
    }

    private static final String[] NEO, FABRIC, FORGE;
    static {
    	NEO = new String[] {
                "net.minecraft.network.protocol.game.ClientboundGameEventPacket.getParam:()F",
                "net.minecraft.network.protocol.game.ClientboundGameEventPacket.getEvent:()Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket$Type;",
                "net.minecraft.network.protocol.game.ClientboundGameEventPacket.WIN_GAME:Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket$Type;",
                "net.minecraft.client.multiplayer.ClientPacketListener.handleGameEvent:(Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket;)V"
    	};
        FABRIC = new String[] {
                "net.minecraft.class_2668.method_11492:()F",
                "net.minecraft.class_2668.method_11491:()Lnet/minecraft/class_2668$class_5402;",
                "net.minecraft.class_2668.field_25649:Lnet/minecraft/class_2668$class_5402;",
                "net.minecraft.class_634.method_11085:(Lnet/minecraft/class_2668;)V"
        };
        FORGE = new String[] {
                "net.minecraft.network.protocol.game.ClientboundGameEventPacket.m_132181_:()F",
                "net.minecraft.network.protocol.game.ClientboundGameEventPacket.m_132178_:()Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket$Type;",
                "net.minecraft.network.protocol.game.ClientboundGameEventPacket.f_132157_:Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket$Type;",
                "net.minecraft.client.multiplayer.ClientPacketListener.m_7616_:(Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket;)V"
        };
    }
}
