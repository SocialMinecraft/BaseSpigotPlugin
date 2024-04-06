package club.somc.base;

import club.somc.base.commands.ReturnToBaseCommand;
import club.somc.base.commands.SetBaseCommand;
import club.somc.base.listeners.BaseListener;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class BasePlugin extends JavaPlugin {

    Connection nc;

    @Override
    public void onEnable() {
        super.onEnable();
        this.saveDefaultConfig();

        Datastore ds = new Datastore(this);

        try {
            this.nc = Nats.connect(getConfig().getString("natsUrl"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.getCommand("setbase").setExecutor(new SetBaseCommand(ds));
        this.getCommand("base").setExecutor(new ReturnToBaseCommand(ds));

        getServer().getPluginManager().registerEvents(new BaseListener(ds), this);
    }

    @Override
    public void onDisable() {
    }
}
