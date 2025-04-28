
package com.igngecko.gcchatcolor;

import com.igngecko.gcchatcolor.commands.ColorCommand;
import com.igngecko.gcchatcolor.gui.ColorGUI;
import com.igngecko.gcchatcolor.listeners.ChatListener;
import com.igngecko.gcchatcolor.listeners.GUIClickListener;
import com.igngecko.gcchatcolor.manager.ConfigManager;
import com.igngecko.gcchatcolor.manager.PlayerDataManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class GcChatColor extends JavaPlugin {

    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private ColorGUI colorGUI;

    @Override
    public void onEnable() {
        // Initialize Managers first
        configManager = new ConfigManager(this);
        playerDataManager = new PlayerDataManager(this);
        colorGUI = new ColorGUI(this); // ColorGUI needs ConfigManager

        // Load Configurations AFTER managers are initialized
        configManager.loadConfigs(); // Changed from loadConfigs() plural to singular

        // Register Commands
        ColorCommand colorCommandExecutor = new ColorCommand(this);
        Objects.requireNonNull(getCommand("chatcolor"), "Command 'chatcolor' not defined in plugin.yml")
               .setExecutor(colorCommandExecutor);
         Objects.requireNonNull(getCommand("chatcolor"), "Command 'chatcolor' not defined in plugin.yml")
               .setTabCompleter(colorCommandExecutor); // Set TabCompleter too
        Objects.requireNonNull(getCommand("ccreload"), "Command 'ccreload' not defined in plugin.yml")
               .setExecutor(colorCommandExecutor); // Reuse executor

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new GUIClickListener(this), this);

        getLogger().info("GcChatColor v" + getDescription().getVersion() + " has been enabled!");
        getLogger().info("Developed by " + String.join(", ", getDescription().getAuthors()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic (e.g., save data if not using PDCs exclusively)
        getLogger().info("GcChatColor has been disabled.");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public ColorGUI getColorGUI() {
        return colorGUI;
    }

    public void log(String message) {
        getLogger().info(message);
    }

    public void debug(String message) {
        // Use the getter from ConfigManager
        if (configManager != null && configManager.isDebugMode()) {
            getLogger().log(Level.INFO, "[DEBUG] " + message);
        }
    }
}
