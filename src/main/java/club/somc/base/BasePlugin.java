package club.somc.base;

import club.somc.base.commands.ReturnToBaseCommand;
import club.somc.base.commands.SetBaseCommand;
import club.somc.base.listeners.BaseListener;
import org.bukkit.plugin.java.JavaPlugin;


public class BasePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Datastore ds = new Datastore(this);

        this.getCommand("setbase").setExecutor(new SetBaseCommand(ds));
        this.getCommand("base").setExecutor(new ReturnToBaseCommand(ds));

        getServer().getPluginManager().registerEvents(new BaseListener(ds), this);
    }

    @Override
    public void onDisable() {
    }
}
