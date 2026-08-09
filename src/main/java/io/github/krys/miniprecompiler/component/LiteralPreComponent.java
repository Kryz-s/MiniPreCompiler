package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.pointer.Pointered;

public final class LiteralPreComponent extends PreComponent {
  private final String text;
  private Component cached;

  public LiteralPreComponent(String text, Style style) {
    this.text = text;
    this.computedStyle = style;
  }

  @Override
  public Component render(Pointered pointered) {
    if (cached != null) return cached;
    if (computedStyle.equals(Style.empty())) {
      cached = Component.text(text);
      return cached;
    }
    cached = Component.text(text, computedStyle);
    return cached;
  }

  @Override
  public PreComponent deepCopy() {
    LiteralPreComponent copy = new LiteralPreComponent(text, computedStyle);
    if (children != null) {
      PreComponent[] copied = new PreComponent[children.length];
      for (int i = 0; i < children.length; i++) {
        copied[i] = children[i].deepCopy();
      }
      copy.children = copied;
    }
    return copy;
  }
}