package me.xneox.indicators.util;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class ChatUtils {
  private static final LegacyComponentSerializer SERIALIZER =
      LegacyComponentSerializer.builder()
          .character('&')
          .hexCharacter('#')
          .hexColors()
          .build();

  /**
   * Returns a formatted TextComponent, colors are replaced
   * using the & symbol. Hex colors are supported (ex. &#cd7f32).
   *
   * @param message Raw string to be formatted.
   * @return A formatted TextComponent.
   */
  @NotNull
  public static TextComponent color(@NotNull String message) {
    return SERIALIZER.deserialize(message);
  }
}
