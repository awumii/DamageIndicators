package me.xneox.indicators.config;

import java.util.EnumSet;
import me.xneox.commons.libs.libs.configurate.objectmapping.ConfigSerializable;
import me.xneox.commons.libs.libs.configurate.objectmapping.meta.Comment;
import org.bukkit.entity.EntityType;

@SuppressWarnings("ALL") // make intellij shut up about using final fields that would break the config loader.
@ConfigSerializable
public class PluginConfiguration {

  @Comment("Hologram displayed when an entity gets damaged.")
  private String damageHologram = "&#f94144-%amount%❤";

  @Comment("Hologram displayed when an entity regains health.")
  private String healHologram = "&#90be6d+%amount%❤";

  @Comment("The format of the displayed damage amount.")
  private String damageFormat = "#.##";

  @Comment("Amount to multiply the displayed damage amount.")
  private double scale = 1;

  @Comment("Time in milliseconds how long the hologram will be displayed.")
  private int duration = 1000;

  @Comment("The Y distance between the entity and the hologram.")
  private double spawnDistance = 0.8;

  @Comment("In which direction the hologram should move during it's display time?\n"
      + "UP, DOWN, or STILL if you don't want it to move.")
  private MoveDirection moveDirection = MoveDirection.DOWN;

  @Comment("How much the hologram shoud move upwards/downwards every TICK?")
  private double moveAmount = 0.04;

  @Comment("Entities that will not display a hologram.")
  private EnumSet<EntityType> blacklistedEntities = EnumSet.of(EntityType.DROPPED_ITEM);

  @Comment("Configure random offsets to spawn the hologram around an entity.")
  private SpawnOffsets spawnOffsets = new SpawnOffsets();

  @ConfigSerializable
  public static class SpawnOffsets {
    @Comment("The minimum Y distance between the entity and the hologram.")
    private double yMin = 0.6;

    @Comment("The maximum Y distance between the entity and the hologram.")
    private double yMax = 1.2;

    @Comment("The minimum X distance between the entity and the hologram.")
    private double xMin = 0.0;

    @Comment("The maximum X distance between the entity and the hologram.")
    private double xMax = 1.0;

    @Comment("The minimum Z distance between the entity and the hologram.")
    private double zMin = 0.0;

    @Comment("The maximum Z distance between the entity and the hologram.")
    private double zMax = 1.0;

    public double yMin() {
      return this.yMin;
    }

    public double yMax() {
      return this.yMax;
    }

    public double xMin() {
      return this.xMin;
    }

    public double xMax() {
      return this.xMax;
    }

    public double zMin() {
      return this.zMin;
    }

    public double zMax() {
      return this.zMax;
    }
  }

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

  public EnumSet<EntityType> blacklistedEntities() {
    return this.blacklistedEntities;
  }

  public SpawnOffsets spawnOffsets() {
    return this.spawnOffsets;
  }
}
