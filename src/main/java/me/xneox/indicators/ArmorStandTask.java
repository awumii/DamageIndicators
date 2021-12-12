package me.xneox.indicators;

import me.xneox.indicators.config.MoveDirection;
import org.jetbrains.annotations.NotNull;

public record ArmorStandTask(@NotNull DamageIndicatorsPlugin plugin) implements Runnable {

  @Override
  public void run() {
    // This needs to be iterator since we remove the values (keys actually) of the map we iterate over.
    var iterator = this.plugin.activeArmorStands().entrySet().iterator();
    while (iterator.hasNext()) {
      var entry = iterator.next();
      var armorStand = entry.getKey();
      var spawnTime = entry.getValue();

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
