package org.yukk1o.MonsterWar.Defaults;

import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * 队伍默认配置常量类
 * 集中管理所有与队伍相关的默认值，避免分散定义
 */
@NoArgsConstructor
public final class TeamDefaults {

    /// 队伍默认最大人数
    public static final int DEFAULT_MIN_TEAM_SIZE = 5;

    /// 默认队伍1配置
    public static final String TEAM1_NAME = "红队";
    public static final String TEAM1_COLOR_Str= "RED";
    public static final Material TEAM1_MATERIAL = Material.RED_WOOL;


    /// 默认队伍2配置
    public static final String TEAM2_NAME = "蓝队";
    public static final String TEAM2_COLOR_Str = "BLUE";
    public static final Material TEAM2_MATERIAL = Material.BLUE_WOOL;

}