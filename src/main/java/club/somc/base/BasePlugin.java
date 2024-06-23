package club.somc.base;

import club.somc.base.commands.FlightCommand;
import club.somc.base.commands.ReturnToBaseCommand;
import club.somc.base.commands.SetBaseCommand;
import club.somc.base.listeners.BaseListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BasePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        Datastore ds = new Datastore(this);

        this.getCommand("setbase").setExecutor(new SetBaseCommand(ds));
        this.getCommand("base").setExecutor(new ReturnToBaseCommand(ds));
        this.getCommand("baseflight").setExecutor(new FlightCommand(ds));

        this.getCommand("baseflight").setTabCompleter(new TabCompleter() {

            private static final String[] OPTIONS = { "on", "off" };

            @Override
            public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
                if (args.length > 1) {
                    return new ArrayList<>();
                }
                final List<String> completions = new ArrayList<>();
                StringUtil.copyPartialMatches(args[0], Arrays.asList(OPTIONS), completions);
                return completions;
            }
        });

        getServer().getPluginManager().registerEvents(new BaseListener(ds), this);
    }

    @Override
    public void onDisable() {
    }
}
