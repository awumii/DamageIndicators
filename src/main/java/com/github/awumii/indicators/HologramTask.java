package com.github.awumii.indicators;

import com.github.awumii.indicators.config.MoveDirection;
import eu.decentsoftware.holograms.api.DHAPI;
import org.jetbrains.annotations.NotNull;

public record HologramTask(@NotNull DamageIndicatorsPlugin plugin) implements Runnable {

  @Override
  public void run() {
    // This needs to be iterator since we remove the values (keys actually) of the map we iterate over.
    var iterator = this.plugin.getActiveHolograms().entrySet().iterator();
    while (iterator.hasNext()) {
      var entry = iterator.next();
      var name = entry.getKey();
      var spawnTime = entry.getValue();

      // Removing the ArmorStand if expired.
      if (System.currentTimeMillis() - spawnTime > this.plugin.config().duration()) {
        DHAPI.removeHologram(name);
        iterator.remove();
        continue;
      }

      // Moving the ArmorStand upwards or downwards.
      if (this.plugin.config().moveDirection() == MoveDirection.UP) {
        var hologram = DHAPI.getHologram(name);
        DHAPI.moveHologram(hologram, hologram.getLocation().add(0, this.plugin.config().moveAmount(), 0));
      } else if (this.plugin.config().moveDirection() == MoveDirection.DOWN) {
        var hologram = DHAPI.getHologram(name);
        DHAPI.moveHologram(hologram, hologram.getLocation().subtract(0, this.plugin.config().moveAmount(), 0));
      }
    }
  }
}
