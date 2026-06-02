package com.smp.voidheartboss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class AuraListener implements Listener {

    private final VoidHeartBoss plugin;

    public AuraListener(VoidHeartBoss plugin) {
        this.plugin = plugin;
        startAuraTask();
    }

    private void startAuraTask() {
        new BukkitRunnable() {
            double angle = 0;
            double waveOffset = 0;

            @Override
            public void run() {
                angle += 8;
                waveOffset += 0.15;

                for (Player p : Bukkit.getOnlinePlayers()) {
                    boolean hasElder = plugin.hasElderReward(p.getUniqueId());
                    boolean hasWither = plugin.hasWitherReward(p.getUniqueId());

                    if (hasElder) spawnWaterBreathAura(p, angle, waveOffset);
                    if (hasWither) spawnFlameBreathAura(p, angle, waveOffset);
                }
            }

            private void spawnWaterBreathAura(Player p, double angle, double waveOffset) {
                Location loc = p.getLocation().clone().add(0, 0.5, 0);

                for (int wave = 0; wave < 3; wave++) {
                    double waveAngleOffset = wave * (Math.PI * 2 / 3);
                    for (int i = 0; i < 8; i++) {
                        double t = i / 8.0;
                        double rad = Math.toRadians(angle) + waveAngleOffset + (t * Math.PI * 2);
                        double radius = 0.8 + Math.sin(waveOffset + t * Math.PI * 4) * 0.3;
                        double height = t * 1.8 + Math.sin(waveOffset + t * Math.PI * 2) * 0.2;
                        double x = Math.cos(rad) * radius;
                        double z = Math.sin(rad) * radius;
                        p.getWorld().spawnParticle(Particle.FALLING_WATER,
                            loc.clone().add(x, height, z), 1, 0, 0, 0, 0);
                    }
                }

                for (int i = 0; i < 4; i++) {
                    double rad = Math.toRadians(angle * 1.5 + i * 90);
                    double x = Math.cos(rad) * 0.6;
                    double z = Math.sin(rad) * 0.6;
                    p.getWorld().spawnParticle(Particle.END_ROD,
                        loc.clone().add(x, 0.2, z), 1, 0.02, 0.05, 0.02, 0.02);
                }

                for (int i = 0; i < 6; i++) {
                    double rad = Math.toRadians(angle * 0.5 + i * 60);
                    double x = Math.cos(rad) * 1.2;
                    double z = Math.sin(rad) * 1.2;
                    p.getWorld().spawnParticle(Particle.SPLASH,
                        p.getLocation().clone().add(x, 0.05, z), 1, 0, 0, 0, 0);
                }
            }

            private void spawnFlameBreathAura(Player p, double angle, double waveOffset) {
                Location loc = p.getLocation().clone().add(0, 0.3, 0);

                for (int i = 0; i < 6; i++) {
                    double t = i / 6.0;
                    double rad = Math.toRadians(-angle * 1.2) + t * Math.PI * 2;
                    double radius = 0.7 + Math.sin(waveOffset + t * Math.PI * 3) * 0.25;
                    double height = t * 1.6;
                    double x = Math.cos(rad) * radius;
                    double z = Math.sin(rad) * radius;
                    p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,
                        loc.clone().add(x, height, z), 1, 0, 0, 0, 0);
                }

                for (int i = 0; i < 5; i++) {
                    double rad = Math.toRadians(angle + i * 72);
                    double x = Math.cos(rad) * 1.0;
                    double z = Math.sin(rad) * 1.0;
                    p.getWorld().spawnParticle(Particle.FLAME,
                        p.getLocation().clone().add(x, 0.1, z), 1, 0, 0.02, 0, 0.01);
                }

                for (int i = 0; i < 3; i++) {
                    double rad = Math.toRadians(-angle * 0.8 + i * 120);
                    double x = Math.cos(rad) * 0.5;
                    double z = Math.sin(rad) * 0.5;
                    p.getWorld().spawnParticle(Particle.SMOKE,
                        loc.clone().add(x, 1.5, z), 1, 0.05, 0.1, 0.05, 0.01);
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }
}
