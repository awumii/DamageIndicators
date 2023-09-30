package com.github.awumii.indicators;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.github.awumii.indicators.config.PluginConfiguration;
import me.xneox.commons.config.ConfigurationLoader;
import me.xneox.commons.libs.libs.configurate.ConfigurateException;
import me.xneox.commons.libs.libs.configurate.hocon.HoconConfigurationLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import eu.decentsoftware.holograms.api.DHAPI;

public final class DamageIndicatorsPlugin extends JavaPlugin {
  private final Map<String, Long> activeHolograms = new HashMap<>();
  private PluginConfiguration config;
  private DecimalFormat format;

  @Override
  public void onEnable() {
    this.loadConfig();

    Bukkit.getScheduler().runTaskTimer(this, new HologramTask(this), 0L, 1L);
    Bukkit.getPluginManager().registerEvents(new IndicatorListener(this), this);

    //noinspection ConstantConditions
    this.getServer().getCommandMap().register("damageindicators", new DamageIndicatorsCommand(this));
  }

  public void loadConfig() {
    var loader = HoconConfigurationLoader.builder()
        .file(new File(this.getDataFolder(), "config.conf"))
        .build();

    try {
      this.config = new ConfigurationLoader<>(PluginConfiguration.class, loader).load();
    } catch (ConfigurateException exception) {
      this.getSLF4JLogger().error("Could not load the plugin configuration: ", exception);
    }

    // Pre-compile the decimal format for later use.
    this.format = new DecimalFormat(this.config.damageFormat());
  }

  @NotNull
  public PluginConfiguration config() {
    return this.config;
  }

  @NotNull
  public Map<String, Long> getActiveHolograms() {
    return this.activeHolograms;
  }

  @NotNull
  public DecimalFormat getFormat() {
    return this.format;
  }

  @Override
  public void onDisable() {
    for (var id : this.activeHolograms.keySet()) {
      DHAPI.removeHologram(id);
    }
  }
}
