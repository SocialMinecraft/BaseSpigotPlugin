package dev.garvis.somcbase;

import org.bukkit.plugin.java.JavaPlugin;


public class SoMCBasePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Datastore ds = new Datastore(this);

        this.getCommand("setbase").setExecutor(new SetBaseCommand(ds));
        this.getCommand("base").setExecutor(new ReturnToBaseCommand(ds));

        getServer().getPluginManager().registerEvents(new BaseListener(ds), this);
        //getServer().getPluginManager().registerEvents(new SetFlyingListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
