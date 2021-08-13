package me.xneox.indicators.task;

import java.util.Iterator;
import java.util.Map.Entry;
import me.xneox.indicators.DamageIndicatorsPlugin;
import me.xneox.indicators.config.MoveDirection;
import org.bukkit.entity.ArmorStand;

public class ArmorStandTask implements Runnable {
  private final DamageIndicatorsPlugin plugin;

  public ArmorStandTask(DamageIndicatorsPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void run() {
    // This needs to be iterator since we remove the values (keys actually) of the map we iterate over.
    Iterator<Entry<ArmorStand, Long>> iterator = this.plugin.activeArmorStands().entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<ArmorStand, Long> entry = iterator.next();
      ArmorStand armorStand = entry.getKey();
      Long spawnTime = entry.getValue();

      // Removing the ArmorStand if expired.
      if (System.currentTimeMillis() - spawnTime > this.plugin.config().duration()) {
        armorStand.remove();
        iterator.remove();
        continue;
      }

      // Moving the ArmorStand upwards or downwards.
      if (this.plugin.config().moveDirection() == MoveDirection.UP) {
        armorStand.teleport(armorStand.getLocation().add(0, this.plugin.config().moveAmount(), 0));
      } else if (this.plugin.config().moveDirection() == MoveDirection.DOWN) {
        armorStand.teleport(armorStand.getLocation().subtract(0, this.plugin.config().moveAmount(), 0));
      }
    }
  }
}
