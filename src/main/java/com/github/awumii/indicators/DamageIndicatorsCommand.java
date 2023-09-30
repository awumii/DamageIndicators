package com.github.awumii.indicators;

import me.xneox.commons.paper.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class DamageIndicatorsCommand extends Command implements PluginIdentifiableCommand {
  private final DamageIndicatorsPlugin plugin;

  public DamageIndicatorsCommand(DamageIndicatorsPlugin plugin) {
    super("damageindicators", "Main command of DamageIndicators", "", List.of("di"));
    this.plugin = plugin;
  }

  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
    if (args.length < 1) {
      var desc = this.plugin.getPluginMeta();
      sender.sendMessage(TextUtils.color("&a&l" + desc.getName() + " v" + desc.getVersion() + " &fby " + TextUtils.join(',', desc.getAuthors())));
      sender.sendMessage(TextUtils.color("&7&o&n" + desc.getWebsite()));
      sender.sendMessage(Component.empty());
      sender.sendMessage(TextUtils.color("&7Run &f/" + commandLabel + " reload &7to reload the configuration."));
      return true;
    }

    if (args[0].equalsIgnoreCase("reload")) {
      this.plugin.loadConfig();
      sender.sendMessage(TextUtils.color("&aConfiguration reloaded!"));
    } else {
      sender.sendMessage(TextUtils.color("&cCommand not found. Use /" + commandLabel + " for help."));
    }
    return true;
  }

  @Override
  public @NotNull Plugin getPlugin() {
    return this.plugin;
  }
}
