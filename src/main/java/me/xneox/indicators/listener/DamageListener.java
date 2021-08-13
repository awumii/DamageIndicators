package me.xneox.indicators.listener;

import java.text.DecimalFormat;
import me.xneox.indicators.DamageIndicatorsPlugin;
import me.xneox.indicators.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

public class DamageListener implements Listener {
  private final DamageIndicatorsPlugin plugin;
  private final DecimalFormat decimalFormat;

  public DamageListener(DamageIndicatorsPlugin plugin) {
    this.plugin = plugin;
    this.decimalFormat = new DecimalFormat(plugin.config().damageFormat());
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    this.createHologram(event.getEntity().getLocation(), this.plugin.config().damageHologram(), event.getFinalDamage());
  }

  @EventHandler
  public void onEntityHeal(EntityRegainHealthEvent event) {
    this.createHologram(event.getEntity().getLocation(), this.plugin.config().healHologram(), event.getAmount());
  }

  private void createHologram(@NotNull Location location, @NotNull String text, double damage) {
    String formattedDamage = this.decimalFormat.format(damage * this.plugin.config().scale());

    // Thanks to Paper, this function will run before the ArmorStand is added to the world.
    // This also fixes client rendering the ArmorStand for a fraction of second.
    location.getWorld().spawn(location.add(0, this.plugin.config().spawnDistance(), 0), ArmorStand.class, armorStand -> {
      armorStand.setVisible(false);
      armorStand.setGravity(false);
      armorStand.setMarker(true);
      armorStand.setInvulnerable(true);

      armorStand.customName(ChatUtils.color(text.replace("%amount%", formattedDamage)));
      armorStand.setCustomNameVisible(true);

      this.plugin.activeArmorStands().put(armorStand, System.currentTimeMillis());
    });
  }
}
