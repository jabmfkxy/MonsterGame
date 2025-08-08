package org.yukk1o.MonsterWar;

import io.fairyproject.FairyLaunch;
import io.fairyproject.bootstrap.bukkit.BukkitPlugin;
import io.fairyproject.log.Log;
import io.fairyproject.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

@FairyLaunch
public class Main extends Plugin {
    public static JavaPlugin Plugin;
    static final String[] pascalBanner = {
            "███╗   ███╗ ██████╗ ███╗   ██╗███████╗████████╗███████╗██████╗     ██╗    ██╗ █████╗ ██████╗",
            "████╗ ████║██╔═══██╗████╗  ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗    ██║    ██║██╔══██╗██╔══██╗",
            "██╔████╔██║██║   ██║██╔██╗ ██║███████╗   ██║   █████╗  ██████╔╝    ██║ █╗ ██║███████║██████╔╝",
            "██║╚██╔╝██║██║   ██║██║╚██╗██║╚════██║   ██║   ██╔══╝  ██╔══██╗    ██║███╗██║██╔══██║██╔══██╗",
            "██║ ╚═╝ ██║╚██████╔╝██║ ╚████║███████║   ██║   ███████╗██║  ██║    ╚███╔███╔╝██║  ██║██║  ██║",
            "╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝     ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝"
    };

    @Override
    public void onPreEnable() {
        Plugin = BukkitPlugin.INSTANCE;
    }

    @Override
    public void onPluginEnable()
    {
        for (String banner : pascalBanner){
            Log.info(banner);
        }
    }
}
