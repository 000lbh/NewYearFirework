// Copyright 2024, 000lbh, all right reserved

package net.lcpu.mc.newyearfirework.effects;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Random;

public class FountainRunnable implements Runnable {

    private static class MyParticle {
        public double x;
        public double y;
        public double z;
        public double dx;
        public double dy;
        public double dz;
        public static final double d2y = -0.02;
    };

    private final int speed;
    private final int queueLength;
    private final Particle particleType;
    private final Color color;
    private final Location center;
    private final LinkedList<MyParticle> particles;

    public FountainRunnable(@NotNull Particle particleType, Color color, @NotNull Location center, int speed, int queueLength) {
        super();
        this.particleType = particleType;
        this.color = particleType.equals(Particle.REDSTONE) ? color : null;
        this.center = center;
        this.speed = speed;
        this.queueLength = queueLength;
        this.particles = new LinkedList<>();
    }

    private void updateParticles() {
        for (var particle : particles) {
            particle.x += particle.dx;
            particle.y += particle.dy;
            particle.z += particle.dz;
            particle.dy += MyParticle.d2y;
        }
        var random = new Random();
        for (int i = 0; i < speed; i++) {
            double radicalSpeed = random.nextExponential() * 0.01;
            double verticalSpeed = random.nextGaussian(0.5, 0.1);
            double angle = 2 * Math.PI * random.nextDouble();
            var particle = new MyParticle();
            particle.x = center.x();
            particle.y = center.y();
            particle.z = center.z();
            particle.dx = radicalSpeed * Math.sin(angle);
            particle.dy = verticalSpeed;
            particle.dz = radicalSpeed * Math.cos(angle);
            particles.addLast(particle);
        }
        if (particles.size() > queueLength) {
            int cnt = particles.size() - queueLength;
            for (int i = 0; i < cnt; i++)
                particles.removeFirst();
        }
    }

    private void showParticles() {
        var pb = new ParticleBuilder(particleType);
        pb.color(color).extra(0.5).allPlayers();
        for (var particle : particles) {
            pb.location(center.getWorld(), particle.x, particle.y, particle.z).spawn();
        }
    }

    @Override
    public void run() {
        updateParticles();
        showParticles();
    }
}
