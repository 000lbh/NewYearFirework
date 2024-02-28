// Copyright 2024, 000lbh, all right reserved

package net.lcpu.mc.newyearfirework.commands;

import net.lcpu.mc.newyearfirework.effects.AbstractEffect;
import net.lcpu.mc.newyearfirework.effects.CrownCurve;
import net.lcpu.mc.newyearfirework.effects.Fountain;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleCommand extends Command {

    private final JavaPlugin plugin;

    public SimpleCommand(String name, JavaPlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public SimpleCommand(String name, String description, String usageMessage, List<String> aliases, JavaPlugin plugin) {
        super(name, description, usageMessage, aliases);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String name, String[] args) {
        Location loc = null;
        if (sender instanceof Player)
            loc = ((Player) sender).getLocation();
        else if (sender instanceof Block)
            loc = ((Block) sender).getLocation();
        else {
            var world = sender.getServer().getWorlds().get(0);
            loc = new Location(world, 0, 64, 0);
        }
        switch (name) {
            case "style1": {
                if (args.length >= 3) {
                    loc.set(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                }
                double radius = 50;
                if (args.length >= 4) {
                    radius = Double.parseDouble(args[3]);
                }
                double duration = 10;
                if (args.length >= 5) {
                    duration = Double.parseDouble(args[4]);
                }
                var effect = new CrownCurve(plugin, loc, radius, Color.WHITE, Particle.REDSTONE, 10);
                effect.setDuration(duration).show();
                break;
            }
            case "style2": {
//                var effect = new CrownCurve(plugin, loc, 20, Color.WHITE, Particle.REDSTONE, 10);
//                effect.setDuration(10).setFinalRadius(1).show();
                break;
            }
            case "style3": {
                Fountain effect = null;
                if (args.length == 0)
                    effect = new Fountain(plugin);
                else {
                    loc.set(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                    int speed = args.length >= 4 ? Integer.parseInt(args[3]) : 10;
                    int duration = args.length >= 5 ? Integer.parseInt(args[4]) : 200;
                    int queueLength = args.length >= 6 ? Integer.parseInt(args[5]) : 200;
                    effect = new Fountain(plugin, speed, duration, queueLength, Particle.FLAME, null, loc);
                }
                effect.show();
                break;
            }
            default:
                sender.sendMessage("Wrong");
                return false;
        }
        return true;
    }
}
