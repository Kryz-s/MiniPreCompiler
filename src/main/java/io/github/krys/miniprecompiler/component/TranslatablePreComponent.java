package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.model.PreComponentTree;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.pointer.Pointered;

public final class TranslatablePreComponent extends PreComponent {
  private final String key;
  private final String fallback;
  private final PreComponentTree[] args;

  public TranslatablePreComponent(String key, PreComponentTree[] args, Style style) {
    this.key = key;
    this.fallback = null;
    this.args = args;
    this.computedStyle = style;
  }

  public TranslatablePreComponent(String key, String fallback, PreComponentTree[] args, Style style) {
    this.key = key;
    this.fallback = fallback;
    this.args = args;
    this.computedStyle = style;
  }

  @Override
  public Component render(Pointered pointered) {
    net.kyori.adventure.text.TranslatableComponent.Builder builder = Component.translatable()
      .key(key)
      .style(computedStyle)
      .fallback(fallback);
    if (args != null) {
      final Component[] components = new Component[args.length];
      for (int i = 0; i < args.length; i++) {
        components[i] = args[i].render(pointered);
      }
      builder.arguments(components);
    }
    if (computedStyle != null) {
      builder.style(computedStyle);
    }
    return builder.build();
  }

  @Override
  public PreComponent deepCopy() {
    PreComponentTree[] copiedArgs = args != null ? args.clone() : null;
    // FIX: Preservar el fallback si existe.
    if (fallback != null) {
      return new TranslatablePreComponent(key, fallback, copiedArgs, computedStyle);
    }
    return new TranslatablePreComponent(key, copiedArgs, computedStyle);
  }
}
