package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.ShadowColor;

public final class ShadowPreComponent extends PreComponent {
  private final ShadowColor color;

  public ShadowPreComponent(ShadowColor color, PreComponent[] children) {
    this.color = color;
    this.children = children;
  }

  @Override
  public Component render(Pointered pointered) {
    var builder = Component.text()
      .style(computedStyle)
      .shadowColor(color);
    for (PreComponent child : children) {
      builder.append(child.render(pointered));
    }
    return builder.build();
  }

  @Override
  public PreComponent deepCopy() {
    PreComponent[] copied = new PreComponent[children.length];
    for (int i = 0; i < children.length; i++) {
      copied[i] = children[i].deepCopy();
    }
    return new ShadowPreComponent(color, copied);
  }
}
