package org.yukk1o.MonsterWar.Manager;

import lombok.RequiredArgsConstructor;
import io.fairyproject.container.Autowired;
import io.fairyproject.container.InjectableComponent;
import io.fairyproject.container.PostInitialize;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.yukk1o.MonsterWar.Config.ConfigYaml;
import org.yukk1o.MonsterWar.Config.Element.TeamElement;
import org.yukk1o.MonsterWar.Entity.MonsterWarTeam;
import org.yukk1o.MonsterWar.Event.PlayerJoinTeamEvent;
import org.yukk1o.MonsterWar.Event.PlayerLeaveTeamEvent;
import org.yukk1o.MonsterWar.Menu.TeamMenu;
import org.yukk1o.MonsterWar.Utils.TeamValidationUtil;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@InjectableComponent
@RequiredArgsConstructor
public class TeamManager {

    @Autowired
    private ConfigYaml config;

    @Autowired
    private TeamValidationUtil validationUtil;

    @Autowired
    private TeamMenu teamMenu;

    /// 存储所有队伍 (队伍名称 -> 队伍实体)
    private final Map<String, MonsterWarTeam> teams = new ConcurrentHashMap<>();

    /// 存储玩家所属队伍 (玩家UUID -> 队伍名称)
    private final Map<UUID,String> playerTeamMap = new ConcurrentHashMap<>();

    @Getter
    private int maxTeamSize;

    @PostInitialize
    public void onPostInitialize(){
        this.maxTeamSize = config.getSetting().getTeamMaxSize();
        initializeDefaultTeams();
    }

    /**
     * 从配置中初始化队伍
     */
    private void initializeDefaultTeams() {
        // 从配置初始化队伍
        createTeam(config.getTeam1());
        createTeam(config.getTeam2());
    }

    /**
     * 创建新队伍
     *
     * @param team  队伍对象
     * @return true如果队伍创建成功，false如果队伍已存在
     */
    public String createTeam(TeamElement team){
        String teamName = team.getName();
        ChatColor color = team.getColor();
        String error = validationUtil.validateTeamCreation(teamName, color);
        if (error != null) return error;

        teams.put(teamName, new MonsterWarTeam(teamName, color));
        return ChatColorManager.success("队伍 " + teamName + " 创建成功，");
    }

    /**
     * 令玩家加入指定队伍
     * @param player    目标玩家
     * @param teamName  队伍名称
     * @return  操作结果显示
     */
    public String addPlayerToTeam(@NotNull Player player,@NotNull String teamName){
        String error = validationUtil.validatePlayerJoin(player, teamName);
        if (error != null) return error;

        MonsterWarTeam team = teams.get(teamName);
        team.addMember(player);
        playerTeamMap.put(player.getUniqueId(), teamName);

        Bukkit.getPluginManager().callEvent(new PlayerJoinTeamEvent(player, team));
        teamMenu.refreshMenuCache();
        return ChatColorManager.success("成功加入队伍: " + teamName);
    }

    /**
     * 从指定队伍中移除玩家
     * @param player 目标玩家
     * @return 操作结果消息
     */
    public String removePlayerFromTeam(@NotNull Player player){
        UUID uuid = player.getUniqueId();
        String teamName = playerTeamMap.get(uuid);

        String error = validationUtil.getLeaveErrorReason(player, teamName);
        if (error != null) return error;

        MonsterWarTeam team = teams.get(teamName);
        team.removeMember(player);
        playerTeamMap.remove(player.getUniqueId());

        Bukkit.getPluginManager().callEvent(new PlayerLeaveTeamEvent(player, team));

        teamMenu.refreshMenuCache();

        return ChatColorManager.success("已成功离开队伍: " + teamName);
    }

    /**
     * 玩家切换队伍（先离开当前队伍，再加入新队伍）
     * @param player 玩家对象
     * @param targetTeamName 新队伍名称
     * @return 操作结果消息
     */
    public String switchTeam(@NotNull Player player, @NotNull String targetTeamName){
        String error = validationUtil.validateTeamSwitch(player, targetTeamName);

        if (error != null) {
            return error;
        }
        String currentTeamName = getPlayerTeam(player).getName();

        removePlayerFromTeam(player);

        String joinResult = addPlayerToTeam(player, targetTeamName);
        teamMenu.refreshMenuCache();
        return joinResult;
    }

    /**
     * 判断玩家是否在指定队伍中
     * @param player 玩家对象
     * @param teamName 队伍名称
     * @return 若在指定队伍中则返回true
     */
    public boolean isPlayerInSpecificTeam(Player player, String teamName){
        if (player == null || teamName == null) return false;

        String currentTeam = playerTeamMap.get(player.getUniqueId());
        return teamName.equals(currentTeam);
    }

    /**
     * 获取玩家所在队伍
     * @param player 玩家对象
     * @return 玩家所在队伍，若不在任何队伍则返回null
     */
    public MonsterWarTeam getPlayerTeam(Player player){
        if (player == null) return null;

        String teamName = playerTeamMap.get(player.getUniqueId());
        return teamName != null ? teams.get(teamName) : null;
    }

    /**
     * 通过队伍名称获取队伍
     *
     * @param teamName 队伍名称
     * @return 队伍对象
     */
    public MonsterWarTeam getTeam(String teamName){
        return teams.get(teamName);
    }

    /**
     * 检查队伍是否存在
     *
     * @param teamName 队伍名称
     * @return 如果队伍存在则返回true，否则返回false
     */
    public boolean teamExist(@NotNull String teamName) {
        return teams.containsKey(teamName);
    }

    /**
     * 检查队伍是否已满
     * @param teamName 队伍名称
     * @return 若队伍已满则返回true
     */
    public boolean isTeamFull(String teamName){
        MonsterWarTeam team =teams.get(teamName);
        return team != null && team.getMemberCount() >= maxTeamSize;
    }

}
