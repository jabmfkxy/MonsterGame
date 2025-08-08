package org.yukk1o.MonsterWar.Entity;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 队伍实体类
 * 封装单个队伍的属性（名称、颜色、成员等）和相关操作
 */
public class MonsterWarTeam {
    /**
     */
    @Getter
    private final String name;
    @Getter
    private final ChatColor color;
    @Getter
    private final Team bukkitTeam;

    private final Set<UUID> members = new CopyOnWriteArraySet<>();

    public MonsterWarTeam(@NotNull String name, @NotNull ChatColor color){
        this.name = name;
        this.color = color;

        // 获取或创建Bukkit计分板队伍
        this.bukkitTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(name);
        bukkitTeam.setColor(color);
        bukkitTeam.setPrefix("[" + name + "]");
        bukkitTeam.setCanSeeFriendlyInvisibles(false);
    }

    /**
     * 添加玩家到队伍
     *
     * @param player 要添加的玩家
     */
    public void addMember(@NotNull Player player){
        UUID uuid = player.getUniqueId();
        if (!members.contains(uuid)) {
            members.add(uuid);
            bukkitTeam.addEntry(player.getName());
        }
    }

    /**
     * 从队伍中移除玩家
     * @param player 要移除的玩家
     */
    public void removeMember(@NotNull Player player) {
        UUID uuid = player.getUniqueId();
        if (members.contains(uuid)) {
            members.remove(uuid);
            bukkitTeam.removeEntry(player.getName()); // 同步到Bukkit计分板
        }
    }

    /**
     * 从队伍中移除离线玩家
     * @param uuid 要移除的离线玩家的uuid
     */
    public void removeOfflineMember(UUID uuid) {
        if (members.contains(uuid)) {
            members.remove(uuid);
            // 离线玩家无法获取名称，使用UUID作为标识
            bukkitTeam.removeEntry(uuid.toString());
        }
    }

    /**
     * 请空无效成员（离线玩家）
     */
    public void clearOfflineMembers(){
        members.forEach(this::removeOfflineMember);
    }

    /**
     * 判断玩家UUID是否在当前队伍中
     * @param uuid 要检查的玩家UUID
     * @return 如果UUID在队伍中返回true，否则返回false
     */
    public boolean isMember(UUID uuid) {
        return uuid != null && members.contains(uuid);
    }

    /// Getter
    public int getMemberCount(){return members.size();}
    public List<UUID> getMembers(){return new ArrayList<>(members);}
}
