package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.pointer.Pointered;

public final class ColorPreComponent extends PreComponent {
  private final TextColor color;

  public ColorPreComponent(TextColor color, PreComponent[] children) {
    this.color = color;
    this.children = children;
  }

  @Override
  public Component render(Pointered pointered) {
    TextComponent.Builder builder = Component.text()
      .style(computedStyle)
      .color(color);
//    System.out.println(color);
    for (PreComponent child : getChildren()) {
      Component c = child.render(pointered);
//      System.out.println(c);
      builder.append(c);
    }
    return builder.build();
  }

  @Override
  public PreComponent deepCopy() {
    PreComponent[] copied = new PreComponent[children.length];
    for (int i = 0; i < children.length; i++) {
      copied[i] = children[i].deepCopy();
    }
    return new ColorPreComponent(color, copied);
  }
}