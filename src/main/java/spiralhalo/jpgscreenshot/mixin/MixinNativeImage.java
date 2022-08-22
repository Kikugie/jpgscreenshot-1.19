package spiralhalo.jpgscreenshot.mixin;

import net.minecraft.client.texture.NativeImage;
import org.lwjgl.stb.STBImageWrite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spiralhalo.jpgscreenshot.config.ModConfig;

import java.io.File;

@Mixin(NativeImage.class)
public class MixinNativeImage {

    private boolean writeToJpg;

    @Inject(method = "Lnet/minecraft/client/texture/NativeImage;writeTo(Ljava/io/File;)V", at = @At(value = "HEAD"))
    public void startWritingToFile(File path, CallbackInfo ci) {
        if (path.toString().endsWith(".jpg")) {
            writeToJpg = true;
        }
    }

    @Redirect(method = "Lnet/minecraft/client/texture/NativeImage;write(Ljava/nio/channels/WritableByteChannel;)Z", at = @At(value = "INVOKE", target = "Lorg/lwjgl/stb/STBImageWrite;nstbi_write_png_to_func(JJIIIJI)I"))
    private int writeJpg(long func, long context, int w, int h, int comp, long data, int stride_in_bytes) {
        if (writeToJpg) {
            writeToJpg = false;
            return STBImageWrite.nstbi_write_jpg_to_func(func, context, w, h, comp, data, ModConfig.getInstance().quality);
        } else {
            return STBImageWrite.nstbi_write_png_to_func(func, context, w, h, comp, data, stride_in_bytes);
        }
    }

}
