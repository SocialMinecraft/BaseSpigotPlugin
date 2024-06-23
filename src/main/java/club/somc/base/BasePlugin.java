package club.somc.base;

import club.somc.base.commands.ReturnToBaseCommand;
import club.somc.base.commands.SetBaseCommand;
import club.somc.base.listeners.BaseListener;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.UUID;


public class BasePlugin extends JavaPlugin {

    Connection nc;

    @Override
    public void onEnable() {
        super.onEnable();
        this.saveDefaultConfig();

        // UUID?
        String uuid = this.getConfig().getString("uuid");
        if (uuid == null || uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString().replace("-", "");
            this.getConfig().set("uuid", uuid);
            this.saveConfig();
        }

        // setup datastore
        Datastore ds = new Datastore(this);

        // Check if we have installed the database before
        //this.getConfig().getInt("dbVersion")

        // connect to nats
        try {
            this.nc = Nats.connect(getConfig().getString("natsUrl"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // setup commmands
        this.getCommand("setbase").setExecutor(new SetBaseCommand(ds));
        this.getCommand("base").setExecutor(new ReturnToBaseCommand(ds));

        // setup listeners
        getServer().getPluginManager().registerEvents(new BaseListener(ds), this);
    }

    @Override
    public void onDisable() {
    }
}
