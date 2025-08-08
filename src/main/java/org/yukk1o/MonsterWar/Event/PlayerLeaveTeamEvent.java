package org.yukk1o.MonsterWar.Event;


import lombok.Getter;
import net.legacy.library.libs.de.leonhard.storage.shaded.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.yukk1o.MonsterWar.Entity.MonsterWarTeam;

public class PlayerLeaveTeamEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final Player player;
    @Getter
    private final MonsterWarTeam team;


    /**
     * 构造函数
     * @param player 离开队伍的玩家
     */
    public PlayerLeaveTeamEvent(Player player, MonsterWarTeam team){
        this.player = player;
        this.team = team;
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
