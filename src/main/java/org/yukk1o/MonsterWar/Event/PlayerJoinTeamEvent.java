package org.yukk1o.MonsterWar.Event;


import net.legacy.library.libs.de.leonhard.storage.shaded.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import org.yukk1o.MonsterWar.Entity.MonsterWarTeam;

public class PlayerJoinTeamEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final MonsterWarTeam team;

    /**
     * 构造函数
     * @param player 加入队伍的玩家
     * @param team 目标队伍
     */
    public PlayerJoinTeamEvent(Player player, MonsterWarTeam team){
        this.player = player;
        this.team = team;
    }

    public Player getPlayer(){
        return player;
    }

    public MonsterWarTeam getTeam(){
        return team;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
