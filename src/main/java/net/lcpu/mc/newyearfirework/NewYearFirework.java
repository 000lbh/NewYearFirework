// Copyright 2024, 000lbh, all right reserved

package net.lcpu.mc.newyearfirework;

import net.lcpu.mc.newyearfirework.commands.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class NewYearFirework extends JavaPlugin {

    @Override
    public void onEnable() {
        var commandMap = this.getServer().getCommandMap();
        var style1Command = new SimpleCommand("style1", this);
        style1Command.setPermission("net.lcpu.mc.newyearfirework.style1");
        commandMap.register("style1", style1Command);
        var style3Command = new SimpleCommand("style3", this);
        style3Command.setPermission("net.lcpu.mc.newyearfirework.style3");
        commandMap.register("style3", style3Command);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
