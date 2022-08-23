package spiralhalo.jpgscreenshot.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

@Config(name = "jpgscreenshot")
public class ModConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(max = 100, min = 1)
    public int quality = 80;

    @ConfigEntry.Gui.Excluded
    private static ModConfig INSTANCE;

    public static void init() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            init();
        }
        return INSTANCE;
    }


}