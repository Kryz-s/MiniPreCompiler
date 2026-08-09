package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.ColorPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.MiniPreCompiler;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

public final class ColorResolver implements ComponentResolver {
  private final String[] names;
  private final TextColor color;

  public ColorResolver(String name, TextColor color) {
    this.names = new String[]{name};
    this.color = color;
  }

  @Override
  public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
    if (name.equals("color")) {
      final String color = args.get(0);
//      System.out.println(color);
      return new ColorPreComponent(resolveColor(color), children);
    }
    return new ColorPreComponent(color, children);
  }

  static TextColor resolveColor(String colorName) {
    final TextColor color;
    if (colorName.charAt(0) == TextColor.HEX_CHARACTER) {
      color = TextColor.fromHexString(colorName);
    } else {
      color = NamedTextColor.NAMES.value(colorName);
    }

//    System.out.println("Resolve color= " + color);

    return color;
  }

  @Override
  public String[] names() {
    return names;
  }
}