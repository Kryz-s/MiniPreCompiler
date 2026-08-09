package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.format.Style;

public final class NewlineResolver implements ComponentResolver {
  @Override
  public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
    return new LiteralPreComponent("\n", inheritedStyle);
  }

  @Override
  public boolean isSelfClosing() {
    return true;
  }
  @Override
  public String[] names() {
    return new String[]{"newline", "br"};
  }
}
