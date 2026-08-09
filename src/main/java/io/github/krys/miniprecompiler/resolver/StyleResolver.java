package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.GroupPreComponent;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.StyleInheritance;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

public final class StyleResolver implements ComponentResolver {
  private final String[] names;
  private final TextDecoration decoration;

  public StyleResolver(TextDecoration decoration, String... names) {
    this.decoration = decoration;
    this.names = names;
  }

  @Override
  public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
    boolean negate = name.startsWith("!");
    Style own = Style.style().decoration(decoration, !negate).build();
    Style merged = StyleInheritance.compute(own, inheritedStyle);
    if (children.length == 0) {
      return new LiteralPreComponent("", merged);
    }
    if (children.length == 1) {
      children[0].setStyle(merged);
      return children[0];
    }
    GroupPreComponent group = new GroupPreComponent(children);
    group.setStyle(merged);
    return group;
  }

  @Override
  public String[] names() {
    return names;
  }
}