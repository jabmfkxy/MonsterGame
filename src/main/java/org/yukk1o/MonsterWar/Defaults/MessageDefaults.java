package org.yukk1o.MonsterWar.Defaults;

public final class MessageDefaults {
    private MessageDefaults() {
    }

    // 队伍相关消息
    public static final String ALREADY_IN_TEAM = "你已在该队伍";
    public static final String TEAM_FULL = "队伍已满";
    public static final String JOIN_SUCCESS = "成功加入%s";
    public static final String LEAVE_SUCCESS = "已成功离开队伍";
    public static final String NOT_IN_TEAM = "你不在任何队伍中";
    public static final String TEAM_NOT_EXIST = "队伍不存在";

    // 菜单相关消息
    public static final String MENU_TITLE = "队伍选择";
    public static final String MENU_INIT_ERROR = "队伍菜单初始化失败";

    // 配置相关消息
    public static final String NO_TEAMS_CONFIG = "配置文件中未找到teams节点,使用默认队伍";
}
