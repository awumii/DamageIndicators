package me.xneox.indicators.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class PluginConfiguration {

  @Comment("Hologram displayed when an entity gets damaged.")
  private String damageHologram = "&#f94144-%amount%";

  @Comment("Hologram displayed whe an entity regains health.")
  private String healHologram = "&#90be6d+%amount%";

  @Comment("The format of the displayed damage amount.")
  private String damageFormat = "#.##";

  @Comment("Amount to scale the displayed damage.")
  private double scale = 1;

  @Comment("Time in milliseconds how long the hologram will be displayed.")
  private int duration = 1500;

  @Comment("The Y distance between the entity and the hologram.")
  private double spawnDistance = 0.8;

  @Comment("In which direction the hologram should move during it's display time?\n"
      + "UP, DOWN, or STILL if you don't want it to move.")
  private MoveDirection moveDirection = MoveDirection.DOWN;

  @Comment("How much the hologram shoud move upwards/downwards every TICK?")
  private double moveAmount = 0.04;

  public String damageHologram() {
    return this.damageHologram;
  }

  public String healHologram() {
    return this.healHologram;
  }

  public String damageFormat() {
    return this.damageFormat;
  }

  public double scale() {
    return this.scale;
  }

  public int duration() {
    return this.duration;
  }

  public double spawnDistance() {
    return this.spawnDistance;
  }

  public MoveDirection moveDirection() {
    return this.moveDirection;
  }

  public double moveAmount() {
    return this.moveAmount;
  }
}
