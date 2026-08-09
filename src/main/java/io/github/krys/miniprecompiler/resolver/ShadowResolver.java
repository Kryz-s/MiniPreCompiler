package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import io.github.krys.miniprecompiler.component.ShadowPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

import static io.github.krys.miniprecompiler.resolver.ColorResolver.resolveColor;

public final class ShadowResolver implements ComponentResolver {
  @Override
  public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
    // FIX: Si no hay argumentos, devolver un componente vacío en lugar de NPE.
    if (args.isEmpty()) {
      return new LiteralPreComponent("", inheritedStyle);
    }
    final String colorString = args.get(0).toLowerCase();
    final ShadowColor color;
    if (colorString.startsWith(TextColor.HEX_PREFIX) && colorString.length() == 9) {
      color = ShadowColor.fromHexString(colorString);
      if (color == null) {
        throw new IllegalArgumentException(String.format("Unable to parse a shadow color from '%s'. Please use #RRGGBBAA formatting.", colorString));
      }
    } else {
      final TextColor text = resolveColor(colorString);
      final String alphaS = args.get(1, "0.25");
      final double alpha = Double.parseDouble(alphaS);
      color = ShadowColor.shadowColor(text, (int) (alpha * 0xff));
    }
    return new ShadowPreComponent(color, children);
  }

  @Override
  public String[] names() {
    return new String[]{"shadow"};
  }
}
