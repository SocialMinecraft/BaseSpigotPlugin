package club.somc.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Datastore {

    private Objective base_x;
    private Objective base_y;
    private Objective base_z;
    private Objective base_world;

    public Datastore(JavaPlugin plugin) {
        // https://www.spigotmc.org/threads/creating-a-scoreboard.156548/
        final ScoreboardManager manager = Bukkit.getScoreboardManager();
        final Scoreboard board = manager.getMainScoreboard();

        try {
            if (board.getObjective("somcbase.x") == null) {
                board.registerNewObjective("somcbase.x", "dummy", "SoMCBase.X");
            }
            if (board.getObjective("somcbase.y") == null) {
                board.registerNewObjective("somcbase.y", "dummy", "SoMCBase.Y");
            }
            if (board.getObjective("somcbase.z") == null) {
                board.registerNewObjective("somcbase.z", "dummy", "SoMCBase.Z");
            }
            if (board.getObjective("somcbase.world") == null) {
                board.registerNewObjective("somcbase.world", "dummy", "SoMCBase.world");
            }

        } catch (IllegalArgumentException ex) {
            plugin.getLogger().severe(ex.toString());
        }
        this.base_x = board.getObjective("somcbase.x");
        this.base_y = board.getObjective("somcbase.y");
        this.base_z = board.getObjective("somcbase.z");
        this.base_world = board.getObjective("somcbase.world");
    }

    /**
     * Updates a players base location to their current location.
     * @param player The player to set the base location for.
     * @return True on success, false on failure.
     */
    public boolean setBase(Player player) {
        final Block block = player.getLocation().getBlock();
        int world_int = worldToInt(block.getWorld());
        if (world_int == 0)
            return false;

        this.base_x.getScore(player).setScore(block.getX());
        this.base_y.getScore(player).setScore(block.getY());
        this.base_z.getScore(player).setScore(block.getZ());
        this.base_world.getScore(player).setScore(world_int);

        return true;
    }

    /**
     * Returns the location of a base for a player.
     * @param player The online player to lookup the base for.
     * @return The Location of their base.
     */
    public Location getBase(Player player) {
        if (!this.base_world.getScore(player).isScoreSet()) {
            return null;
        }
        World world = intToWorld(this.base_world.getScore(player).getScore());
        if (world == null) {
            return null;
        }

        return new Location(world,
                this.base_x.getScore(player).getScore(),
                this.base_y.getScore(player).getScore(),
                this.base_z.getScore(player).getScore());
    }

    /**
     * Returns if a player is in their base.
     * @param player The player to check.
     * @return Non-Zero means they are in their base. The
     * value is the blocks away from the edge of the base.
     * IE bigger numbers are more toward the center.
     */
    public int playerInBase(Player player) {
        Location base = getBase(player);
        if (base == null) {
            return 0;
        }

        if (player.getLocation().getWorld() != base.getWorld())
            return 0;

        double distance = base.distance(player.getLocation());

        return (int)Math.max(175 - distance, 0);
    }

    /**
     * Converts the world into an int value that can be store in a scoreboard.
     * @param world the world.
     * @return 1 for normal, 2 for nether, 3 for the end, 0 for unknown.
     */
    private int worldToInt(World world) {
        switch (world.getEnvironment()) {
            case NORMAL:
                return 1;
            case NETHER:
                return 2;
            case THE_END:
                return 3;
            default:
                return 0;
        }
    }

    private World intToWorld(int id) {
        for ( World world : Bukkit.getWorlds()) {
            if (world.getEnvironment() == World.Environment.NORMAL &&
                    id == 1)
                return world;
            if (world.getEnvironment() == World.Environment.NETHER &&
                    id == 2)
                return world;
            if (world.getEnvironment() == World.Environment.THE_END &&
                    id == 3)
                return world;
        }

        return null;
    }

}
