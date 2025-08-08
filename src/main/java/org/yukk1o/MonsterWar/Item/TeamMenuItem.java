package org.yukk1o.MonsterWar.Item;

import io.fairyproject.bukkit.listener.ListenerRegistry;
import io.fairyproject.bukkit.util.items.FairyItem;
import io.fairyproject.bukkit.util.items.FairyItemRegistry;
import io.fairyproject.bukkit.util.items.behaviour.ItemBehaviour;

import io.fairyproject.container.InjectableComponent;
import io.fairyproject.container.PostInitialize;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.yukk1o.MonsterWar.Menu.TeamMenu;

@InjectableComponent
@RequiredArgsConstructor
public class TeamMenuItem {

    private FairyItem teamMenuItem;

    private FairyItemRegistry fairyItemRegistry;
    private ListenerRegistry listenerRegistry;

    private TeamMenu teamMenu;

    public void createItem() {
        this.teamMenuItem = FairyItem.builder("teamMenuItem")
                .item(new ItemStack(Material.DIAMOND_SWORD))

                .behaviour(ItemBehaviour.interact(listenerRegistry, (player, itemStack, action, event) ->
                        teamMenu.openMenu(player), Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK))

                .create(fairyItemRegistry);
    }
}
