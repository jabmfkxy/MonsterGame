package org.yukk1o.MonsterWar.Listener;

import io.fairyproject.bukkit.listener.RegisterAsListener;
import io.fairyproject.bukkit.util.items.FairyItemRegistry;
import io.fairyproject.container.Autowired;
import io.fairyproject.container.InjectableComponent;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.yukk1o.MonsterWar.Entity.MonsterWarTeam;
import org.yukk1o.MonsterWar.Manager.TeamManager;

import java.util.UUID;

@InjectableComponent
@RegisterAsListener
public class ChoiringTeam implements Listener {

    private FairyItemRegistry fairyItemRegistry;

    @Autowired
    private TeamManager teamManager;

    /**
     * 玩家加入时初始化（仅在未选队伍时固定物品）
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        initFixedSlots(player);
    }

    /**
     * 为玩家的物品栏加入特定物品
     */
    private void initFixedSlots(Player player) {
        ItemStack teamMenuItem = fairyItemRegistry.get("teamMenuItem").provideItemStack(player);

        PlayerInventory backpack = player.getInventory();
        backpack.setItem(0, teamMenuItem);
    }

    /**
     * 玩家退出时自动退出队伍
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        MonsterWarTeam playerTeam = teamManager.getPlayerTeam(player);
        if (playerTeam != null) {
            UUID uuid = player.getUniqueId();
            playerTeam.removeOfflineMember(uuid);
        }
    }

    /**
     * 阻止点击移动受保护物品
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        event.setCancelled(true);
    }

    /**
     * 阻止拖拽受保护物品
     */
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        event.setCancelled(true);
    }

    /**
     * 阻止丢弃受保护物品
     */
    @EventHandler
    public void onItemDrop(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        event.setCancelled(true);
    }
}
