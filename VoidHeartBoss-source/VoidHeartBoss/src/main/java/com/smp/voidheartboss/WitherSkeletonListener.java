package com.smp.voidheartboss;

import org.bukkit.Material;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class WitherSkeletonListener implements Listener {

    private final VoidHeartBoss plugin;
    private final Random random = new Random();
    private static final double HEAD_DROP_CHANCE = 0.15;

    public WitherSkeletonListener(VoidHeartBoss plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWitherSkeletonDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof WitherSkeleton)) return;

        event.getDrops().removeIf(item -> item.getType() == Material.WITHER_SKELETON_SKULL);

        if (random.nextDouble() < HEAD_DROP_CHANCE) {
            event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL));

            if (event.getEntity().getKiller() != null) {
                event.getEntity().getKiller().sendMessage(
                    "\u00a78[\u00a7cVoidHeart\u00a78] \u00a77Une \u00a7cTete de Wither Skeleton \u00a77est tombee !"
                );
            }
        }
    }
}
