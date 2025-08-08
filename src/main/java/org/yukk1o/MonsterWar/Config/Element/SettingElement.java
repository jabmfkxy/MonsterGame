package org.yukk1o.MonsterWar.Config.Element;

import io.fairyproject.config.annotation.ConfigurationElement;
import lombok.Getter;

@ConfigurationElement
@Getter
public class SettingElement {
    private int TeamMaxSize = 5;

    public SettingElement() {
    }
}
