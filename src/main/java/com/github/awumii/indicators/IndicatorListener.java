package com.github.awumii.indicators;

import java.util.List;

import eu.decentsoftware.holograms.api.DHAPI;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

public class IndicatorListener implements Listener {
  private final DamageIndicatorsPlugin plugin;

  public IndicatorListener(DamageIndicatorsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.isCancelled()) {
      return;
    }
    this.createHologram(event.getEntity(), this.plugin.config().damageHologram(), event.getFinalDamage());
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityHeal(EntityRegainHealthEvent event) {
    this.createHologram(event.getEntity(), this.plugin.config().healHologram(), event.getAmount());
  }

  private void createHologram(@NotNull Entity entity, @NotNull String text, double damage) {
    if (this.plugin.config().blacklistedEntities().contains(entity.getType())) {
      return;
    }

    var formattedDamage = this.plugin.getFormat().format(damage * this.plugin.config().scale());
    var location = entity.getLocation();

    // Randomize hologram spawn offsets.
    var offsets = this.plugin.config().spawnOffsets();
    var x = rand(offsets.xMin(), offsets.xMax());
    var y = rand(offsets.yMin(), offsets.yMax());
    var z = rand(offsets.zMin(), offsets.zMax());

    var name = "DI_" + RandomUtils.nextInt();
    this.plugin.getSLF4JLogger().info(name);

    DHAPI.createHologram(name, location.add(x, y, z), List.of(text.replace("%amount%", formattedDamage)));
    this.plugin.getActiveHolograms().put(name, System.currentTimeMillis());
  }

  // todo move to commons
  private double rand(double min, double max) {
    return min + Math.random() * (max - min);
  }
}
