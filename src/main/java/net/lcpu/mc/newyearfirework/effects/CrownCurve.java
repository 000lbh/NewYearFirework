// Copyright 2024, 000lbh, all right reserved

package net.lcpu.mc.newyearfirework.effects;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CrownCurve extends AbstractEffect {

    private final JavaPlugin plugin;
    private Location center;
    private Color color;
    private Particle particle;
    private double radius;
    private double finalRadius;
    private int curvePeriod;
    private double duration;
    // How many particle(s) in one block
    static private final int SAMPLE_RATE = 10;

    public CrownCurve(JavaPlugin plugin, Location center, double radius, Color color, Particle particle, int count) {
        this.plugin = plugin;
        this.center = center;
        this.color = color;
        this.radius = radius;
        this.finalRadius = Double.NaN;
        this.particle = particle;
        this.curvePeriod = count;
        this.duration = 0;
    }

    public CrownCurve() {
        this.plugin = null;
        this.radius = 1.0;
        this.color = Color.WHITE;
        this.particle = Particle.REDSTONE;
        this.center = null;
        this.curvePeriod = 5;
        this.duration = 0;
    }

    public CrownCurve setColor(@NotNull Color color) {
        this.color = color;
        return this;
    }

    public CrownCurve setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public CrownCurve setFinalRadius(double finalRadius) {
        this.finalRadius= finalRadius;
        return this;
    }

    public CrownCurve setCenter(@NotNull Location center) {
        this.center = center;
        return this;
    }

    public CrownCurve setParticle(@NotNull Particle particle) {
        this.particle = particle;
        return this;
    }

    public CrownCurve setCurvePeriod(int count) {
        this.curvePeriod = count;
        return this;
    }

    public CrownCurve setDuration(double duration) {
        if (plugin == null)
            throw new IllegalArgumentException("Set plugin first");
        this.duration = duration;
        return this;
    }

    public void show() {
        if (center == null)
            throw new IllegalArgumentException("Set center first");
        if (center.getWorld() == null)
            throw new IllegalArgumentException("World in center must be set");
        if (color != null && !particle.equals(Particle.REDSTONE))
            throw new IllegalArgumentException("Only REDSTONE can set color");
        var pb = new ParticleBuilder(particle);
        pb.color(color).offset(0, 0, 0).extra(0.05);
        final var x = center.x();
        final var y = center.y();
        final var z = center.z();
        final double full_length = 2 * Math.PI * (Double.isNaN(finalRadius) ? radius : Math.max(radius, finalRadius));
        final int sample_count = (int)(full_length * SAMPLE_RATE);
        for (int i = 0; i < sample_count; i++) {
            double angle = i * 2 * Math.PI / sample_count;
            final var currentRadius = Double.isNaN(finalRadius) ? radius : radius + (finalRadius - radius) * (double) i / sample_count;
            final double x1 = x + Math.sin(angle) * currentRadius;
            final double z1 = z + Math.cos(angle) * currentRadius;
            final var loc1 = new Location(center.getWorld(), x1, y + 3 * Math.sin(angle * curvePeriod), z1);
            final var loc2 = new Location(center.getWorld(), x1, y - 3 * Math.sin(angle * curvePeriod), z1);
            int tick = (int)(i * duration / sample_count * 20);
            if (tick > 0) {
                assert plugin != null;
                plugin.getServer().getScheduler().runTaskLater(
                        plugin,
                        () -> {
                            pb.location(loc1).spawn();
                            pb.location(loc2).spawn();
                        },
                        tick
                );
            }
            else {
                pb.location(loc1).spawn();
                pb.location(loc2).spawn();
            }
        }
    }
}
