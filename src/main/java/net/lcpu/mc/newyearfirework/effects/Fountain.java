// Copyright 2024, 000lbh, all right reserved

package net.lcpu.mc.newyearfirework.effects;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public class Fountain extends AbstractEffect {

    private final JavaPlugin plugin;
    private int speed; // particle(s) per tick
    private int duration; // tick
    private int queueLength; // max particle number
    private Particle particle;
    private Color color;
    private Location center;

    public Fountain(JavaPlugin plugin) {
        this.plugin = plugin;
        this.speed = 10;
        this.duration = 200;
        this.queueLength = 200;
        this.particle = Particle.FLAME;
        this.color = null;
        this.center = new Location(plugin.getServer().getWorlds().get(0), 0, 128, 0);
    }

    public Fountain(JavaPlugin plugin, int speed, int duration, int queueLength, Particle particle, Color color, Location center) {
        this.plugin = plugin;
        this.speed = speed;
        this.duration = duration;
        this.queueLength = queueLength;
        this.particle = particle;
        this.center = center;
    }

    public void show() {
        var fr = new FountainRunnable(particle, null, center, speed, queueLength);
        for (int i = 0; i < duration; i++)
            plugin.getServer().getScheduler().runTaskLater(plugin, fr, i);
    }
}
