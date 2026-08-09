package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.InsertingPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.format.Style;

public final class InsertResolver implements ComponentResolver {
  @Override
  public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
    String placeholder = args.get(0, "");
    return new InsertingPreComponent(placeholder, children);
  }

  @Override
  public String[] names() {
    return new String[]{"insert", "insertion"};
  }
}