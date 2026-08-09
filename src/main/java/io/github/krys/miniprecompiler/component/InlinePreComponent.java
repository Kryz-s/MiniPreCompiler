package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.Component;

public final class InlinePreComponent extends PreComponent {
  private final Component component;

  public InlinePreComponent(Component component) {
    this.component = component;
  }

  @Override
  public Component render(Pointered pointered) {
    return this.component;
  }

  @Override
  public PreComponent deepCopy() {
    return new InlinePreComponent(component);
  }
}
