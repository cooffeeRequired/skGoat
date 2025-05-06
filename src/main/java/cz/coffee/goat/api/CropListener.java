package cz.coffee.goat.api;

import cz.coffee.goat.SkGoat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.Bukkit.getServer;

public class CropListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material type = event.getBlock().getType();
        if (isCrop(type)) {
            CropBreakEvent cropBreakEvent = new CropBreakEvent(event);
            getServer().getPluginManager().callEvent(cropBreakEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.PHYSICAL)) {
            try {
                Player player = event.getPlayer();
                Block clickedBlock = event.getClickedBlock();

                assert clickedBlock != null;
                Material type = clickedBlock.getType();
                if (isCrop(type)) {
                    CropBreakEvent cropBreakEvent = new CropBreakEvent(event);
                    getServer().getPluginManager().callEvent(cropBreakEvent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isCrop(Material type) {
        Server server = getServer();
        String version = server.getBukkitVersion();

        if (type == Material.WHEAT ||
                type == Material.CARROTS ||
                type == Material.POTATOES ||
                type == Material.BEETROOTS ||
                type == Material.NETHER_WART ||
                type == Material.COCOA ||
                type == Material.SUGAR_CANE ||
                type == Material.MELON_STEM ||
                type == Material.PUMPKIN_STEM ||
                type == Material.BAMBOO ||
                type == Material.SWEET_BERRY_BUSH ||
                type == Material.FARMLAND) {
            return true;
        }

        if (version.compareTo("1.19") >= 0) {
            if (type == Material.CAVE_VINES ||
                    type == Material.CAVE_VINES_PLANT ||
                    type == Material.TWISTING_VINES ||
                    type == Material.TWISTING_VINES_PLANT ||
                    type == Material.WEEPING_VINES ||
                    type == Material.WEEPING_VINES_PLANT) {
                return true;
            }
        }

        if (version.compareTo("1.20") >= 0) {
            if (type == Material.TORCHFLOWER ||
                    type == Material.PITCHER_PLANT) {
                return true;
            }
        }

        if (version.compareTo("1.21") >= 0) {
            return type == Material.CHORUS_PLANT ||
                    type == Material.CHORUS_FLOWER;
        }

        return false;
    }
}
