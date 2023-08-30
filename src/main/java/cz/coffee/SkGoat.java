package cz.coffee;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import cz.coffee.api.EventTimerOnce;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import static cz.coffee.util.SimpleUtils.color;
import static cz.coffee.util.SimpleUtils.hex;

public final class SkGoat extends JavaPlugin {

    private static PluginManager pm;
    private static SkGoat instance;
    private static PluginDescriptionFile pdf;
    private static Logger logger;
    //public static Set<Trigger> OI = new HashSet<>();

    public static SkGoat getInstance() {
        if (instance == null) throw new IllegalStateException("instance cannot be a null");
        return instance;
    }

    private boolean pluginCanLoad() {
        logger = getLogger();
        boolean canLoad = true;
        String reason = null;
        Plugin skriptPlugin = pm.getPlugin("Skript");
        if (skriptPlugin == null || !skriptPlugin.isEnabled()) {
            reason = "Plugin 'Skript' isn't enabled";
            canLoad = false;
        }
        if (!canLoad) severe("Couldn't load " + pdf.getName() + ":\n- " + reason);
        return canLoad;
    }

    public PluginManager getPm() {
        return this.getServer().getPluginManager();
    }


    // Loggers
    @SuppressWarnings("unused")
    public static void warning(String string) {
        logger.warning(color("&e" + string));
    }

    // Logging
    public static void info(String string) {
        logger.info(color(string));
    }

    @SuppressWarnings("unused")
    public static void debug(Object str) {
        logger.severe(color("DEBUG! " + "&r" + str));
    }

    public static void severe(String string) {
        logger.severe(color("&c" + string));
    }

    public static void console(String string) {
        String prefix = color("&7[" + hex("#6ee1fas#60c0d9k#53a0b9G#458198o#386478a#2a4858t") + "&7]");
        Bukkit.getServer().getConsoleSender().sendMessage(color(prefix + " " + hex(string)));
    }





    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        instance = this;
        pm = getPm();
        pdf = instance.getDescription();
        if (!pluginCanLoad()) {
            pm.disablePlugin(this);
            return;
        }
        SkriptAddon addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("cz.coffee.skript");
        } catch (Exception ex) {
            severe("Unable to register " + pdf.getName() + " syntaxes:\n- " + ex.getMessage());
            ex.printStackTrace();
            return;
        }
        console("&aFinished loading");}

    @Override
    public void onDisable() {
        info("&eDisabling... good bye!");
    }
}
