package me.xneox.indicators;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import me.xneox.indicators.config.PluginConfiguration;
import me.xneox.indicators.listener.IndicatorListener;
import me.xneox.indicators.task.ArmorStandTask;
import me.xneox.indicators.util.ConfigurationLoader;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;

public final class DamageIndicatorsPlugin extends JavaPlugin {
  private final Map<ArmorStand, Long> activeArmorStands = new HashMap<>();
  private PluginConfiguration config;

  @Override
  public void onEnable() {
    File configFile = new File(this.getDataFolder(), "config.conf");
    try {
      this.config = new ConfigurationLoader<>(configFile, PluginConfiguration.class)
          .load();
    } catch (ConfigurateException exception) {
      this.getLog4JLogger().error("Could not load the plugin configuration: ", exception);
    }

    Bukkit.getScheduler().runTaskTimer(this, new ArmorStandTask(this), 0L, 1L);
    Bukkit.getPluginManager().registerEvents(new IndicatorListener(this), this);

    new Metrics(this, 12458);
  }

  public PluginConfiguration config() {
    return this.config;
  }

  public Map<ArmorStand, Long> activeArmorStands() {
    return this.activeArmorStands;
  }

  @Override
  public void onDisable() {
    this.activeArmorStands.keySet().forEach(Entity::remove);
  }
}
