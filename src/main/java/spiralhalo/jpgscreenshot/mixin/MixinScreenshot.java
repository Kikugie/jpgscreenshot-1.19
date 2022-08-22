package spiralhalo.jpgscreenshot.mixin;

import net.minecraft.client.util.ScreenshotRecorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;

@Mixin(ScreenshotRecorder.class)
public class MixinScreenshot {
    @Redirect(method = "Lnet/minecraft/client/util/ScreenshotRecorder;getScreenshotFilename(Ljava/io/File;)Ljava/io/File;", at = @At(value = "NEW", target = "java/io/File"), require = 1)
    private static File createFile(File parent, String child) {
        return new File(parent, child.replace(".png", ".jpg"));
    }
}
