package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.GradientPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.MiniPreCompiler;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.Style;

public final class RainbowResolver implements ComponentResolver {
  private static final TextColor[] RAINBOW = new TextColor[]{
    TextColor.color(0xFF0000), TextColor.color(0xFF7F00), TextColor.color(0xFFFF00),
    TextColor.color(0x00FF00), TextColor.color(0x0000FF), TextColor.color(0x4B0082),
    TextColor.color(0x9400D3)
  };

  @Override
  public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
    float phase = args.getFloat(0, 0.0f);
    return new GradientPreComponent(RAINBOW.clone(), phase, children);
  }

  @Override
  public String[] names() {
    return new String[]{"rainbow"};
  }
}