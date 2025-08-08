package org.yukk1o.MonsterWar.Config.Element;

import io.fairyproject.config.annotation.ConfigurationElement;
import io.fairyproject.config.annotation.ElementType;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@ConfigurationElement
public class TeamElement {
        private String name = "队伍名";
        private String color_Str = "WHITE";

        public TeamElement() {
        }

        public TeamElement(String name, String color_Str) {
            this.name = name;
            this.color_Str = ChatColor.valueOf(color_Str).isColor() ? color_Str : this.color_Str;
        }

        public ChatColor getColor(){
            return ChatColor.valueOf(color_Str);
        }
}