package me.xneox.indicators;

import java.text.DecimalFormat;
import me.xneox.commons.paper.TextUtils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

public class IndicatorListener implements Listener {
  private final DamageIndicatorsPlugin plugin;
  private final DecimalFormat decimalFormat;

  public IndicatorListener(DamageIndicatorsPlugin plugin) {
    this.plugin = plugin;
    this.decimalFormat = new DecimalFormat(plugin.config().damageFormat());
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

    var formattedDamage = this.decimalFormat.format(damage * this.plugin.config().scale());
    var location = entity.getLocation();

    // Randomize hologram spawn offsets.
    var offsets = this.plugin.config().spawnOffsets();
    var x = rand(offsets.xMin(), offsets.xMax());
    var y = rand(offsets.yMin(), offsets.yMax());
    var z = rand(offsets.zMin(), offsets.zMax());

    // Thanks to Paper, this function will run before the ArmorStand is added to the world.
    // This also fixes client rendering the ArmorStand for a fraction of second.
    location.getWorld().spawn(location.add(x, y, z), ArmorStand.class, armorStand -> {
      armorStand.setVisible(false);
      armorStand.setGravity(false);
      armorStand.setMarker(true);
      armorStand.setInvulnerable(true);

      armorStand.customName(TextUtils.color(text.replace("%amount%", formattedDamage)));
      armorStand.setCustomNameVisible(true);

      this.plugin.activeArmorStands().put(armorStand, System.currentTimeMillis());
    });
  }

  // todo move to commons
  private double rand(double min, double max) {
    return min + Math.random() * (max - min);
  }
}
