package org.yukk1o.MonsterWar.Config;

import io.fairyproject.config.yaml.YamlConfiguration;
import io.fairyproject.container.InjectableComponent;
import io.fairyproject.container.configuration.Configuration;
import io.fairyproject.log.Log;
import lombok.Getter;
import org.yukk1o.MonsterWar.Config.Element.SettingElement;
import org.yukk1o.MonsterWar.Config.Element.TeamElement;
import org.yukk1o.MonsterWar.Defaults.TeamDefaults;

import static org.yukk1o.MonsterWar.Main.Plugin;

/// 配置文件
@Getter
@InjectableComponent
public class ConfigYaml extends YamlConfiguration {
    private SettingElement setting = new SettingElement();
    private TeamElement team1 = new TeamElement(TeamDefaults.TEAM1_NAME, TeamDefaults.TEAM1_COLOR_Str);
    private TeamElement team2 = new TeamElement(TeamDefaults.TEAM2_NAME, TeamDefaults.TEAM2_COLOR_Str);


    public ConfigYaml() {
        super(Plugin.getDataFolder().toPath().resolve("config.yml"));
        Log.debug("尝试创建config文件");
        this.loadAndSave();
    }
}
