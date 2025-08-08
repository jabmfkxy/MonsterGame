package org.yukk1o.MonsterWar.Utils;

import io.fairyproject.container.Autowired;
import io.fairyproject.container.InjectableComponent;
import io.fairyproject.container.PostInitialize;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.yukk1o.MonsterWar.Manager.ChatColorManager;
import org.yukk1o.MonsterWar.Manager.TeamManager;

/**
 * 队伍验证工具类（单例模式实现，提供全局统一访问点）
 */
@InjectableComponent
@NoArgsConstructor
public class TeamValidationUtil {

    @Autowired
    private TeamManager teamManager;

    /**
     * 验证队伍创建参数
     * @param teamName 队伍名称
     * @param color 队伍颜色
     * @return 错误信息，无错误则返回null
     */
    public String validateTeamCreation(String teamName, ChatColor color) {
        if (teamName == null || teamName.trim().isEmpty()){
            return ChatColorManager.error("队伍名字不能为空");
        }
        if (teamName.length() > 16){
            return ChatColorManager.error("队伍名称不能超过16个字符");
        }
        if (teamManager.teamExist(teamName)){
            return ChatColorManager.error("队伍名称已存在");
        }
        if (color == null || color.isColor()) {
            return ChatColorManager.error("队伍颜色不能为空");
        }
        return null;
    }

    /**
     * 获取加入队伍失败的原因
     * @param player 玩家
     * @param teamName 队伍名称
     * @return 错误信息，无错误则返回null
     */
    public String validatePlayerJoin(Player player, String teamName) {
        if (!teamManager.teamExist(teamName)) {
            return ChatColorManager.error("队伍不存在");
        }
        if (isPlayerInSpecificTeam(player, teamName)) {
            return ChatColorManager.error("你已经在该队伍");
        }
        if (teamManager.isTeamFull(teamName)) {
            return ChatColorManager.error("该队伍已满员");
        }
        return null;
    }

    public String validateTeamSwitch(Player player, String newTeamName) {
        if (!teamManager.teamExist(newTeamName)) {
            return ChatColorManager.error("目标队伍不存在");
        }
        if (isPlayerInSpecificTeam(player, newTeamName)) {
            return ChatColorManager.error("你已经在该队伍,无需切换");
        }
        if (!isInAnyTeam(player)){
            return ChatColorManager.error("你不在任何队伍");
        }
        if (teamManager.isTeamFull(newTeamName)) {
            return ChatColorManager.error("目标队伍已满员,无法切换");
        }
        return null;
    }

    /**
     * 获取离开队伍失败的原因
     * @param player 玩家
     * @param teamName 队伍名称
     * @return 错误信息，无错误则返回null
     */
    public String getLeaveErrorReason(Player player, String teamName) {
        if (player == null) {
            return ChatColorManager.error("玩家数据无效");
        }
        if (teamName == null || teamName.trim().isEmpty()) {
            return ChatColorManager.error("队伍名称无效");
        }
        if (!teamManager.teamExist(teamName)) {
            return ChatColorManager.error("队伍不存在");
        }
        if (!teamManager.isPlayerInSpecificTeam(player, teamName)) {
            return ChatColorManager.error("你不在该队伍中，无法离开");
        }
        return null;
    }

    public boolean isPlayerInSpecificTeam(Player player, String teamName) {
        return teamManager.isPlayerInSpecificTeam(player, teamName);
    }

    public boolean isInAnyTeam(Player player) {
        return player != null && teamManager.getPlayerTeam(player) != null;
    }
}
