package me.xneox.indicators;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import me.xneox.commons.config.ConfigurationLoader;
import me.xneox.commons.libs.libs.configurate.ConfigurateException;
import me.xneox.commons.libs.libs.configurate.hocon.HoconConfigurationLoader;
import me.xneox.indicators.config.PluginConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class DamageIndicatorsPlugin extends JavaPlugin {
  private final Map<ArmorStand, Long> activeArmorStands = new HashMap<>();
  private PluginConfiguration config;

  @Override
  public void onEnable() {
    this.loadConfig();

    Bukkit.getScheduler().runTaskTimer(this, new ArmorStandTask(this), 0L, 1L);
    Bukkit.getPluginManager().registerEvents(new IndicatorListener(this), this);

    //noinspection ConstantConditions
    this.getCommand("damageindicators").setExecutor(new DamageIndicatorsCommand(this));
  }

  public void loadConfig() {
    var loader = HoconConfigurationLoader.builder()
        .file(new File(this.getDataFolder(), "config.conf"))
        .build();

    try {
      this.config = new ConfigurationLoader<>(PluginConfiguration.class, loader)
          .load();
    } catch (ConfigurateException exception) {
      this.getLog4JLogger().error("Could not load the plugin configuration: ", exception);
    }
  }

  @NotNull
  public PluginConfiguration config() {
    return this.config;
  }

  @NotNull
  public Map<ArmorStand, Long> activeArmorStands() {
    return this.activeArmorStands;
  }

  @Override
  public void onDisable() {
    for (var armorStand : this.activeArmorStands.keySet()) {
      armorStand.remove();
    }
  }
}
