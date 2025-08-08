package org.yukk1o.MonsterWar.Menu;

import io.fairyproject.bukkit.gui.Gui;
import io.fairyproject.bukkit.gui.pane.NormalPane;
import io.fairyproject.bukkit.gui.pane.Pane;
import io.fairyproject.bukkit.gui.slot.GuiSlot;
import io.fairyproject.bukkit.nbt.NBTKey;
import io.fairyproject.bukkit.util.items.ItemBuilder;
import io.fairyproject.container.Autowired;
import io.fairyproject.container.InjectableComponent;
import io.fairyproject.container.PostInitialize;
import io.fairyproject.log.Log;
import io.fairyproject.bukkit.gui.GuiFactory;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.yukk1o.MonsterWar.Config.ConfigYaml;
import org.yukk1o.MonsterWar.Entity.MonsterWarTeam;
import org.yukk1o.MonsterWar.Manager.TeamManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@InjectableComponent
public class TeamMenu {

    private ConfigYaml config;

    private TeamManager teamManager;

    private GuiFactory guiFactory;

    private final NormalPane airPane = Pane.normal(1, 4);

    /// 存储所有队伍的菜单 (队伍名称 -> 菜单实体)
    Map<String, Gui> teamMenuCache = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    private String team1Name;
    private String team2Name;

    /**
     * 初始化队伍菜单槽位配置
     */
    @PostInitialize
    private void initMenu() {
        this.team1Name = config.getTeam1().getName();
        this.team2Name = config.getTeam1().getName();
    }

    private Gui getMenuForPlayer(Player player) {
        if (!isMenuInitialized()) {
            Log.warn("菜单未初始化，使用默认配置");
            initMenu();
        }

        MonsterWarTeam playerTeam = teamManager.getPlayerTeam(player);
        String teamKey = playerTeam != null ? playerTeam.getName() : "DEFAULT";

        return teamMenuCache.computeIfAbsent(teamKey, k -> createTeamSpecificMenu(teamKey));
    }

    /**
     * 创建菜单 (缓存未命中)
     */
    private Gui createTeamSpecificMenu(String playerTeamName) {
        Gui gui = guiFactory.create(Component.text("MENU_TITLE"));

        /// 两个队伍的物品窗格
        NormalPane team1_Pane = Pane.normal(4, 4);
        NormalPane team2_Pane = Pane.normal(4, 4);

        /// 使用队伍物品对窗格填充
        team1_Pane.fillEmptySlots(GuiSlot.of(
                createTeamItem(team1Name, playerTeamName)));
        team2_Pane.fillEmptySlots(GuiSlot.of(
                createTeamItem(team1Name, playerTeamName)));

        /// 将窗格填入gui
        gui.addPane(team1_Pane);
        gui.addPane(airPane);
        gui.addPane(team2_Pane);

        return gui;
    }

    /**
     * @param teamName       队伍名称
     * @param playerTeamName 玩家所在队伍
     */
    private ItemBuilder createTeamItem(String teamName, String playerTeamName) {

        MonsterWarTeam team = teamManager.getTeam(teamName);
        if (team == null) {
            throw new RuntimeException("队伍 " + teamName + " 不存在,无法创建物品");
        }

        int currentSize = team.getMemberCount();
        int maxSize = teamManager.getMaxTeamSize();
        ChatColor teamColor = team.getColor();

        List<String> lore = new ArrayList<>();
        if (!teamManager.isTeamFull(teamName)) {
            lore.add(ChatColor.GRAY + "当前人数: " + currentSize + "/" + maxSize);

            if (teamName.equals(playerTeamName)) {
                lore.add(ChatColor.GREEN + "你已经在本队");
            } else lore.add(ChatColor.YELLOW + "点击加入本队");
        } else lore.add(ChatColor.GRAY + "该队伍已满");

        return ItemBuilder
                .of(Material.BARRIER)
                .name(teamColor + teamName)
                .lore(lore);

    }

    /**
     * 清空菜单缓存
     */
    public void refreshMenuCache() {
        lock.lock();
        try {
            teamMenuCache.clear();
            Log.info("队伍菜单缓存已刷新");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 检查菜单是否已初始化
     *
     * @return 若未初始化则返回false，否则返回true
     */
    private boolean isMenuInitialized() {
        return teamMenuCache != null && team1Name != null && team2Name != null;
    }

    /**
     * 重置菜单配置
     */
    public void reset() {
        lock.lock();
        try {
            teamMenuCache.clear();  // 清理菜单缓存
            team1Name = null;
            team2Name = null;
            Log.info("队伍菜单已重置");
        } finally {
            lock.unlock();
        }
    }

    public void openMenu(Player player){
        getMenuForPlayer(player).open(player);
    }
}
