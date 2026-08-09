package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.InlinePreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.object.ObjectContents;

public final class SpriteResolver implements ComponentResolver {
  @Override
  public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
    final String firstArg = args.get(0);
    final String secondArg = args.get(1);
    if (secondArg == null) return new InlinePreComponent(Component.object(ObjectContents
      .sprite(Key.key(firstArg))));
    return new InlinePreComponent(Component.object(ObjectContents
      .sprite(Key.key(firstArg), Key.key(secondArg))));
  }

  @Override
  public String[] names() {
    return new String[]{"sprite"};
  }

  @Override
  public boolean isSelfClosing() {
    return true;
  }
}
