package me.xneox.indicators;

import me.xneox.commons.paper.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public record DamageIndicatorsCommand(@NotNull DamageIndicatorsPlugin plugin) implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (args.length < 1) {
      var desc = this.plugin.getDescription();
      sender.sendMessage(TextUtils.color("&a&l" + desc.getName() + " v" + desc.getVersion() + " &fby " + TextUtils.join(',', desc.getAuthors())));
      sender.sendMessage(TextUtils.color("&7&o&n" + desc.getWebsite()));
      sender.sendMessage(Component.empty());
      sender.sendMessage(TextUtils.color("&7Run &f/" + label + " reload &7to reload the configuration."));
      return true;
    }

    if (args[0].equalsIgnoreCase("reload")) {
      this.plugin.loadConfig();
      sender.sendMessage(TextUtils.color("&aConfiguration reloaded!"));
    } else {
      sender.sendMessage(TextUtils.color("&cCommand not found. Use /" + label + " for help."));
    }
    return true;
  }
}
