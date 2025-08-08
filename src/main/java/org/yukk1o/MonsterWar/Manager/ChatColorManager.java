package org.yukk1o.MonsterWar.Manager;

import org.bukkit.ChatColor;

public class ChatColorManager {

    // 颜色代码常量定义
    public static final String COLOR_ERROR = ChatColor.RED.toString();         // §c
    public static final String COLOR_SUCCESS = ChatColor.GREEN.toString();     // §a
    public static final String COLOR_INFO = ChatColor.AQUA.toString();         // §b
    public static final String COLOR_WARNING = ChatColor.YELLOW.toString();    // §e
    public static final String COLOR_HIGHLIGHT = ChatColor.LIGHT_PURPLE.toString(); // §d
    public static final String COLOR_RESET = ChatColor.RESET.toString();       // §r

    // 格式代码常量定义
    public static final String FORMAT_BOLD = ChatColor.BOLD.toString();        // §l
    public static final String FORMAT_ITALIC = ChatColor.ITALIC.toString();    // §o
    public static final String FORMAT_UNDERLINE = ChatColor.UNDERLINE.toString(); // §n

    /**
     * 格式化错误消息
     * @param message 原始消息
     * @return 带错误颜色的消息
     */
    public static String error(String message){
        return COLOR_ERROR + message + COLOR_RESET;
    }

    /**
     * 格式化成功消息
     * @param message 原始消息
     * @return 带成功颜色的消息
     */
    public static String success(String message){
        return COLOR_SUCCESS + message + COLOR_RESET;
    }

    /**
     * 格式化信息消息
     * @param message 原始消息
     * @return 带信息颜色的消息
     */
    public static String info(String message){
        return COLOR_INFO + message + COLOR_RESET;
    }

    /**
     * 格式化警告消息
     * @param message 原始消息
     * @return 带警告颜色的消息
     */
    public static String warning(String message){
        return COLOR_WARNING + message + COLOR_RESET;
    }

    /**
     * 格式化高光消息
     * @param message 原始消息
     * @return 带高光颜色的消息
     */
    public static String highlight(String message){
        return COLOR_HIGHLIGHT + message + COLOR_HIGHLIGHT;
    }

}
